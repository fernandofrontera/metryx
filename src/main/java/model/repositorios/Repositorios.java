package model.repositorios;

import model.repositorios.fuentes.jpa.FuenteJPADeEmpresa;
import model.repositorios.fuentes.jpa.FuenteJPADeIndicador;
import model.repositorios.fuentes.jpa.FuenteJPADeMetodologia;

public class Repositorios {
	
	private static RepositorioDeUsuarios repositorioDeUsuarios;
	private static RepositorioDeEmpresas repositorioDeEmpresas;
	private static RepositorioDeIndicadores repositorioDeIndicadores;
	private static RepositorioDeMetodologias repositorioDeMetodologias;
	
	public static RepositorioDeUsuarios obtenerRepositorioDeUsuarios() {
		if(repositorioDeUsuarios == null) {
			repositorioDeUsuarios = new RepositorioDeUsuarios();
		}
		return repositorioDeUsuarios;
	}
	
	public static RepositorioDeEmpresas obtenerRepositorioDeEmpresas() {
		if(repositorioDeEmpresas == null) {
			FuenteJPADeEmpresa fuenteDeEmpresa = new FuenteJPADeEmpresa();
			repositorioDeEmpresas = new RepositorioDeEmpresas(fuenteDeEmpresa);
		}
		return repositorioDeEmpresas;
	}
	
	public static void establecerRepositorioDeEmpresas(RepositorioDeEmpresas repositorio) {
		repositorioDeEmpresas = repositorio;
	}
	
	public static RepositorioDeIndicadores obtenerRepositorioDeIndicadores() {
		if(repositorioDeIndicadores == null) {
			FuenteJPADeIndicador fuenteDeIndicador = new FuenteJPADeIndicador();
			repositorioDeIndicadores = new RepositorioDeIndicadores(fuenteDeIndicador);
			repositorioDeIndicadores.crearIndicadores();
		}
		return repositorioDeIndicadores;
	}
	
	public static void establecerRepositorioDeIndicadores(RepositorioDeIndicadores repositorio) {
		repositorioDeIndicadores = repositorio;
	}
	
	public static RepositorioDeMetodologias obtenerRepositorioDeMetodologias() {
		if(repositorioDeMetodologias == null) {
			FuenteJPADeMetodologia fuenteDeMetodologia = new FuenteJPADeMetodologia();
			repositorioDeMetodologias = new RepositorioDeMetodologias(fuenteDeMetodologia);
		}
		return repositorioDeMetodologias;
	}
	
	public static void establecerRepositorioDeMetodologias(RepositorioDeMetodologias repositorio) {
		repositorioDeMetodologias = repositorio;
	}
}