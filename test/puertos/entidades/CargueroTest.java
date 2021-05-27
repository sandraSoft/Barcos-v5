package puertos.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Se realizan pruebas al método "calcularCapacidad" de un Carguero.
 */
class CargueroTest {

	/**
	 * La capacidad normal de un carguero es el 80 % de volumen
	 */
	@Test
	void testCapacidadNormalSinLiquidos() {
		Carguero carguero = new Carguero("123","Colombiana",100,false);
		double capacidadEsperada = 80;
		double capacidadCarguero = carguero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadCarguero);
	}

	/** 
	 * Si un carguero lleva líquidos, a su capacidad normal
	 * se le debe restar 40 
	 */
	@Test
	void testCapacidadNormalConLiquidos() {
		Carguero carguero = new Carguero("456","Peruana",200,true);
		double capacidadEsperada = 120;
		double capacidadCarguero = carguero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadCarguero);
	}
	
	/**
	 * Cuando un carguero lleva líquidos, a la capacidad
	 * se le resta 40, así que se probará cuando el 
	 * 80 % del volumen es menor a 40 (debe dar capacidad cero).
	 */
	@Test
	void testCapacidadConLiquidosVolumenMenor() {
		Carguero carguero = new Carguero("789","Chilena",30,true);
		double capacidadEsperada = 0;
		double capacidadCarguero = carguero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadCarguero);
	}

}
