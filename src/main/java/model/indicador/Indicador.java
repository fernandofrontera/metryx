package model.indicador;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Empresa;
import model.Entidad;
import model.Medida;
import model.indicador.AnalizadorSintactico.ParseFailedException;

@Entity(name="Indicadores")
public class Indicador extends Entidad implements Medida,Calculable {
	
//	private Long idUsuario; TODO cero que falta esto de la entrega anterior
	
	@JsonProperty
	@Column(unique=true)
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private String formula;
	
	@Transient
	private Calculable calculable;

	@SuppressWarnings("serial")
	public class InvalidFormulaException extends Exception {
		private String cause;

		public InvalidFormulaException(String cause) {
			this.cause = cause;
		}

		@Override
		public String getMessage() { 
			return String.format("Fórmula inválida: %s.", cause);
		}
	}
	@SuppressWarnings("serial")
	public class InvalidForContextException extends Exception {}
	
	@SuppressWarnings("unused")
	private Indicador() {}
	
	@JsonCreator
	public Indicador(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		try {
			this.calculable = new AnalizadorSintactico().obtenerCalculable(formula);
		} catch (ParseFailedException e) {}
	}

	public String getFormula()	{	return formula;	}

	public String getDescription()	{	return description;	}

	public String obtenerFórmula() {
		return formula;
	}

	public String getName() {
		return name;		
	}

	public String obtenerDescripción() {
		return description;
	}

	public double obtenerValor(Empresa company, short period) {
		return this.calcular(company, period);
	}
	
	public void actualizar(String nombre, String descripción, String fórmula) {
		this.name = nombre;
		this.description = descripción;
		this.formula = fórmula;
		try {
			this.calculable = new AnalizadorSintactico().obtenerCalculable(formula);
		} catch (ParseFailedException e) {}		
	}

	@Override
	public double calcular(Empresa company, short period) {
		return calculable.calcular(company, period); //fixme reemplazar alguno de los 2 metodos :s
	}

	@JsonIgnore
	@Override
	public Set<String> getCuentas() {
		return calculable.getCuentas(); //fixme: quedarse con uno
	}
	
	public boolean esVálidoParaContexto(Empresa company, short period) { 
		return company.obtenerCuentas(period).stream()
				.map(m -> m.getName()).collect(Collectors.toSet())
				.containsAll(calculable.getCuentas());
	}
}
