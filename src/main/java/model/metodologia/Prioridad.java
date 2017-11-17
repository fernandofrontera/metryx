package model.metodologia;

public enum Prioridad {
	MaXIMA(5), ALTA(4), MEDIA(3), BAJA(2), MiNIMA(1);
	
	private int valor;
	
	private Prioridad(int valor) {
		this.valor = valor;
	}
	public int obtenerValor() {
		return valor;
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
