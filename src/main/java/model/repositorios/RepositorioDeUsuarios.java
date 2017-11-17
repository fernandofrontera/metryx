package model.repositorios;

import java.util.List;

import main.PasswordManager;
import model.Usuario;
import model.repositorios.fuentes.jpa.AdministradorJPA;

public class RepositorioDeUsuarios {
	
	List<Usuario> usuarios;
	
	public RepositorioDeUsuarios() {
		AdministradorJPA<Usuario> jpa = new AdministradorJPA<>(Usuario.class);
		usuarios = jpa.obtenerTodos();
	}
	
	public List<Usuario> todos() {
		return usuarios;
	}
	
	public Usuario encontrarPorId(String uid) {
		long id;
		try {
			id = Long.parseLong(uid);
		} catch(NumberFormatException e) {
			return null;
		}
		return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
	}
	
	public Usuario encontrarPorCorreoElectronico(String email) {
		return usuarios.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
	}
	
	public boolean validarCredenciales(String email, String password) {
		Usuario usuario = encontrarPorCorreoElectronico(email);
		if(usuario == null) return false;
		return PasswordManager.validate(password, usuario.getPassword());
	}
	
}
