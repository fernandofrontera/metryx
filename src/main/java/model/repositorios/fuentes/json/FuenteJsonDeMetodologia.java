package model.repositorios.fuentes.json;

import java.util.List;

import model.metodología.Metodología;
import model.repositorios.RepositorioDeMetodologias;
import model.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJsonDeMetodologia implements FuenteDeMetodologia {

	private static final String ARCHIVO_DE_METODOLOGIAS = "Metodologias.json";
	
	public CodificadorJson coder;
	
	public FuenteJsonDeMetodologia(String nombreDeArchivo){
		coder = new CodificadorJson(nombreDeArchivo, Metodología.class);
	}
	
	public FuenteJsonDeMetodologia() {
		this(ARCHIVO_DE_METODOLOGIAS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Metodología> cargar() {
		
		return (List<Metodología>) coder.read();
	}

	@Override
	public void guardar(RepositorioDeMetodologias repositorio, List<Metodología> metodologias) {
		coder.write(metodologias);
	}

}
