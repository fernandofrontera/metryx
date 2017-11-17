package model.metodología;


import model.indicador.Indicador;
import model.repositorios.Repositorios;

public abstract class ConstructorDeCondición<T extends ConstructorDeCondición<T>> {

	protected String nombre;
	protected String nombreDelIndicador;
	protected int númeroDePeríodos;
	protected Evaluación evaluación;
	protected Orden orden;
	protected Prioridad prioridad;
	protected Double valorDeReferencia;
	
	public ConstructorDeCondición(String nombre) {
		// Precondición: que el indicador exista
		// Si no se define nombre del indicador, se usa el nombre de la condición
		this.nombre = nombre;
		this.nombreDelIndicador = nombre;
		
		// Valores por defecto
		this.númeroDePeríodos = 1;
		this.evaluación = Evaluación.PROMEDIO;
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
	
	public void establecerEvaluación(Evaluación evaluación) {
		this.evaluación = evaluación;
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
	
	public T conEvaluación(Evaluación evaluación) {
		establecerEvaluación(evaluación);
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
	
	public abstract Condición construir();
	
	protected abstract T obtenerEsto();
}