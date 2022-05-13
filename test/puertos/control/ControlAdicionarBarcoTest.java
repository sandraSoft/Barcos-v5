package puertos.control;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import puertos.persistencia.ListaBarcos;

/**
 * Pruebas del método adicionarBarco de ControlPuerto.
 */
class ControlAdicionarBarcoTest {

	/**
	 * Adicionar un barco con datos correctos
	 * @throws BarcoException 
	 */
	@Test
	void adicionarVeleroTest() throws BarcoException {
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		JSONObject datosVelero = new JSONObject().put("matricula","123")
				.put("nacionalidad","colombiana").put("volumen", 200)
				.put("pasajeros",10).put("tipo","v").put("liquidos", true);
		
		assertFalse(control.existeMatricula("123"));
		control.adicionarBarco(datosVelero);
		assertTrue(control.existeMatricula("123"));
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con matrícula repetida
	 */
	@Test
	void testAdicionarBarcoRepetido() throws BarcoException {
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		JSONObject datosVelero = new JSONObject().put("matricula","245")
				.put("nacionalidad","peruana").put("volumen", 100)
				.put("pasajeros",5).put("tipo","v").put("liquidos", false);
		
		assertFalse(control.existeMatricula("245"));
		control.adicionarBarco(datosVelero);
		assertTrue(control.existeMatricula("245"));   //primera vez que se adiciona
		assertThrows(Exception.class, 
				() -> control.adicionarBarco(datosVelero)); 	// segunda vez (repetido)
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen negativo
	 */
	@Test
	void testAdicionarBarcoVolumenNegativo() {
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		
		JSONObject datosVelero = new JSONObject().put("matricula","789")
				.put("nacionalidad","italiana").put("volumen", -79)
				.put("pasajeros",10).put("tipo","v").put("liquidos", true);
		
		assertThrows(BarcoException.class,
				() ->  control.adicionarBarco(datosVelero));
		assertFalse(control.existeMatricula("789"));
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen mayor 
	 * a lo permitido (en este caso 1000)
	 */
	@Test
	void testAdicionarBarcoVolumenAlto() {
		ControlPuerto control = new ControlPuerto(new ListaBarcos());
		double volumen = control.VOLUMEN_MAXIMO + 500;
		
		JSONObject datosCarguero = new JSONObject().put("matricula","003")
				.put("nacionalidad","canadiense").put("volumen", volumen)
				.put("pasajeros",30).put("tipo","c").put("liquidos", false);
		
		assertThrows(BarcoException.class,
				() ->  control.adicionarBarco(datosCarguero));
		assertFalse(control.existeMatricula("003"));
	}
}
