package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entidad {

	@Id @GeneratedValue
	protected Long id;
	
	public Long getId() {
		return id;
	}
}
