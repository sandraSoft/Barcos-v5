package puertos.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import puertos.persistencia.ListaBarcos;

/**
 * Pruebas del método adicionarBarco del Puerto.
 */
class PuertoAdicionarBarcoTest {

	/**
	 * Se adicionan un velero y un carguero, con matrículas únicas
	 * y volumen en el rango permitido
	 * @throws BarcoException 
	 */
	@Test
	void testAdicionarBarcosValidos() throws BarcoException {
		Puerto puerto = new Puerto(new ListaBarcos());
		boolean adicionVelero = puerto.adicionarBarco("Vel-001", "colombiana", 100, 'v', 8, false);
		boolean adicionCarguero = puerto.adicionarBarco("Car-001", "peruana", 500, 'c', 15, true);
		assertTrue(adicionVelero);
		assertTrue(adicionCarguero);
	}

	/**
	 * Se verifica que no permita adicionar un barco con matrícula repetida
	 */
	@Test
	void testAdicionarBarcoRepetido() throws BarcoException {
		Puerto puerto = new Puerto(new ListaBarcos());
		puerto.adicionarBarco("Vel-002", "chilena", 50, 'v', 5, false);
		boolean adicionRepetido = puerto.adicionarBarco("Vel-002", "chilena", 150, 'v', 15, false);
		assertFalse(adicionRepetido);
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen negativo
	 */
	@Test
	void testAdicionarBarcoVolumenNegativo() {
		Puerto puerto = new Puerto(new ListaBarcos());
		assertThrows(BarcoException.class,
				() ->  puerto.adicionarBarco("Car-002", "mexicano", -250, 'c', 25, false));
	}
	
	/**
	 * Se verifica que no permita adicionar un barco con volumen mayor 
	 * a lo permitido (en este caso 1000)
	 */
	@Test
	void testAdicionarBarcoVolumenAlto() {
		Puerto puerto = new Puerto(new ListaBarcos());
		assertThrows(BarcoException.class,
				() ->  puerto.adicionarBarco("Car-003", "canadiense", 1500, 'c', 30, true));
	}
}
