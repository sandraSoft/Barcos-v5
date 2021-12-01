package puertos.control;

import java.util.List;

import org.json.JSONObject;

import puertos.entidades.Barco;
import puertos.entidades.FabricaBarcos;
import puertos.persistencia.RepositorioBarcos;
import puertos.persistencia.FabricaRepositorio;

/**
 * Clase donde se registran los barcos que llegan al puerto,
 * y tiene la principales funciones del programa (es el control).
 * 
 * @version 4.5
 */
public class Puerto {

	private RepositorioBarcos repositorio;
	private final double VOLUMEN_MAXIMO = 1000;
	
	public Puerto() {
		repositorio = FabricaRepositorio.crearRepositorio("BaseDatos");
	}
	
	public Puerto(RepositorioBarcos repositorio) {
		this.repositorio = repositorio;
	}
	
	/**
	 * Calcula la capacidad de todos los barcos en el puerto,
	 * para poder determinar la carga que puede recibir.
	 * @return	la capacidad total de los barcos, en m3
	 */
	public double calcularCapacidadTotal() {
		List<Barco> barcos = repositorio.consultarBarcos();
		double capacidadTotal = 0;
		for (Barco barco : barcos) {
			capacidadTotal += barco.calcularCapacidad();
		}
		return capacidadTotal;
	}
	
	/**
	 * Se adiciona un barco al puerto, es decir, 
	 * se registra su información y se guarda.
	 * @param datosBarco Objeto JSON con la información completa de un Barco.
	 * @throws BarcoException cuando una regla del negocio no se cumple
	 *  	(como el volumen permitido)
	 */
	public void adicionarBarco(JSONObject datosBarco) throws BarcoException {
		String matricula = datosBarco.getString("matricula");
		double volumen = datosBarco.getDouble("volumen");
		
		if (!validarMatriculaUnica(matricula)) {
			throw new BarcoException("No se puede guardar: "
					+ "Ya existe un barco registrado con esa matrícula");
		}
		
		if (!validarVolumenBarco(volumen)) {
			throw new BarcoException("Volumen incorrecto: "
					+ "debe estar entre cero y mil [0 - 1000]");
		}
		
		Barco barco = FabricaBarcos.crearBarco(datosBarco);
		repositorio.adicionarBarco(barco);
	}

	/**
	 * Valida que la matrícula no esté registrada en la lista de barcos.
	 * @param matricula	la identificación del barco que se desea revisar
	 * @return true si la matrícula es única, o false si ya existe 
	 */
	boolean validarMatriculaUnica(String matricula) {
		Barco barco = repositorio.buscarBarco(matricula);
		if (barco == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Valida que el volumen de un barco se conserve en los rangos permitidos
	 * @param volumen el volumen que se desea evaluar
	 * @return	si el volumen es aceptado o no
	 */
	private boolean validarVolumenBarco(double volumen) {
		if (volumen < 0 || volumen > VOLUMEN_MAXIMO) {
			return false;
		}
		return true;
	}
}
