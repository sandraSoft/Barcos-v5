package puertos.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import puertos.entidades.Barco;

/**
 * Usa una base de datos como repositorio de los datos de los barcos,
 * y ofrece los servicios definidos en RepositorioBarcos.
 * 
 * Se usa como ejemplo una base de datos llamada "barcos.db" (motor SQLite),
 * que tiene una sola tabla llamada "barcos", con campos: 
 * matricula,nacionalidad,volumen,pasajeros,liquidos y tipo 
 * 
 * @version 2.0
 */
public class BaseDatosBarcos implements RepositorioBarcos {
	String cadenaConexion;
	private ConversorSql conversor;

	public BaseDatosBarcos() {
		conversor = new ConversorSql();
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			cadenaConexion = "jdbc:sqlite:barcos.db";
		} catch (SQLException e) {
			System.err.println("Error de conexión con la base de datos: " + e);
		}
	}

	@Override
	public List<Barco> consultarBarcos() {
		List<Barco> barcos = new ArrayList<Barco>();
		try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
			String consultaSQL = conversor.crearSentenciaSelectTodos();
			PreparedStatement sentencia = conexion.prepareStatement(consultaSQL);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null) {
				while (resultadoConsulta.next()) {
					Barco barco = conversor.crearObjetoBarco(resultadoConsulta);
					barcos.add(barco);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en consultarBarcos: \n" + e);
		}
		return barcos;
	}

	@Override
	public boolean adicionarBarco(Barco barco) {
		try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
			String sentenciaSQL = conversor.crearSentenciaSQL(barco);
			Statement sentencia = conexion.createStatement();
			int cantidadInserciones = sentencia.executeUpdate(sentenciaSQL);
			return (cantidadInserciones > 0);
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en adicionarBarco: \n" + e);
		}
		return false;
	}


	@Override
	public Barco buscarBarco(String matricula) {
		try (Connection conexion = DriverManager.getConnection(cadenaConexion)) {
			String consultaSQL = conversor.crearSentenciaSelectBarco();
			PreparedStatement sentencia = conexion.prepareStatement(consultaSQL);
			sentencia.setString(1, matricula);
			ResultSet datosBarco = sentencia.executeQuery();
			if (datosBarco != null && datosBarco.next()) {
				Barco barco = conversor.crearObjetoBarco(datosBarco);
				return barco;
			}
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en buscarBarco: \n" + e);
		}
		return null;
	}
}