package model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.uqbar.commons.model.annotations.Observable;

@Observable @Entity(name="Cuentas")
public class Cuenta extends Entidad {
	
	@Column(unique=true)
	private String nombre;
	
	private String descripcion;
	
	@SuppressWarnings("unused")
	private Cuenta() {}
	
	public Cuenta(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
