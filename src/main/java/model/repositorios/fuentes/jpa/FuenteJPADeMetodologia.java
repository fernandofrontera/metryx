package model.repositorios.fuentes.jpa;

import java.util.List;

import javax.persistence.EntityTransaction;

import model.metodología.Metodología;
import model.repositorios.RepositorioDeMetodologias;
import model.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJPADeMetodologia implements FuenteDeMetodologia {

	AdministradorJPA<Metodología> jpa = new AdministradorJPA<>(Metodología.class);
	List<Metodología> metodologias;
	
	@Override
	public List<Metodología> cargar() {
		this.metodologias = jpa.obtenerTodos();
		return metodologias;
	}

	@Override
	public void guardar(RepositorioDeMetodologias repositorio, List<Metodología> metodologías) {
		EntityTransaction transacción = jpa.iniciarTransacción();
		metodologías.forEach(metodología-> {
			if(encontrarOriginal(metodología) == null) {
				jpa.persistir(metodología);
				metodologias.add(metodología);
			}
		});
		transacción.commit();
		repositorio.setMetodologias(this.metodologias);
	}
	
	public void remover(Metodología metodologia) {
		Metodología original = encontrarOriginal(metodologia);
		metodologias.remove(original);
		if(original == null) return;
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		transacción.commit();
	}
	
	private Metodología encontrarOriginal(Metodología metodologia) {
		return metodologias.stream().filter(m -> m.obtenerNombre().equals(metodologia.obtenerNombre())).findFirst().orElse(null);
	}
	
	@Override
	public void actualizar(Metodología viejo, Metodología nuevo) {
		Metodología original = encontrarOriginal(viejo);
		metodologias.remove(original);
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		jpa.persistir(nuevo);
		transacción.commit();
	}
}
