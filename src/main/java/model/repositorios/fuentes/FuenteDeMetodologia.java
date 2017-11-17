package model.repositorios.fuentes;

import java.util.List;

import model.metodologia.Metodologia;
import model.repositorios.RepositorioDeMetodologias;


public interface FuenteDeMetodologia {
	
	public List<Metodologia> cargar();
		
	public default void guardar(RepositorioDeMetodologias repositorio, List<Metodologia> metodologias) {}
		
	public default void remover(Metodologia metodologia) {}

	public default void actualizar(Metodologia original, Metodologia nuevo) {}
}
