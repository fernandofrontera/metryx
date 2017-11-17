package model;

import javax.persistence.Entity;

@Entity(name="Usuarios")
public class Usuario extends Entidad {

	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
}
