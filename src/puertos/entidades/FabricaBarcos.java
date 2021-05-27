package puertos.entidades;

/**
 * Permite crear un barco de acuerdo con el tipo seleccionado
 * (que llega como uno de los parámetros),y así las clases
 * que usan los barcos no tiene que conocer las hijas, solo la
 * clase padre.
 * Corresponde al patrón de diseño "SIMPLE FACTORY"
 * 
 * @version 1.0
 */
public class FabricaBarcos {
	
	/**
	 * Crea un nuevo barco a partir de los parámetros recibidos.
	 * @param matricula	el número de matrícula del barco, que lo identifica
	 * @param nacionalidad	la nacionalidad del barco (dada por el país de origen)
	 * @param volumen	el espacio total del barco, en m3
	 * @param tipo	de qué tipo es el barco: 'v' para velero, 'c' para carguero
	 * @param pasajeros	la cantidad de pasajeros que lleva el barco
	 * @param liquidos	indica (true/false) si puede llevar líquidos o no
	 * @return	el objeto Barco creado,
	 * 		o null si no se especificó un tipo válido de barco.
	 */
	public static Barco crearBarco(String matricula, String nacionalidad, double volumen, 
			char tipo, int pasajeros, boolean liquidos) {
		
		switch (tipo) {
		  case 'v':
		  case 'V': 
			  return new Velero(matricula, nacionalidad, volumen, pasajeros);
		  case 'c':
		  case 'C':
			  return new Carguero(matricula, nacionalidad, volumen, liquidos);
		}
		return null;
	}
}
