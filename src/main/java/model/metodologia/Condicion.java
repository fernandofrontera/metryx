package model.metodologia;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.uqbar.commons.model.annotations.Observable;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Empresa;
import model.Entidad;
import model.indicador.Indicador;

@Observable
@Entity(name="Condiciones")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) @DiscriminatorColumn(name="tipo")
public abstract class Condicion extends Entidad {
	
	@JsonProperty
	protected String nombre;
	
	@JsonProperty
	@ManyToOne
	protected Indicador indicador;
	
	@JsonProperty
	protected int numeroDePeriodos;
	
	@JsonProperty
	protected Evaluacion evaluacion;
	
	@JsonProperty
	protected Orden orden;
	
	protected Condicion() {}
	
	protected Condicion(String nombre, Indicador indicador, int numeroDePeriodos, Evaluacion evaluacion, Orden orden) {
		this.nombre = nombre;
		this.indicador = indicador;
		this.numeroDePeriodos = numeroDePeriodos;
		this.evaluacion = evaluacion;
		this.orden = orden;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Indicador obtenerIndicador() {
		return indicador;
	}
	
	public int obtenerNumeroDePeriodos() {
		return numeroDePeriodos;
	}
	
	public Evaluacion obtenerEvaluacion() {
		return evaluacion;
	}
	
	public Orden obtenerOrden() {
		return orden;
	}

	public abstract List<Empresa> aplicar(List<Empresa> empresas);
	
	public boolean esAplicable(List<Empresa> empresas) {
		return empresas.stream().allMatch(empresa -> periodosAEvaluar(empresa).stream()
				.allMatch(periodo -> indicador.esValidoParaContexto(empresa, periodo)));
	}
	
	protected List<Short> periodosAEvaluar(Empresa empresa) {
		List<Short> periodos = empresa.obtenerPeriodos();
		if(periodos.size() == 0) return periodos;
		short periodoFinal = periodos.get(periodos.size()-1);
		short periodoInicial = (short)(periodoFinal - numeroDePeriodos + 1);
		int indiceInicial = periodos.indexOf(periodoInicial);
		if(indiceInicial == -1) indiceInicial++;
		return periodos.subList(indiceInicial, periodos.size());
	}
	
	protected List<Double> valoresAEvaluar(Empresa empresa) {
		return periodosAEvaluar(empresa).stream().map(periodo -> indicador.obtenerValor(empresa, periodo))
				.collect(Collectors.toList());
	}
	
	public abstract String getTipo();
}