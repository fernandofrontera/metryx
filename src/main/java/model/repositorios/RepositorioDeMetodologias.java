package model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.metodología.Metodología;
import model.repositorios.fuentes.FuenteDeMetodologia;

public class RepositorioDeMetodologias {
	
	private FuenteDeMetodologia source;
	
	public List<Metodología> metodologias;
	
	

	public RepositorioDeMetodologias(FuenteDeMetodologia source){
		
		this.source = source;
		this.metodologias = new ArrayList<>(this.source.cargar());
	}
	
	public void setMetodologias(List<Metodología> metodologias) {
		this.metodologias = new ArrayList<>(metodologias); //fixme fede
	}
	
	public List<Metodología> todos(){
		return this.metodologias;
	}
	
	public Metodología encontrar(String nombre){
		return this.metodologias.stream().filter(c -> c.obtenerNombre().equals(nombre)).findFirst().orElse(null);
	}
	
	public void agregar(Metodología metodologia) {
		this.metodologias.add(metodologia);
		guardar();
	}

	public void remover(Metodología metodologia) {
		this.metodologias.remove(metodologia);
		source.remover(metodologia);
		guardar();
	}
	
	public void reemplazar(Metodología oldMetodologia, Metodología newMetodologia) {
		this.metodologias.remove(oldMetodologia);
		this.metodologias.add(newMetodologia);
		this.source.actualizar(oldMetodologia, newMetodologia);
		guardar();
	}

	private void guardar() {
		this.source.guardar(this, this.metodologias);
	}
	
	public List<String> obtenerNombres(){
		
		return this.metodologias.stream().map(m -> m.obtenerNombre()).collect(Collectors.toList());
	}
	

}
