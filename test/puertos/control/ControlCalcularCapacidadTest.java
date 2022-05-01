package puertos.control;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import puertos.persistencia.ListaBarcos;

/**
 * Pruebas del método calcularCapacidadTotal de ControlPuerto
 */
class ControlCalcularCapacidadTest {
	
	/**
	 * Se calcula la capacidad cuando no hay barcos registrados
	 */
	@Test
	void testCalcularSinBarcos() {
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		double capacidadEsperada = 0;
		double capacidad = control.calcularCapacidadTotal();
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
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		
		JSONObject datosVelero1 = new JSONObject().put("matricula","Vel-001")
				.put("nacionalidad","colombiana").put("volumen", 100)
				.put("pasajeros",8).put("tipo","v").put("liquidos", false);
		JSONObject datosVelero2 = new JSONObject().put("matricula","Vel-002")
				.put("nacionalidad","chilena").put("volumen",150)
				.put("pasajeros",15).put("tipo","v").put("liquidos", false); 
		JSONObject datosCarguero1 = new JSONObject().put("matricula","Car-001")
				.put("nacionalidad","peruana").put("volumen",500)
				.put("liquidos",true).put("tipo","c").put("pasajeros", 12); 
		JSONObject datosCarguero2 = new JSONObject().put("matricula","Car-002")
				.put("nacionalidad","mexicano").put("volumen",250)
				.put("liquidos",false).put("tipo","c").put("pasajeros", 12); 
		
		control.adicionarBarco(datosVelero1);
		control.adicionarBarco(datosVelero2);
		control.adicionarBarco(datosCarguero1);
		control.adicionarBarco(datosCarguero2);
		
		double capacidadEsperada = 675;
		double capacidad = control.calcularCapacidadTotal();
		assertEquals(capacidadEsperada,capacidad);
	}
}
