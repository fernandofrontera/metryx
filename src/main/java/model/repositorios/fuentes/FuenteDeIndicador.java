package model.repositorios.fuentes;

import java.util.List;

import model.indicador.Indicador;
import model.indicador.IndicadorCalculado;
import model.repositorios.RepositorioDeIndicadores;

public interface FuenteDeIndicador {

	public List<Indicador> cargar();
	
	public List<IndicadorCalculado> cargarCalculados();
	
	public List<String> obtenerNombres();
	
	public void guardar(RepositorioDeIndicadores repositorio, List<Indicador> indicators);

	public void remover(Indicador indicador);
	
	public default void removerCalculados(Indicador indicador) {}

	public default void actualizar(Indicador original, Indicador nuevo) {}
	
	public default double getValorIndicador(Indicador indicador, String empresa, Short periodo) {return 0;}
}
