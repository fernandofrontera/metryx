package model.metodologia;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Empresa;
import model.indicador.Indicador;

@Entity
@DiscriminatorValue("TAXCOMP")
public final class CondicionTaxocomparativa extends Condicion {
	
	@JsonProperty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="condiciónTaxativa_id") @Where(clause = "tipo = 'TAX'")
	private CondicionTaxativa condiciónTaxativa;
	
	@JsonProperty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="CondiciónComparativa_id") @Where(clause = "tipo = 'COMP'")
	private CondicionComparativa condiciónComparativa;
	
	public CondicionTaxocomparativa() {}

	CondicionTaxocomparativa(
			@JsonProperty("nombre") String nombre, 
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("númeroDePeríodos") int númeroDePeríodos,
			@JsonProperty("evaluacion") Evaluacion evaluacion,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("valorDeReferencia") Double valor,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluacion, orden);
		condiciónTaxativa = new CondicionTaxativa(nombre, indicador, númeroDePeríodos, evaluacion, orden, valor);
		condiciónComparativa = new CondicionComparativa(nombre, indicador, númeroDePeríodos, evaluacion, orden, prioridad);
	}
	
	public Double obtenerValorDeReferencia() {
		return condiciónTaxativa.obtenerValorDeReferencia();
	}
	
	public Prioridad obtenerPrioridad() {
		return condiciónComparativa.obtenerPrioridad();
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return condiciónComparativa.aplicar(condiciónTaxativa.aplicar(empresas));
	}
	
	CondicionTaxativa obtenerCondiciónTaxativa() {
		return condiciónTaxativa;
	}
	
	CondicionComparativa obtenerCondiciónComparativa() {
		return condiciónComparativa;
	}
	
	public String getTipo()	{
		return "Taxocomparativa";
	}
}