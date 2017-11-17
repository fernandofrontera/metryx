package model.metodologia;


import model.indicador.Indicador;
import model.repositorios.Repositorios;

public abstract class ConstructorDeCondicion<T extends ConstructorDeCondicion<T>> {

	protected String nombre;
	protected String nombreDelIndicador;
	protected int númeroDePeríodos;
	protected Evaluacion evaluacion;
	protected Orden orden;
	protected Prioridad prioridad;
	protected Double valorDeReferencia;
	
	public ConstructorDeCondicion(String nombre) {
		// Precondición: que el indicador exista
		// Si no se define nombre del indicador, se usa el nombre de la condición
		this.nombre = nombre;
		this.nombreDelIndicador = nombre;
		
		// Valores por defecto
		this.númeroDePeríodos = 1;
		this.evaluacion = Evaluacion.PROMEDIO;
		this.orden = Orden.MAYOR;
		this.prioridad = Prioridad.MEDIA;
		//this.valorDeReferencia = Optional.empty();
	}
	
	public void establecerIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
	}
	
	public void establecerNúmeroDePeríodos(int númeroDePeríodos) {
		this.númeroDePeríodos = númeroDePeríodos;
	}
	
	public void establecerEvaluación(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public void establecerOrden(Orden orden) {
		this.orden = orden;
	}
	
	public T conIndicador(String nombreDelIndicador) {
		establecerIndicador(nombreDelIndicador);
		return obtenerEsto();
	}
	
	public T conNúmeroDePeríodos(int númeroDePeríodos) {
		establecerNúmeroDePeríodos(númeroDePeríodos);
		return obtenerEsto();
	}
	
	public T conEvaluación(Evaluacion evaluacion) {
		establecerEvaluación(evaluacion);
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