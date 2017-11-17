package model.metodologia;


public final class ConstructorDeCondicionTaxativa extends ConstructorDeCondicion<ConstructorDeCondicionTaxativa> {
	
	public ConstructorDeCondicionTaxativa(String nombre) {
		super(nombre);
	}

	public void establecerValorDeReferencia(Double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}
	
	public ConstructorDeCondicionTaxativa conValorDeReferencia(Double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}
	
	@Override
	public CondicionTaxativa construir() {
		return new CondicionTaxativa(nombre, obtenerIndicador(), numeroDePeriodos, evaluacion, orden, valorDeReferencia);
	}

	@Override
	protected ConstructorDeCondicionTaxativa obtenerEsto() {
		return this;
	}
}