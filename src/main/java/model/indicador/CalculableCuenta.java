package model.indicador;

import java.util.HashSet;
import java.util.Set;

import model.CuentaDeEmpresa;
import model.Empresa;

public class CalculableCuenta implements Calculable {

	String nombreCuenta;
	
	public CalculableCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	
	@Override
	public double calcular(Empresa empresa, short periodo) {
		CuentaDeEmpresa cuenta;
		cuenta = empresa.obtenerCuenta(nombreCuenta, periodo);
		return cuenta.obtenerValor(empresa, periodo);
	}

	@Override
	public Set<String> getCuentas() {
		Set<String> set = new HashSet<String>();
		set.add(nombreCuenta);
		return set;
	}
}
