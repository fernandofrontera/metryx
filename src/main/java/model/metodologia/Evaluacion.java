package model.metodologia;

import java.util.Collections;
import java.util.List;

public enum Evaluacion {
	
	PROMEDIO, MEDIANA, SUMATORIA;
	
	public double evaluar(List<Double> lista) {
		switch(this) {
		case PROMEDIO:
			return lista.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		case MEDIANA:
			return mediana(lista);
		case SUMATORIA:
			return lista.stream().mapToDouble(Double::doubleValue).sum();
		}
		return 0;
	}
	
	private double mediana(List<Double> lista) {
		// Para encontrar la mediana es necesario que la lista esté ordenada
		Collections.sort(lista);
		
		int tamaño = lista.size();
		int medio = tamaño / 2;
		if(tamaño % 2 == 1) {
			// Número impar de elementos; la mediana es el que está en el medio
			return lista.get(medio);
		}
		// Número par de elementos; la mediana es la media de los dos del medio
		return (lista.get(medio-1) + lista.get(medio)) / 2.0;
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}