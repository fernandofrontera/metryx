package model.indicador;

import java.util.Set;

import model.Empresa;

import java.util.HashSet;

public class CalculableOperador implements Calculable {

	public CalculableOperador(Calculable vIzquierda, Calculable vDerecha, char operador) {
		this.vIzquierda = vIzquierda;
		this.vDerecha = vDerecha;
		this.operador = operador;
	}

	Calculable vIzquierda;
	Calculable vDerecha;
	char operador;
	
	
	@Override
	public double calcular(Empresa Empresa, short periodo) {	
		switch (operador){
		case '+': return (vIzquierda.calcular(Empresa, periodo) + vDerecha.calcular(Empresa, periodo));
		case '-': return (vIzquierda.calcular(Empresa, periodo) - vDerecha.calcular(Empresa, periodo));
		case '*': return (vIzquierda.calcular(Empresa, periodo) * vDerecha.calcular(Empresa, periodo));
		case '/': return (vIzquierda.calcular(Empresa, periodo) / vDerecha.calcular(Empresa, periodo));
		}
		return 0;
	}


	@Override
	public Set<String> getCuentas() {
		
		Set<String> set = new HashSet<String>();
		set.addAll(vIzquierda.getCuentas());
		set.addAll(vDerecha.getCuentas());
		return set;
				
	}
}
