package puertos.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertos.entidades.Barco;
import puertos.entidades.Carguero;
import puertos.entidades.FabricaBarcos;
import puertos.entidades.Velero;

/**
 * Se encarga de obtener las sentencias SQL para crear, modificar o borrar un barco,
 * o para crear el objeto a partir de la sentencia SQL. 
 * Es decir, realiza las "conversiones" entre objetos e instrucciones SQL
 * para el registro de la información en la base de datos.
 * 
 * Se tiene una sola tabla llamada "barcos", con campos: 
 * matricula,nacionalidad,volumen,pasajeros,liquidos y tipo 
 * (tipo puede ser carguero o velero).
 * 
 * @version 1.0
 */
public class ConversorSqlBarcos {

	/**
	 * Elabora la instrucción SQL para insertar un barco en la base de datos,
	 *  usando los métodos get del objeto.
	 * @param barco objeto Barco que se desea insertar en la base de datos,
	 * 			debe ser diferente de null
	 */
	String crearSentenciaInsert(Barco barco) {
		String tipo = "carguero";
		if (barco instanceof Velero) {
			tipo = "velero";
		}

		String sentenciaSQL = "Insert into barcos(matricula,nacionalidad,volumen,pasajeros,liquidos,tipo)"
				+ " values ('"+barco.getMatricula()+"','"+barco.getNacionalidad()+"',"
				+ barco.getVolumen()+",";
		if (barco instanceof Velero) {
			Velero velero = (Velero)barco;
			sentenciaSQL += velero.getPasajeros()+",null,'"+tipo+"')";
		}
		else if (barco instanceof Carguero) {
			Carguero carguero = (Carguero)barco;
			int liquido = carguero.getLiquidos()?1:0;
			sentenciaSQL += "null,"+liquido+","+"'"+tipo+"')";
		}
		return sentenciaSQL;
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
	String crearSentenciaSelectUno() {
		return "Select matricula,nacionalidad,volumen,pasajeros,liquidos,tipo "
				+ " from Barcos "
				+ " where matricula = ?";
	}
	
	/**
	 * Crea un objeto barco a partir de los datos de un ResultSet.
	 * @param datosBarco el ResultSet resultante de una consulta de un barco en la base de datos.
	 * 						Debe ser diferente de null.
	 * @return	el objeto barco con sus valores (tomados del ResultSet), o null
	 * 			si el ResultSet está vacío (es decir, no se encontró al consultar en la BD).
	 */
	Barco instanciarBarco(ResultSet datosBarco) {
		Barco barco = null;
		try {
			String matricula = datosBarco.getString("matricula");
			String nacionalidad = datosBarco.getString("nacionalidad");
			double volumen = datosBarco.getDouble("volumen");
			int pasajeros = datosBarco.getInt("pasajeros");
			boolean liquidos = datosBarco.getBoolean("liquidos");
			char tipo = datosBarco.getString("tipo").charAt(0);
			barco = FabricaBarcos.crearBarco(matricula, nacionalidad, volumen, tipo, pasajeros, liquidos);
		} catch (SQLException e) {
			System.err.println("No se pudo obtener el barco con el ResultSet: "+datosBarco);
		}
		return barco;
	}
}
