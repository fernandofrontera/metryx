package model.metodologia;


import model.indicador.Indicador;
import model.repositorios.Repositorios;

public abstract class ConstructorDeCondicion<T extends ConstructorDeCondicion<T>> {

	protected String nombre;
	protected String nombreDelIndicador;
	protected int numeroDePeriodos;
	protected Evaluacion evaluacion;
	protected Orden orden;
	protected Prioridad prioridad;
	protected Double valorDeReferencia;
	
	public ConstructorDeCondicion(String nombre) {
		// Precondicion: que el indicador exista
		// Si no se define nombre del indicador, se usa el nombre de la condicion
		this.nombre = nombre;
		this.nombreDelIndicador = nombre;
		
		// Valores por defecto
		this.numeroDePeriodos = 1;
		this.evaluacion = Evaluacion.PROMEDIO;
		this.orden = Orden.MAYOR;
		this.prioridad = Prioridad.MEDIA;
		//this.valorDeReferencia = Optional.empty();
	}
	
	public void establecerIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
	}
	
	public void establecerNumeroDePeriodos(int numeroDePeriodos) {
		this.numeroDePeriodos = numeroDePeriodos;
	}
	
	public void establecerEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public void establecerOrden(Orden orden) {
		this.orden = orden;
	}
	
	public T conIndicador(String nombreDelIndicador) {
		establecerIndicador(nombreDelIndicador);
		return obtenerEsto();
	}
	
	public T conNumeroDePeriodos(int numeroDePeriodos) {
		establecerNumeroDePeriodos(numeroDePeriodos);
		return obtenerEsto();
	}
	
	public T conEvaluacion(Evaluacion evaluacion) {
		establecerEvaluacion(evaluacion);
		return obtenerEsto();
	}
	
	public T conOrden(Orden orden) {
		establecerOrden(orden);
		return obtenerEsto();
	}
	
	protected Indicador obtenerIndicador() {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		if(indicador == null) {
			throw new RuntimeException(String.format("El indicador '%s' no existe", nombreDelIndicador));
		}
		return indicador;
	}
	
	public abstract Condicion construir();
	
	protected abstract T obtenerEsto();
}