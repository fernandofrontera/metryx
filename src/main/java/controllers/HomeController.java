package controllers;

import java.util.HashMap;

import model.Usuario;
import model.repositorios.Repositorios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
	
	public ModelAndView show(Request request, Response response) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		Usuario usuario = Repositorios.obtenerRepositorioDeUsuarios().encontrarPorId(request.cookie("uid"));
		map.put("usuario", usuario);
		
		return new ModelAndView(map, "home.hbs");
	}
	
}
