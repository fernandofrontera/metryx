package model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.indicador.Indicador;
import model.indicador.IndicadorCalculado;
import model.repositorios.fuentes.FuenteDeIndicador;

public class RepositorioDeIndicadores {

	private FuenteDeIndicador source;

	public List<String> nombres;
	public List<Indicador> indicators;
	public List<IndicadorCalculado> indicadoresCalculados;


	public RepositorioDeIndicadores(FuenteDeIndicador source) {
		this.source = source;
		this.nombres = source.obtenerNombres();
	}
	
	public List<IndicadorCalculado> getIndicadoresCalculados() {
		return indicadoresCalculados;
	}

	public void setIndicadoresCalculados(List<IndicadorCalculado> indicadoresCalculados) {
		this.indicadoresCalculados = indicadoresCalculados;
	}
	public void setIndicators(List<Indicador> indicators) {
		this.indicators = new ArrayList<>(indicators);
	}
	
	public void crearIndicadores() { 
		this.indicators = new ArrayList<>(source.cargar());
		this.indicadoresCalculados = new ArrayList<>(source.cargarCalculados());
	}
	public List<Indicador> todos() {
		return indicators;
	}
	
	public Indicador encontrar(String name) {
		return this.indicators.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
	}

	public void agregar(Indicador indicator) {
		this.indicators.add(indicator);
		guardar();
	}

	public boolean existeIndicador(String nombre){
		return this.nombres.stream().anyMatch(n -> n.equals(nombre));
	}
	
	public void reemplazar(Indicador viejo, Indicador nuevo) {
		this.indicators.remove(viejo);
		this.indicators.add(nuevo);
		this.source.actualizar(viejo, nuevo);
		guardar();
	}

	public void remover(Indicador indicador) {
		this.indicators.remove(indicador);
		this.source.remover(indicador);
		guardar();
	}

	private void guardar() {
		this.source.guardar(this,this.indicators);
		actualizarNombres(); // ya que se actualizo la lista de indicadores
	}

	private void actualizarNombres() {
		this.nombres = this.indicators.stream().map(i -> i.getName()).collect(Collectors.toList());
	}
	
	public double getValorIndicador(Indicador indicador, String empresa, Short periodo) {
		
		return this.source.getValorIndicador(indicador, empresa, periodo);
	}

}
