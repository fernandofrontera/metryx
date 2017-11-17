package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.uqbar.commons.model.annotations.Observable;

import model.repositorios.Repositorios;

@Observable @Entity(name="Empresas")
public class Empresa extends Entidad {
	
	private String nombre;
	
	@OneToMany(cascade=CascadeType.ALL) @JoinColumn(name="empresa_id")
	private List<CuentaDeEmpresa> cuentas;
	
	@SuppressWarnings("unused")
	private Empresa() {}
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<>();
	}
	
	public Empresa(String nombre, List<CuentaDeEmpresa> cuentas) {
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void agregarCuenta(CuentaDeEmpresa cuenta) {
		cuentas.add(cuenta);
	}
	
	public boolean tieneCuenta(String nombre) {
		return cuentas.stream().anyMatch(x -> x.getName().equals(nombre));
	}

	public List<CuentaDeEmpresa> getCuentas() {
		return cuentas.stream().distinct().collect(Collectors.toList());
	}
	
	public List<CuentaDeEmpresa> obtenerCuentas(short periodo) {
		return cuentas.stream().filter(m -> m.getPeriod() == periodo).collect(Collectors.toList());
	}

	public CuentaDeEmpresa obtenerCuenta(String nombre, short periodo) {
		return cuentas.stream()
		            .filter(x -> x.getName().equals(nombre) && x.getPeriod() == periodo)
		            .findFirst().orElse(null);
	}
	
	public List<Short> obtenerPeriodos() {
		return cuentas.stream().map(cuenta -> cuenta.getPeriod()).distinct().sorted().collect(Collectors.toList());
	}
	
	public void actualizar(List<CuentaDeEmpresa> cuentas) {
		cuentas.forEach(cuenta -> {
			CuentaDeEmpresa original = this.cuentas.stream().filter(c -> cuenta.getName()
					.equals(c.getName()) && cuenta.getCompanyName().equals(c.getCompanyName())
							&& cuenta.getPeriod() == c.getPeriod()).findFirst().orElse(null);
			System.out.println(String.format("original%s es null", original == null ? "" : " no"));
			if(original == null) {
				Cuenta cuentaOrig = Repositorios.obtenerRepositorioDeEmpresas().obtenerCuentas().stream()
					.filter(c -> c.getNombre().equals(cuenta.obtenerCuenta().getNombre())).findFirst().orElse(null);
				if(cuentaOrig != null) {
					cuenta.establecerCuenta(cuentaOrig);
				}
				this.cuentas.add(cuenta);
			} else {
				original.actualizar(cuenta.getCompanyName(), cuenta.getPeriod(), cuenta.getValue());
			}
		});
	}
}
