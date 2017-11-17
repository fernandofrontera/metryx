package modelo.fuentes;

import java.util.ArrayList;
import java.util.List;

import model.indicador.ConstructorDeIndicador;
import model.indicador.Indicador;
import model.indicador.IndicadorCalculado;
import model.indicador.ConstructorDeIndicador.ExcepcionDeFormulaInvalida;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;
import model.repositorios.fuentes.FuenteDeIndicador;

public class FuenteDeIndicadorDePrueba implements FuenteDeIndicador {
	
	@Override
	public List<Indicador> cargar() {
		return new ArrayList<>();
	}
	
	@Override
	public List<String> obtenerNombres() {
		return new ArrayList<>();
	}

	@Override
	public void guardar(RepositorioDeIndicadores repositorio, List<Indicador> indicadores) {
		
	}
	
	public static void crearIndicador(String nombre, String formula) {
		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerFormula(formula);
		
		try {
			Indicador indicador = constructor.construir();
			Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
		} catch (ExcepcionDeFormulaInvalida e) {
			e.printStackTrace();
		}
	}

	public static void crearIndicadoresDePrueba() {
		Repositorios.obtenerRepositorioDeIndicadores().crearIndicadores();
		crearIndicador("I1", "13 * 724 + C2");
		crearIndicador("I2", "I1 - 3.14");
		crearIndicador("I3", "I1 / I2");
		crearIndicador("I4", "(235 / 3.4) * C1");
		crearIndicador("I5", "2 * C3");
	}

	@Override
	public void remover(Indicador indicador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IndicadorCalculado> cargarCalculados() {
		// TODO Auto-generated method stub
		return null;
	}
}