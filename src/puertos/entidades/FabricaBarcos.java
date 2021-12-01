package puertos.entidades;

import org.json.JSONObject;

/**
 * Permite crear un barco de acuerdo con el tipo seleccionado
 * (que llega como uno de los valores del JSON que llega por parámetro),
 * y así las clases que usan los barcos no tiene que conocer las hijas.
 * Corresponde al patrón de diseño "SIMPLE FACTORY".
 * 
 * @version 1.0
 */
public class FabricaBarcos {
	
	/**
	 * Crea un objeto barco (una de las hijas: velero o carguero, por ejemplo).
	 * @param datosBarco datos del barco en un objeto JSON
	 * @return el objeto Barco creado, o null sino se especificó
	 * 	un tipo válido
	 */
	public static Barco crearBarco(JSONObject datosBarco) {
		Barco barco = null;
		
		String matricula = datosBarco.getString("matricula");
		String nacionalidad = datosBarco.getString("nacionalidad");
		double volumen = datosBarco.getDouble("volumen");
		char tipo = datosBarco.getString("tipo").charAt(0);
		int pasajeros = datosBarco.getInt("pasajeros");
		boolean liquidos = datosBarco.getBoolean("liquidos");
		
		switch (tipo) {
		  case 'v':
		  case 'V':
			barco = new Velero(matricula, nacionalidad, volumen, pasajeros);
			break;
		  case 'c':
		  case 'C':
			barco = new Carguero(matricula, nacionalidad, volumen, liquidos);
			break;
		}
		return barco;
	}
}
