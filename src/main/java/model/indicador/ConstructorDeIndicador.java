package model.indicador;

import model.indicador.AnalizadorSintactico.ParseFailedException;

public class ConstructorDeIndicador {
	@SuppressWarnings("serial")
	public class ExcepciónDeFórmulaInválida extends Exception {
		public ExcepciónDeFórmulaInválida(String message) {
			super(message);
		}
	}
	
	String name;
	String description;
	String formulaAsString;
	
	public void establecerNombre(String name) {
		this.name = name;
	}
	
	public void establecerDescripción(String description) {
		this.description = description;
	}
	
	public void establecerFórmula(String formula) {
		this.formulaAsString = formula;
	}


	public Indicador construir() throws ExcepciónDeFórmulaInválida {
		AnalizadorSintactico parser = new AnalizadorSintactico();
		
		try {
			parser.obtenerCalculable(formulaAsString);
		} catch (ParseFailedException e) {
			throw new ExcepciónDeFórmulaInválida(e.getMessage());
		}
		
		Indicador indicator = new Indicador(name, description, formulaAsString);
		return indicator;
	}
}
