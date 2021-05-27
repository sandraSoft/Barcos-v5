package puertos.control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import puertos.persistencia.ListaBarcos;

/**
 * Pruebas del método calcularCapacidadTotal del Puerto
 */
class PuertoCalcularCapacidadTest {
	
	/**
	 * Se calcula la capacidad cuando no hay barcos registrados
	 */
	@Test
	void testCalcularSinBarcos() {
		Puerto puerto = new Puerto(new ListaBarcos());
		double capacidadEsperada = 0;
		double capacidad = puerto.calcularCapacidadTotal();
		assertEquals(capacidadEsperada,capacidad);
	}

	/**
	 * Se calcula la capacidad con varios barcos:
	 * un velero con menos de 10 pasajeros y otro con más de 10,
	 * un carguero con líquidos y otro que no.
	 * @throws BarcoException 
	 */
	@Test
	void testCalcularValido() throws BarcoException  {
		Puerto puerto = new Puerto(new ListaBarcos());
		puerto.adicionarBarco("Vel-001", "colombiana", 100, 'v', 8, false);
		puerto.adicionarBarco("Vel-002", "chilena", 150, 'v', 15, false);
		puerto.adicionarBarco("Car-001", "peruana", 500, 'c', 15, true);
		puerto.adicionarBarco("Car-002", "mexicano", 250, 'c', 25, false);
		double capacidadEsperada = 675;
		double capacidad = puerto.calcularCapacidadTotal();
		assertEquals(capacidadEsperada,capacidad);
	}
}
