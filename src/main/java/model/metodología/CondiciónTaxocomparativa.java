package model.metodología;

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
public final class CondiciónTaxocomparativa extends Condición {
	
	@JsonProperty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="condiciónTaxativa_id") @Where(clause = "tipo = 'TAX'")
	private CondiciónTaxativa condiciónTaxativa;
	
	@JsonProperty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="CondiciónComparativa_id") @Where(clause = "tipo = 'COMP'")
	private CondiciónComparativa condiciónComparativa;
	
	public CondiciónTaxocomparativa() {}

	CondiciónTaxocomparativa(
			@JsonProperty("nombre") String nombre, 
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("númeroDePeríodos") int númeroDePeríodos,
			@JsonProperty("evaluacion") Evaluación evaluación,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("valorDeReferencia") Double valor,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluación, orden);
		condiciónTaxativa = new CondiciónTaxativa(nombre, indicador, númeroDePeríodos, evaluación, orden, valor);
		condiciónComparativa = new CondiciónComparativa(nombre, indicador, númeroDePeríodos, evaluación, orden, prioridad);
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
	
	CondiciónTaxativa obtenerCondiciónTaxativa() {
		return condiciónTaxativa;
	}
	
	CondiciónComparativa obtenerCondiciónComparativa() {
		return condiciónComparativa;
	}
	
	public String getTipo()	{
		return "Taxocomparativa";
	}
}