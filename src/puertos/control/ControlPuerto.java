package puertos.control;

import java.util.List;

import org.json.JSONObject;

import puertos.entidades.Barco;
import puertos.entidades.FabricaBarcos;
import puertos.persistencia.ListaBarcos;
import puertos.persistencia.RepositorioBarcos;

/**
 * Clase donde se registran los barcos que llegan al puerto, y tiene la
 * principales funciones del programa (lógica el negocio).
 * 
 * @version 4.5
 */
public class ControlPuerto {

	private RepositorioBarcos repositorio;
	final double VOLUMEN_MAXIMO = 1000;

	public ControlPuerto() {
		repositorio = new ListaBarcos();
	}
	
	public ControlPuerto(RepositorioBarcos repositorio) {
		this.repositorio = repositorio;
	}

	/**
	 * Calcula la capacidad de todos los barcos en el puerto, 
	 * para poder determinar la carga que puede recibir.
	 * 
	 * @return la capacidad total de los barcos, en m3
	 */
	public double calcularCapacidadTotal() {
		double capacidadTotal = 0;
		List<Barco> barcos = repositorio.consultarBarcos();
		for (Barco barco : barcos) {
			capacidadTotal += barco.calcularCapacidad();
		}
		return capacidadTotal;
	}

	/**
	 * Se adiciona un barco al puerto, es decir, se registra su información y se
	 * guarda.
	 * 
	 * @param datosBarco Objeto JSON con la información completa de un Barco
	 * @throws BarcoException cuando alguna regla del negocio no se cumple
	 */
	public void adicionarBarco(JSONObject datosBarco) throws BarcoException {
		String matricula = datosBarco.getString("matricula");
		double volumen = datosBarco.getDouble("volumen");

		if (existeMatricula(matricula)) {
			throw new BarcoException("No se puede guardar: " +
						"Ya existe un barco registrado con esa matrícula");
		}
		if (!esVolumenPermitido(volumen)) {
			throw new BarcoException("Volumen incorrecto: " +
						"debe estar entre 0 y " + VOLUMEN_MAXIMO);
		}

		Barco barcoNuevo = new FabricaBarcos().crearBarco(datosBarco);
		if (barcoNuevo != null) {
			repositorio.adicionarBarco(barcoNuevo);
		}
	}

	/**
	 * Valida si la matrícula está o no registrada en el puerto.
	 * 
	 * @return true si la matrícula ya existe (ya hay un barco registrado) o false
	 *         si no existe (es decir, no hay registro).
	 */
	boolean existeMatricula(String matricula) {
		Barco barcoBuscado = repositorio.buscarBarco(matricula);
		return (barcoBuscado != null);
	}

	/**
	 * Valida que el volumen de un barco se conserve en los rangos permitidos
	 * 
	 * @param volumen el volumen que se desea evaluar
	 * @return si el volumen es aceptado o no
	 */
	private boolean esVolumenPermitido(double volumen) {
		if (volumen < 0 || volumen > VOLUMEN_MAXIMO) {
			return false;
		}
		return true;
	}
}
