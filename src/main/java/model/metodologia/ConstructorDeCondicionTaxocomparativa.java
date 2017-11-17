package model.metodologia;

public final class ConstructorDeCondicionTaxocomparativa extends ConstructorDeCondicion<ConstructorDeCondicionTaxocomparativa> {

	public ConstructorDeCondicionTaxocomparativa(String nombre) {
		super(nombre);
	}
	
	public void establecerValorDeReferencia(Double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}
	
	public ConstructorDeCondicionTaxocomparativa conValorDeReferencia(Double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}
	
	public void establecerPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	
	public ConstructorDeCondicionTaxocomparativa conPrioridad(Prioridad prioridad) {
		establecerPrioridad(prioridad);
		return this;
	}

	@Override
	public CondicionTaxocomparativa construir() {
		return new CondicionTaxocomparativa(nombre, obtenerIndicador(), numeroDePeriodos,
				evaluacion, orden, valorDeReferencia, prioridad);
	}

	@Override
	protected ConstructorDeCondicionTaxocomparativa obtenerEsto() {
		return this;
	}

}