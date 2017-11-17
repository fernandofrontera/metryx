package model.metodología;

public enum Orden {
	
	MENOR, MAYOR;
	
	public boolean comparar(double valor1, double valor2) {
		return this == MAYOR ? valor1 >= valor2 : valor1 <= valor2;
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}