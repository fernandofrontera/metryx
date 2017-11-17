package model.metodología;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Empresa;
import model.indicador.Indicador;

@Entity
@DiscriminatorValue("COMP")
public final class CondiciónComparativa extends Condición {
	
	@JsonProperty
	private Prioridad prioridad;

	public CondiciónComparativa() {}

	@JsonCreator
	CondiciónComparativa(
			@JsonProperty("nombre") String nombre,
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("númeroDePeríodos") int númeroDePeríodos,
			@JsonProperty("evaluacion") Evaluación evaluación,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluación, orden);
		this.prioridad = prioridad;
	}
	
	public Prioridad obtenerPrioridad() {
		return prioridad;
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return empresas.stream().sorted(this::compararEmpresas).collect(Collectors.toList());
	}
	
	private int compararEmpresas(Empresa empresa1, Empresa empresa2) {
		double valor1 = evaluación.evaluar(valoresAEvaluar(empresa1));
		double valor2 = evaluación.evaluar(valoresAEvaluar(empresa2));
		return orden.comparar(valor1, valor2) ? 1 : -1;
	}
	
	public String getTipo()	{
		return "Comparativa";
	}

}