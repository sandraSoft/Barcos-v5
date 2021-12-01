package puertos.control;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import puertos.entidades.Carguero;
import puertos.entidades.Velero;
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
		
		JSONObject datosVelero1 = new JSONObject(
				new Velero("Vel-001", "colombiana", 100, 8));
		datosVelero1.put("tipo","velero").put("liquidos",false);
		JSONObject datosVelero2 = new JSONObject(
				new Velero("Vel-002", "chilena", 150, 15));
		datosVelero2.put("tipo","velero").put("liquidos",false);
		JSONObject datosCarguero1 = new JSONObject(
				new Carguero("Car-001", "peruana", 500, true));
		datosCarguero1.put("tipo","carguero").put("pasajeros",12);
		JSONObject datosCarguero2 = new JSONObject(
				new Carguero("Car-002", "mexicano", 250, false));
		datosCarguero2.put("tipo","carguero").put("pasajeros",12);
		
		puerto.adicionarBarco(datosVelero1);
		puerto.adicionarBarco(datosVelero2);
		puerto.adicionarBarco(datosCarguero1);
		puerto.adicionarBarco(datosCarguero2);
		double capacidadEsperada = 675;
		double capacidad = puerto.calcularCapacidadTotal();
		assertEquals(capacidadEsperada,capacidad);
	}
}
