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
	@JoinColumn(name="condicionTaxativa_id") @Where(clause = "tipo = 'TAX'")
	private CondicionTaxativa condicionTaxativa;
	
	@JsonProperty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval= true) 
	@JoinColumn(name="CondicionComparativa_id") @Where(clause = "tipo = 'COMP'")
	private CondicionComparativa condicionComparativa;
	
	public CondicionTaxocomparativa() {}

	CondicionTaxocomparativa(
			@JsonProperty("nombre") String nombre, 
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("numeroDePeriodos") int numeroDePeriodos,
			@JsonProperty("evaluacion") Evaluacion evaluacion,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("valorDeReferencia") Double valor,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, numeroDePeriodos, evaluacion, orden);
		condicionTaxativa = new CondicionTaxativa(nombre, indicador, numeroDePeriodos, evaluacion, orden, valor);
		condicionComparativa = new CondicionComparativa(nombre, indicador, numeroDePeriodos, evaluacion, orden, prioridad);
	}
	
	public Double obtenerValorDeReferencia() {
		return condicionTaxativa.obtenerValorDeReferencia();
	}
	
	public Prioridad obtenerPrioridad() {
		return condicionComparativa.obtenerPrioridad();
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return condicionComparativa.aplicar(condicionTaxativa.aplicar(empresas));
	}
	
	CondicionTaxativa obtenerCondicionTaxativa() {
		return condicionTaxativa;
	}
	
	CondicionComparativa obtenerCondicionComparativa() {
		return condicionComparativa;
	}
	
	public String getTipo()	{
		return "Taxocomparativa";
	}
}