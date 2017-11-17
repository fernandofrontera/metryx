package model.repositorios.fuentes.jpa;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityTransaction;

import model.Empresa;
import model.repositorios.RepositorioDeEmpresas;
import model.repositorios.fuentes.FuenteDeEmpresa;

public class FuenteJPADeEmpresa implements FuenteDeEmpresa {

	AdministradorJPA<Empresa> jpa = new AdministradorJPA<>(Empresa.class);
	List<Empresa> empresas;
	
	@Override
	public List<Empresa> cargar() {
		empresas = jpa.obtenerTodos();
		return empresas;
	}
	
	@Override
	public void guardar(RepositorioDeEmpresas repositorio, List<Empresa> empresas) {
		EntityTransaction transaccion = jpa.iniciarTransaccion();
		Iterator<Empresa> iter = empresas.iterator();
		while(iter.hasNext()) {
			Empresa empresa = iter.next();
			Empresa original = encontrarOriginal(empresa);
			if(original == null) {
				jpa.persistir(empresa);
				empresas.add(empresa);
			} else {
				original.actualizar(empresa.getCuentas());
			}
		}
		transaccion.commit();
	}
	
	private Empresa encontrarOriginal(Empresa empresa) {
		return empresas.stream().filter(e -> e.getNombre().equals(empresa.getNombre())).findFirst().orElse(null);
	}
	
}
