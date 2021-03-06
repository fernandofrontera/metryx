package controllers;

import java.util.HashMap;

import model.Usuario;
import model.repositorios.RepositorioDeUsuarios;
import model.repositorios.Repositorios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SessionController {
	
	private static final int TREINTA_DiAS = 2592000;
	
	public ModelAndView showLogin(Request request, Response response) {
		if(request.cookie("uid") != null) {
			response.redirect("/");
		}
		
		boolean invalidCredentials = false;
		if(request.cookie("invalid-credentials") != null) {
			invalidCredentials = true;
			response.removeCookie("invalid-credentials");
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("invalid-credentials", invalidCredentials);
		
		return new ModelAndView(map, "login.hbs");
	}
	
	public Void validateLogin(Request request, Response response) {
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		RepositorioDeUsuarios usuarios = Repositorios.obtenerRepositorioDeUsuarios();
		boolean credencialesValidas = usuarios.validarCredenciales(email, password);
		
		if(credencialesValidas) {
			Usuario usuario = usuarios.encontrarPorCorreoElectronico(email);
			response.cookie("uid", String.valueOf(usuario.getId()), TREINTA_DiAS);
		} else {
			response.cookie("invalid-credentials", "1");
		}
		
		response.redirect(credencialesValidas ? "/" : "/login", 301);
		return null;
	}
	
	public Void logout(Request request, Response response) {
		if(request.cookie("uid") != null) {
			response.removeCookie("uid");
		}
		response.redirect("/");
		return null;
	}
}
