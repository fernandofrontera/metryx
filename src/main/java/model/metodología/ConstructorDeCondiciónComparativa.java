package model.metodología;

public class ConstructorDeCondiciónComparativa extends ConstructorDeCondición<ConstructorDeCondiciónComparativa> {

	public ConstructorDeCondiciónComparativa(String nombre) {
		super(nombre);
	}
	
	public void establecerPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	
	public ConstructorDeCondiciónComparativa conPrioridad(Prioridad prioridad) {
		establecerPrioridad(prioridad);
		return this;
	}

	@Override
	public CondiciónComparativa construir() {
		return new CondiciónComparativa(nombre, obtenerIndicador(), númeroDePeríodos, evaluación, orden, prioridad);
	}

	@Override
	protected ConstructorDeCondiciónComparativa obtenerEsto() {
		return this;
	}

}