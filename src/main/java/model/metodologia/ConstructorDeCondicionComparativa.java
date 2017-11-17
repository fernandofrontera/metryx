package model.metodologia;

public class ConstructorDeCondicionComparativa extends ConstructorDeCondicion<ConstructorDeCondicionComparativa> {

	public ConstructorDeCondicionComparativa(String nombre) {
		super(nombre);
	}
	
	public void establecerPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	
	public ConstructorDeCondicionComparativa conPrioridad(Prioridad prioridad) {
		establecerPrioridad(prioridad);
		return this;
	}

	@Override
	public CondicionComparativa construir() {
		return new CondicionComparativa(nombre, obtenerIndicador(), númeroDePeríodos, evaluacion, orden, prioridad);
	}

	@Override
	protected ConstructorDeCondicionComparativa obtenerEsto() {
		return this;
	}

}