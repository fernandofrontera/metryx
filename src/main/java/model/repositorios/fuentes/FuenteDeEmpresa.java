package model.repositorios.fuentes;

import java.util.List;

import model.Empresa;
import model.repositorios.RepositorioDeEmpresas;

public interface FuenteDeEmpresa {

	public List<Empresa> cargar();
	
	public void guardar(RepositorioDeEmpresas repositorio, List<Empresa> empresas);
}