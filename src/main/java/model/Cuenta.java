package model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.uqbar.commons.model.annotations.Observable;

@Observable @Entity(name="Cuentas")
public class Cuenta extends Entidad {
	
	@Column(unique=true)
	private String nombre;
	
	private String descripción;
	
	@SuppressWarnings("unused")
	private Cuenta() {}
	
	public Cuenta(String nombre, String descripción) {
		this.nombre = nombre;
		this.descripción = descripción;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}
}
