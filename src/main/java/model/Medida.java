package model;

public interface Medida {
	
	public String getName();
	
	public String obtenerDescripcion();
	
	public double obtenerValor(Empresa company, short period);

}
