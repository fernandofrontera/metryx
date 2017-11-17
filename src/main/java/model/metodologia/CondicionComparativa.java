package model.metodologia;

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
public final class CondicionComparativa extends Condicion {
	
	@JsonProperty
	private Prioridad prioridad;

	public CondicionComparativa() {}

	@JsonCreator
	CondicionComparativa(
			@JsonProperty("nombre") String nombre,
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("númeroDePeríodos") int númeroDePeríodos,
			@JsonProperty("evaluacion") Evaluacion evaluacion,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluacion, orden);
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
		double valor1 = evaluacion.evaluar(valoresAEvaluar(empresa1));
		double valor2 = evaluacion.evaluar(valoresAEvaluar(empresa2));
		return orden.comparar(valor1, valor2) ? 1 : -1;
	}
	
	public String getTipo()	{
		return "Comparativa";
	}

}