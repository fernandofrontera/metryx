package modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Empresa;
import model.indicador.ConstructorDeIndicador;
import model.indicador.Indicador;
import model.indicador.ConstructorDeIndicador.ExcepcionDeFormulaInvalida;

public class PruebaDeIndicadores {

	@Test
	public void probarIndicadorValido() throws Exception {
		String nombre = "IV";
		String descripcion = "El numero aureo";
		String formula = "1.61803398875";

		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerDescripcion(descripcion);
		constructor.establecerFormula(formula);

		Indicador indicador = constructor.construir();

		assertEquals(nombre, indicador.getName());
		assertEquals(descripcion, indicador.obtenerDescripcion());
		assertEquals(formula, indicador.obtenerFormula());
	}
	
	@Test(expected = ExcepcionDeFormulaInvalida.class)
	public void probarIndicadorInvalido() throws Exception {
		String nombre = "II";
		String formula = "1:61803398875";

		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerFormula(formula);
		constructor.construir();
	}
	
	@Test
	public void probarObtenerElValorDeUnIndicador() throws Exception {
		String nombre = "nombre";
		String descripcion = "";
		String formula = "1+2";
		
		Empresa empresa = new Empresa("CompanyName");

		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerDescripcion(descripcion);
		constructor.establecerFormula(formula);

		Indicador indicador = constructor.construir();
		
		double resultado = indicador.obtenerValor(empresa, (short) 1929);

		assertEquals(3.0, resultado, 0.0);
	}
	
	
}