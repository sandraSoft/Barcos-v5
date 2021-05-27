package puertos.persistencia;

import java.util.List;

import puertos.entidades.Barco;

/**
 * Servicios relacionados con la gestión de los datos de los barcos
 * en un repositorio (consultar, adicionar, buscar)
 * @version 1.0
 */
public interface RepositorioBarcos {
	
	/**
	 * Adiciona un barco al repositorio para hacerlo persistente
	 * @param barco el objeto barco que se desea guardar,
	 * 			debe ser diferente de null
	 * @return un valor booleano indicando si se pudo guardar en el repositorio o no
	 * 		(por alguna restricción en el repositorio, no por reglas del negocio)
	 */
	public abstract boolean adicionarBarco(Barco barco);
	
	/**
	 * Busca un barco en la base de datos a partir de su matrícula
	 * @param matricula	el número de matrícula del barco que se desea buscar,
	 * 			debe ser diferente de null
	 * @return	el objeto barco con la matrícula dada, o null si no se encuentra.
	 */
	public abstract Barco buscarBarco(String matricula);
	
	/**
	 * Consulta toda la lista de los barcos que hay registrados
	 * @return	la lista (List) con los barcos.
	 * 			En caso de no existir barcos registrados retorna una lista vacía.
	 */
	public List<Barco> consultarBarcos();
	
}
