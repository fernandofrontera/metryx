package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.uqbar.commons.model.annotations.Dependencies;
import org.uqbar.commons.model.annotations.Observable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Observable @Entity(name="CuentasDeEmpresas")
public class CuentaDeEmpresa extends Entidad implements Medida {
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Cuenta cuenta;
	
	@JsonProperty("company")
	protected String companyName;
	
	@JsonProperty
	protected short period;
	
	@JsonProperty
	private double value;
	
	@SuppressWarnings("unused")
	private CuentaDeEmpresa() {}
	
	@JsonCreator
	public CuentaDeEmpresa(
			@JsonProperty("name") String nombre,
			@JsonProperty("description") String descripción,
			@JsonProperty("company") String nombreDeEmpresa,
			@JsonProperty("period") short período,
			@JsonProperty("value") double valor
			) {
		this.cuenta = new Cuenta(nombre, descripción);
		this.companyName = nombreDeEmpresa;
		this.period = período;
		this.value = valor;
	}
	
	@JsonSetter("name")
	private void establecerNombre(String nombre) {
		this.cuenta.setNombre(nombre);
	}
	
	@JsonSetter("description")
	private void establecerDescripción(String descripción) {
		this.cuenta.setDescripción(descripción);
	}
	
	@JsonGetter("name")
	public String getName() {
		return cuenta.getNombre();
	}
	
	@JsonGetter("description")
	public String obtenerDescripción() {
		return cuenta.getDescripción();
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public double obtenerValor(Empresa company, short period) {
		return value;
	}
	
	public double getValue() {
		return value;
	}

	public short getPeriod() {
		return period;
	}
	
	public Cuenta obtenerCuenta() {
		return cuenta;
	}
	
	@Dependencies("value")
	public String getValueString() {
		return ""; //fixme
		//return Util.significantDigits(getValue());
	}
	
	public void actualizar(String companyName, short period, double value) {
		this.companyName = companyName;
		this.period = period;
		this.value = value;
	}

	public void establecerCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
}
