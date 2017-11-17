package model.metodología;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Empresa;
import model.Entidad;

@Entity(name="Metodologías")
public class Metodología extends Entidad {
	
	@JsonProperty
	private String nombre;
	

	@JsonProperty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="metodologia_id") @Where(clause = "tipo = 'COMP'")
	List<CondiciónComparativa> condicionesComparativas;
	
	@JsonProperty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval= true)  
	@JoinColumn(name="metodologia_id") @Where(clause = "tipo = 'TAX'")
	List<CondiciónTaxativa> condicionesTaxativas;
		
	@JsonProperty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="metodologia_id") @Where(clause = "tipo = 'TAXCOMP'")
	List<CondiciónTaxocomparativa> condicionesTaxocomparativas;
	
	@SuppressWarnings("unused")
	private Metodología() {}
	
	@JsonCreator
	Metodología(
			@JsonProperty("nombre") String nombre,
			@JsonProperty("condicionesTaxativas") List<CondiciónTaxativa> condicionesTaxativas,
			@JsonProperty("condicionesComparativas") List<CondiciónComparativa> condicionesComparativas,
			@JsonProperty("condicionesTaxocomparativas") List<CondiciónTaxocomparativa> condicionesTaxocomparativas) {
		this.nombre = nombre;
		this.condicionesTaxativas = condicionesTaxativas;
		this.condicionesComparativas = condicionesComparativas;
		this.condicionesTaxocomparativas = condicionesTaxocomparativas;
	}

	public List<CondiciónComparativa> getCondicionesComparativas() {
		return condicionesComparativas;
	}

	public List<CondiciónTaxativa> getCondicionesTaxativas() {
		return condicionesTaxativas;
	}

	public List<CondiciónTaxocomparativa> getCondicionesTaxocomparativas() {
		return condicionesTaxocomparativas;
	}

	public String obtenerNombre() {
		return nombre;
	}
		
	public boolean esAplicable(List<Empresa> empresas) {
		return Stream.concat(condicionesTaxativas.stream(), condicionesComparativas.stream())
				.allMatch(condición -> condición.esAplicable(empresas));
	}

	public List<Empresa> aplicar(List<Empresa> empresas) {
		return aplicarCondicionesComparativas(aplicarCondicionesTaxativas(empresas));
	}
	
	private List<Empresa> aplicarCondicionesTaxativas(List<Empresa> empresas) {
		for(Condición condiciónTaxativa : condicionesTaxativas) {
			empresas = condiciónTaxativa.aplicar(empresas);
		}
		for(CondiciónTaxocomparativa condicionTaxocomparativa : condicionesTaxocomparativas) {
			empresas = condicionTaxocomparativa.obtenerCondiciónTaxativa().aplicar(empresas);
		}
		return empresas;
	}
	
	private List<Empresa> aplicarCondicionesComparativas(List<Empresa> empresas) {
		if (condicionesComparativas.isEmpty()) return empresas;
		Map<String, Integer> pesos = new HashMap<>();
		
		for(CondiciónComparativa condiciónComparativa : condicionesComparativas) {
			actualizarPesos(pesos, condiciónComparativa.obtenerPrioridad(), condiciónComparativa.aplicar(empresas));
		}
		
		for(CondiciónTaxocomparativa condicionTaxocomparativa : condicionesTaxocomparativas) {
			actualizarPesos(pesos, condicionTaxocomparativa.obtenerCondiciónComparativa().obtenerPrioridad(), condicionTaxocomparativa.obtenerCondiciónComparativa().aplicar(empresas));
		}
		
		return obtenerEmpresasOrdenadasPorPeso(pesos, empresas);
	}

	private void actualizarPesos(Map<String, Integer> pesos, Prioridad prioridad, List<Empresa> empresas) {
		// Agrego puntos segun posición
		int i = empresas.size();
		for(String empresa : empresas.stream().map(e -> e.getNombre()).collect(Collectors.toList())) {
			Integer peso = pesos.get(empresa);
			if(peso == null) peso = 0;
			
			peso += prioridad.obtenerValor() * i--;
			pesos.put(empresa, peso); // no reemplaza; si existe lo actualiza			
		}		
	}
	
	public void eliminarCondicion(Condición condicion) {
		this.condicionesComparativas.remove(condicion);
		this.condicionesTaxativas.remove(condicion);
	}
	
	private List<Empresa> obtenerEmpresasOrdenadasPorPeso(Map<String, Integer> pesos, List<Empresa> empresas) {
		return pesos.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(e -> obtenerEmpresaDeNombre(e.getKey(), empresas)).collect(Collectors.toList());
	}
	
	private Empresa obtenerEmpresaDeNombre(String nombre, List<Empresa> empresas) {
		return empresas.stream().filter(empresa -> empresa.getNombre().equals(nombre)).findFirst().get();
	}

	public void actualizar(List<CondiciónTaxativa> condicionesTaxativas,
			List<CondiciónComparativa> condicionesComparativas,
			List<CondiciónTaxocomparativa> condicionesTaxocomparativas) {
		this.condicionesTaxativas = condicionesTaxativas;
		this.condicionesComparativas = condicionesComparativas;
		this.condicionesTaxocomparativas = condicionesTaxocomparativas;
	}
}
