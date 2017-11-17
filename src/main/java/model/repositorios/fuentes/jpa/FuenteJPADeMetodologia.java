package model.repositorios.fuentes.jpa;

import java.util.List;

import javax.persistence.EntityTransaction;

import model.metodologia.Metodologia;
import model.repositorios.RepositorioDeMetodologias;
import model.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJPADeMetodologia implements FuenteDeMetodologia {

	AdministradorJPA<Metodologia> jpa = new AdministradorJPA<>(Metodologia.class);
	List<Metodologia> metodologias;
	
	@Override
	public List<Metodologia> cargar() {
		this.metodologias = jpa.obtenerTodos();
		return metodologias;
	}

	@Override
	public void guardar(RepositorioDeMetodologias repositorio, List<Metodologia> metodologias) {
		EntityTransaction transacción = jpa.iniciarTransacción();
		metodologias.forEach(metodología-> {
			if(encontrarOriginal(metodología) == null) {
				jpa.persistir(metodología);
				this.metodologias.add(metodología);
			}
		});
		transacción.commit();
		repositorio.setMetodologias(this.metodologias);
	}
	
	public void remover(Metodologia metodologia) {
		Metodologia original = encontrarOriginal(metodologia);
		metodologias.remove(original);
		if(original == null) return;
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		transacción.commit();
	}
	
	private Metodologia encontrarOriginal(Metodologia metodologia) {
		return metodologias.stream().filter(m -> m.obtenerNombre().equals(metodologia.obtenerNombre())).findFirst().orElse(null);
	}
	
	@Override
	public void actualizar(Metodologia viejo, Metodologia nuevo) {
		Metodologia original = encontrarOriginal(viejo);
		metodologias.remove(original);
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		jpa.persistir(nuevo);
		transacción.commit();
	}
}
