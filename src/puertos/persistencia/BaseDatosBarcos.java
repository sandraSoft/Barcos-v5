package puertos.persistencia;

import java.sql.Connection;
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
	
	private GestorConexionBd gestorConexion;
	private ConversorSqlBarcos conversorSql;
	
	public BaseDatosBarcos() {
		this.gestorConexion = new GestorConexionBd();
		this.conversorSql = new ConversorSqlBarcos();
	}
	
	@Override
	public List<Barco> consultarBarcos() {
		List<Barco> barcos = new ArrayList<Barco>();
		Connection conexion = null;
		try {
			conexion = gestorConexion.abrirConexion();
			String consultaSQL = conversorSql.crearSentenciaSelectTodos();
			PreparedStatement sentencia = conexion.prepareStatement(consultaSQL);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null) {
				while (resultadoConsulta.next()) {
					Barco barco = conversorSql.instanciarBarco(resultadoConsulta);
					barcos.add(barco);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en consultarBarcos: \n" + e);
		} finally {
			gestorConexion.cerrarConexion(conexion);
		}
		return barcos;
	}

	@Override
	public boolean adicionarBarco(Barco barco) {
		Connection conexion = null;
		try {
			conexion = gestorConexion.abrirConexion();
			String sentenciaSQL = conversorSql.crearSentenciaInsert(barco);
			Statement sentencia = conexion.createStatement();
			int cantidadInserciones = sentencia.executeUpdate(sentenciaSQL);
			return (cantidadInserciones > 0);
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en adicionarBarco: \n" + e);
		} finally {
			gestorConexion.cerrarConexion(conexion);
		}
		return false;
	}

	@Override
	public Barco buscarBarco(String matricula) {
		Connection conexion = null;
		try {
			conexion = gestorConexion.abrirConexion();
			String consultaSQL = conversorSql.crearSentenciaSelectUno();
			PreparedStatement sentencia = conexion.prepareStatement(consultaSQL);
			sentencia.setString(1, matricula);
			ResultSet resultadoConsulta = sentencia.executeQuery();
			if (resultadoConsulta != null && resultadoConsulta.next()) {
				return conversorSql.instanciarBarco(resultadoConsulta);
			}
		} catch (SQLException e) {
			System.err.println("Error con la base de datos en buscarBarco: \n" + e);
		} finally {
			gestorConexion.cerrarConexion(conexion);
		}
		return null;
	}
}
