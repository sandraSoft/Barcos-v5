package puertos.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import puertos.entidades.Barco;
import puertos.entidades.Carguero;
import puertos.entidades.FabricaBarcos;
import puertos.entidades.Velero;

/**
 * Se encarga de obtener las sentencias SQL para crear o borrar un barco,
 * o para crear el objeto a partir de la sentencia SQL. 
 * Es decir, realiza las "conversiones" entre objetos e instrucciones SQL
 * para el registro de la información en la base de datos.
 * 
 * En la BD se tiene una sola tabla llamada "barcos", con campos: 
 * matricula,nacionalidad,volumen,pasajeros,liquidos y tipo 
 * (tipo puede ser carguero o velero).
 * 
 * @version 1.0
 */
public class ConversorSql {
	/**
	 * Crea un objeto barco con los datos de una consulta en la BD (ResultSet)
	 * 
	 * @param datosBarco el ResultSet resultante de una consulta de un barco
	 * 		 en la base de datos. Debe ser diferente de null.
	 * @return	el objeto barco con sus valores (tomados del ResultSet), 
	 * 		o null si el ResultSet está vacío (no se encontró en la BD).
	 * @throws SQLException si se presenta algún error al obtener los datos
	 */
	Barco crearObjetoBarco(ResultSet datosBarco) throws SQLException {

		JSONObject barcoJson  = new JSONObject();
		barcoJson.put("matricula",datosBarco.getString("matricula"));
		barcoJson.put("nacionalidad",datosBarco.getString("nacionalidad"));
		barcoJson.put("volumen",datosBarco.getDouble("volumen"));
		barcoJson.put("pasajeros",datosBarco.getInt("pasajeros"));
		barcoJson.put("liquidos",datosBarco.getBoolean("liquidos"));
		barcoJson.put("tipo",datosBarco.getString("tipo"));

		return FabricaBarcos.crearBarco(barcoJson);
	}
	
	/**
	 * Elabora la instrucción select para consultar la información 
	 * de todos los barcos.
	 * @return Una cadena con la instrucción SQL (Select) de la consulta
	 */
	String crearSentenciaSelectTodos() {
		return "Select matricula,nacionalidad,volumen,pasajeros,liquidos,tipo "
				+ " from Barcos";
	}
	
	/**
	 * Elabora la instrucción select para consultar la información 
	 * de un barco, dejando la matrícula parametrizable.
	 * @return Una cadena con la instrucción SQL (Select) de la consulta
	 * 		de un barco, dejando la matrícula para que luego se le pueda
	 * 		dar el valor usando un preparedStatement y setString.
	 */
	String crearSentenciaSelectBarco() {
		return "Select matricula,nacionalidad,volumen,pasajeros,liquidos,tipo "
				+ " from Barcos "
				+ " where matricula = ?";
	}
	
	/**
	 * Crear la sentencia SQL para insertar un barco en la base de datos
	 * 	(toma los datos del barco usando los métodos get).
	 * @param barco el objeto Barco que se desea insertar en la base de datos,
	 * 			debe ser diferente de null
	 * @return una cadena con la instrucción SQL para insertar un barco en la BD.
	 */
	String crearSentenciaSQL(Barco barco) {
		String tipo = "carguero";
		if (barco instanceof Velero) {
			tipo = "velero";
		}
		String sentenciaSQL = "Insert into barcos(matricula," 
				+ "nacionalidad,volumen,pasajeros,liquidos,tipo)"
				+ " values ('" + barco.getMatricula() + "','" 
				+ barco.getNacionalidad() + "'," + barco.getVolumen()
				+ ",";
		if (barco instanceof Velero) {
			Velero velero = (Velero) barco;
			sentenciaSQL += velero.getPasajeros() + ",null,'" + tipo + "')";
		} else if (barco instanceof Carguero) {
			Carguero carguero = (Carguero) barco;
			int liquido = carguero.getLiquidos() ? 1 : 0;
			sentenciaSQL += "null," + liquido + ",'" + tipo + "')";
		}
		return sentenciaSQL;
	}
}