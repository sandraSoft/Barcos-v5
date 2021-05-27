package puertos.persistencia;

import java.util.ArrayList;
import java.util.List;
import puertos.entidades.Barco;

/**
 * Repositorio temporal que tiene la lista de barcos en memoria.
 * Generalmente usado para pruebas.
 * @version 1.0
 */
public class ListaBarcos implements RepositorioBarcos {
	
	private List<Barco> barcos;

	public ListaBarcos() {
		barcos = new ArrayList<Barco>();
	}
	
	@Override
	public List<Barco> consultarBarcos() {
		return barcos;
	}
	
	@Override
	public boolean adicionarBarco(Barco barco) {
		return barcos.add(barco);
	}
	
	@Override
	public Barco buscarBarco(String matricula) {
		for (Barco barco : barcos) {
			if (barco.getMatricula().equals(matricula)) {
				return barco;
			}
		}
		return null;
	}
}
