package model.metodología;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorDeMetodología {
	
	private String nombre;


	private List<CondiciónTaxativa> condicionesTaxativas = new ArrayList<>();
	private List<CondiciónComparativa> condicionesComparativas = new ArrayList<>();
	private List<CondiciónTaxocomparativa> condicionesTaxocomparativas = new ArrayList<>();
	
	public ConstructorDeMetodología() {}
	
	public ConstructorDeMetodología(String nombre){
		this.nombre = nombre;
	}
	
	public ConstructorDeMetodología(Metodología metodologia){
		/*~ Para construir una metodologia en base al estado de una anterior ~*/
		this.nombre = metodologia.obtenerNombre();
		this.condicionesTaxativas = new ArrayList<>(metodologia.getCondicionesTaxativas());
		this.condicionesComparativas = new ArrayList<>(metodologia.getCondicionesComparativas());
		this.condicionesTaxocomparativas = new ArrayList<>(metodologia.getCondicionesTaxocomparativas());
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void agregarCondición(CondiciónTaxativa condiciónTaxativa) {
		condicionesTaxativas.add(condiciónTaxativa);
	}
	
	public void agregarCondición(CondiciónComparativa condiciónComparativa) {
		condicionesComparativas.add(condiciónComparativa);
	}
	
	public void agregarCondición(CondiciónTaxocomparativa condiciónTaxocomparativa) {
		condicionesTaxocomparativas.add(condiciónTaxocomparativa);
		//condicionesTaxativas.add(condiciónTaxocomparativa.obtenerCondiciónTaxativa());
		//condicionesComparativas.add(condiciónTaxocomparativa.obtenerCondiciónComparativa());
	}
	
	public List<Condición> getCondiciones(){
		List<Condición> listaCondiciones = new ArrayList<>(this.condicionesTaxativas);
		listaCondiciones.addAll(this.condicionesComparativas);
		listaCondiciones.addAll(this.condicionesTaxocomparativas);
			
		return listaCondiciones;
	}
	
	public void eliminarCondicion(String nombreCondicion){
		this.condicionesTaxativas = condicionesTaxativas.stream()
														.filter(c-> !c.getNombre().equals(nombreCondicion))
														.collect(Collectors.toList());
		
		this.condicionesComparativas =	 condicionesComparativas.stream()
																.filter(c-> !c.getNombre().equals(nombreCondicion))
																.collect(Collectors.toList());
		
		this.condicionesTaxocomparativas =	 condicionesTaxocomparativas.stream()
				.filter(c-> !c.getNombre().equals(nombreCondicion))
				.collect(Collectors.toList());
	}
	
	public boolean existeCondicion(String nombreCondicion){
		List<String> nombreCondiciones = new ArrayList<>();
		nombreCondiciones = condicionesTaxativas.stream().map(c-> c.getNombre()).collect(Collectors.toList());
		nombreCondiciones.addAll(condicionesComparativas.stream().map(c->c.getNombre()).collect(Collectors.toList()));
		
		return nombreCondiciones.contains(nombreCondicion);
		
	}
	
	public Metodología construir() {
		return new Metodología(nombre, condicionesTaxativas, condicionesComparativas, condicionesTaxocomparativas);
	}
	
}