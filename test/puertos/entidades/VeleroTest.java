package puertos.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Se realizan pruebas al método "calcularCapacidad" de un Velero.
 */
class VeleroTest {

	/**
	 * La capacidad normal de un velero es el 50 % de volumen
	 * (si lleva menos o exactamente 10 pasajeros)
	 */
	@Test
	void testCapacidadPocosPasajeros() {
		Velero velero = new Velero("321","Colombiana",100,8);
		double capacidadEsperada = 50;
		double capacidadVelero = velero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadVelero);
	}

	/** 
	 * Si un velero lleva más de diez pasajeros, a su capacidad
	 * normal se le resta 10
	 */
	@Test
	void testCapacidadMuchosPasajeros() {
		Velero velero = new Velero("654","Peruana",200,20);
		double capacidadEsperada = 90;
		double capacidadVelero = velero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadVelero);
	}
	
	/**
	 * Cuando un velero lleva más de diez pasajeros, a la capacidad
	 * se le resta 10, así que se probará cuando el 
	 * 50 % del volumen es menor a 10 (debe dar capacidad cero).
	 */
	@Test
	void testCapacidadMuchosPasajerosVolumenMenor() {
		Velero velero = new Velero("987","Chilena",10,12);
		double capacidadEsperada = 0;
		double capacidadVelero = velero.calcularCapacidad();
		assertEquals(capacidadEsperada, capacidadVelero);
	}
}
