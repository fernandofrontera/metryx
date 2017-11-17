package model.repositorios.fuentes.json;

import java.util.List;

import model.indicador.Indicador;
import model.indicador.IndicadorCalculado;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.fuentes.FuenteDeIndicador;

public class FuenteJsonDeIndicador implements FuenteDeIndicador {
	
	private static final String ARCHIVO_DE_INDICADORES = "indicadores.json";
	
	CodificadorJson coder;
	
	public FuenteJsonDeIndicador(String nombreDeArchivo) {
		coder = new CodificadorJson(nombreDeArchivo, Indicador.class);
	}
	
	public FuenteJsonDeIndicador() {
		this(ARCHIVO_DE_INDICADORES);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Indicador> cargar() {
		
		return (List<Indicador>) coder.read();
	}

	@Override
	public void guardar(RepositorioDeIndicadores repositorio, List<Indicador> indicators) {
		
		coder.write(indicators);		
	}

	@Override
	public List<String> obtenerNombres() {
		return coder.obtenerCampo("name");
	}

	@Override
	public void remover(Indicador indicador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IndicadorCalculado> cargarCalculados() {
		// TODO Auto-generated method stub
		return null;
	}

}
