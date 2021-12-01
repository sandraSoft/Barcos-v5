package puertos.persistencia;

/**
 * Permite crear un repositorio de acuerdo con el tipo seleccionado,
 * y así la clase de control (Puerto) solo tiene que conocer la interfaz.
 * Corresponde al patrón de diseño "SIMPLE FACTORY".
 * 
 * @version 1.0
 */
public class FabricaRepositorio {

	/**
	 * Crea un repositorio (objeto que cumple con la interfaz RepositorioBarcos)
	 * @param tipo el tipo de repositorio que se desea crear, 
	 * 	por ejemplo: "BaseDatos", "Orm".
	 * @return el objeto RepositorioBarcos del tipo definido.
	 * 	Si no se recibe un tipo reconocido, se devuelve una lista 
	 * 	(repositorio "falso" para pruebas, en memoria).
	 */
	public static RepositorioBarcos crearRepositorio(String tipo) {
		switch (tipo) {
		  case "BaseDatos":
			  return new BaseDatosBarcos();
		}
		return new ListaBarcos();
	}
}
