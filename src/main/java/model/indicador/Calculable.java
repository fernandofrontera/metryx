package model.indicador;

import java.util.Set;

import model.Empresa;

public interface Calculable {
	double calcular(Empresa company, short period);
	Set<String> getCuentas();
}
