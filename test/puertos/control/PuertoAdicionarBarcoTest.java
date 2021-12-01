package puertos.control;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import puertos.entidades.Carguero;
import puertos.entidades.Velero;
import puertos.persistencia.ListaBarcos;

/**
 * Pruebas del método adicionarBarco del Puerto.
 */
class PuertoAdicionarBarcoTest {

	/**
	 * Se adiciona un barco con datos correctos, es decir:
	 *   matrícula única y volumen en el rango permitido.
	 */
	@Test
	void testAdicionarBarcosValidos() throws BarcoException {
		Puerto puerto = new Puerto(new ListaBarcos());
		JSONObject datosBarco = new JSONObject(
				new Velero("123", "colombiana", 200, 10));
		datosBarco.put("tipo","velero").put("liquidos",true);
		
		assertTrue(puerto.validarMatriculaUnica("123"));
		puerto.adicionarBarco(datosBarco);
		assertFalse(puerto.validarMatriculaUnica("123"));
	}

	/**
	 * Se verifica que no permita adicionar un barco con matrícula repetida
	 */
	@Test
	void testAdicionarBarcoRepetido() throws BarcoException {
		Puerto puerto = new Puerto(new ListaBarcos());
		JSONObject datosBarco = new JSONObject(
				new Velero("245", "peruana", 100, 5));
		datosBarco.put("tipo","velero").put("liquidos",false);
		
		assertTrue(puerto.validarMatriculaUnica("245"));
		puerto.adicionarBarco(datosBarco);
		assertFalse(puerto.validarMatriculaUnica("245"));
		assertThrows(BarcoException.class,
				() -> puerto.adicionarBarco(datosBarco));
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen negativo
	 */
	@Test
	void testAdicionarBarcoVolumenNegativo() {
		Puerto puerto = new Puerto(new ListaBarcos());
		JSONObject datosBarco = new JSONObject(
				new Velero("789", "italiano", -79, 10));
		datosBarco.put("tipo","velero").put("liquidos",true);
		assertThrows(BarcoException.class,
				() ->  puerto.adicionarBarco(datosBarco));
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen mayor 
	 * a lo permitido (en este caso 1000)
	 */
	@Test
	void testAdicionarBarcoVolumenAlto() {
		Puerto puerto = new Puerto(new ListaBarcos());
		JSONObject datosBarco = new JSONObject(
				new Carguero("003", "canadiense", 1500, false));
		datosBarco.put("tipo","carguero").put("pasajeros",30);
		assertThrows(BarcoException.class,
				() ->  puerto.adicionarBarco(datosBarco));
	}
}
