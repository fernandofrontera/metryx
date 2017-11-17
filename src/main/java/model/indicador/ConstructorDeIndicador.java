package model.indicador;

import model.indicador.AnalizadorSintactico.ParseFailedException;

public class ConstructorDeIndicador {
	@SuppressWarnings("serial")
	public class ExcepcionDeFormulaInvalida extends Exception {
		public ExcepcionDeFormulaInvalida(String message) {
			super(message);
		}
	}
	
	String name;
	String description;
	String formulaAsString;
	
	public void establecerNombre(String name) {
		this.name = name;
	}
	
	public void establecerDescripcion(String description) {
		this.description = description;
	}
	
	public void establecerFormula(String formula) {
		this.formulaAsString = formula;
	}


	public Indicador construir() throws ExcepcionDeFormulaInvalida {
		AnalizadorSintactico parser = new AnalizadorSintactico();
		
		try {
			parser.obtenerCalculable(formulaAsString);
		} catch (ParseFailedException e) {
			throw new ExcepcionDeFormulaInvalida(e.getMessage());
		}
		
		Indicador indicator = new Indicador(name, description, formulaAsString);
		return indicator;
	}
}
