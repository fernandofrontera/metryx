package model.repositorios.fuentes;

import java.util.List;

import model.metodología.Metodología;
import model.repositorios.RepositorioDeMetodologias;


public interface FuenteDeMetodologia {
	
	public List<Metodología> cargar();
		
	public default void guardar(RepositorioDeMetodologias repositorio, List<Metodología> metodologias) {}
		
	public default void remover(Metodología metodologia) {}

	public default void actualizar(Metodología original, Metodología nuevo) {}
}
