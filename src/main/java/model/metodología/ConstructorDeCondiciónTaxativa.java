package model.metodología;


public final class ConstructorDeCondiciónTaxativa extends ConstructorDeCondición<ConstructorDeCondiciónTaxativa> {
	
	public ConstructorDeCondiciónTaxativa(String nombre) {
		super(nombre);
	}

	public void establecerValorDeReferencia(Double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}
	
	public ConstructorDeCondiciónTaxativa conValorDeReferencia(Double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}
	
	@Override
	public CondiciónTaxativa construir() {
		return new CondiciónTaxativa(nombre, obtenerIndicador(), númeroDePeríodos, evaluación, orden, valorDeReferencia);
	}

	@Override
	protected ConstructorDeCondiciónTaxativa obtenerEsto() {
		return this;
	}
}