package puertos.entidades;

/**
 * Un barco que transporta carga entre puertos, tiene buena capacidad de carga.
 * 
 * @version 2.5
 */
public class Carguero extends Barco {
	private boolean liquidos;

	/**
	 * @see puertos.entidades.Barco#Barco(String, String, double)
	 * @param liquidos	indicación de si puede llevar líquidos o no
	 */
	public Carguero(String matricula, String nacionalidad, double volumen,
			boolean liquidos) {
		super(matricula, nacionalidad, volumen);
		this.liquidos = liquidos;
	}

	public boolean getLiquidos() {
		return this.liquidos;
	}

	@Override
	public double calcularCapacidad() {
		double capacidad = getVolumen() * 0.8;
		if (this.liquidos) {
			capacidad-= 40;
		}
		return (capacidad < 0) ? 0 : capacidad;
	}
}
