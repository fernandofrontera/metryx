package modelo;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Empresa;
import model.metodologia.CondicionComparativa;
import model.metodologia.CondicionTaxativa;
import model.metodologia.CondicionTaxocomparativa;
import model.metodologia.ConstructorDeCondicionComparativa;
import model.metodologia.ConstructorDeCondicionTaxativa;
import model.metodologia.ConstructorDeCondicionTaxocomparativa;
import model.metodologia.ConstructorDeMetodologia;
import model.metodologia.Metodologia;
import model.metodologia.Orden;
import model.metodologia.Prioridad;
import model.repositorios.RepositorioDeEmpresas;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;
import modelo.fuentes.FuenteDeEmpresaDePrueba;
import modelo.fuentes.FuenteDeIndicadorDePrueba;

import static modelo.fuentes.FuenteDeEmpresaDePrueba.*;
import static org.junit.Assert.*;

public class PruebaDeMetodologiaDeBuffet {

	private class FuenteDeEmpresaDePruebaParaLaMetodologiaDeBuffet extends FuenteDeEmpresaDePrueba {
		@Override
		protected void crearEmpresas() {
			crearEmpresa("E1", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2014, 200);
				crearCuenta("UN", empresa, 2015, 300);
				crearCuenta("UN", empresa, 2016, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2014, 1300);
				crearCuenta("PN", empresa, 2015, 1500);
				crearCuenta("PN", empresa, 2016, 1700);
				// Pasivo total
				crearCuenta("PT", empresa, 2014, 400);
				crearCuenta("PT", empresa, 2015, 500);
				crearCuenta("PT", empresa, 2016, 500);
				// Ventas
				crearCuenta("V", empresa, 2014, 900);
				crearCuenta("V", empresa, 2015, 1100);
				crearCuenta("V", empresa, 2016, 1200);
				// Longevidad
				crearCuenta("L", empresa, 2016, 15);
			});

			crearEmpresa("E2", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2015, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2015, 1300);
				// Pasivo total
				crearCuenta("PT", empresa, 2015, 700);
				// Ventas
				crearCuenta("V", empresa, 2015, 900);
				// Longevidad
				crearCuenta("L", empresa, 2015, 12);
			});

			crearEmpresa("E3", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2013, 500);
				crearCuenta("UN", empresa, 2014, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2013, 1900);
				crearCuenta("PN", empresa, 2014, 1700);
				// Pasivo total
				crearCuenta("PT", empresa, 2013, 800);
				crearCuenta("PT", empresa, 2014, 600);
				// Ventas
				crearCuenta("V", empresa, 2013, 500);
				crearCuenta("V", empresa, 2014, 300);
				// Longevidad
				crearCuenta("L", empresa, 2014, 7);
			});

			crearEmpresa("E4", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2015, 700);
				crearCuenta("UN", empresa, 2016, 200);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2015, 1500);
				crearCuenta("PN", empresa, 2016, 1100);
				// Pasivo total
				crearCuenta("PT", empresa, 2015, 1000);
				crearCuenta("PT", empresa, 2016, 1000);
				// Ventas
				crearCuenta("V", empresa, 2015, 800);
				crearCuenta("V", empresa, 2016, 900);
				// Longevidad
				crearCuenta("L", empresa, 2016, 14);
			});

			crearEmpresa("E5", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2016, 900);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2016, 1300);
				// Pasivo total
				crearCuenta("PT", empresa, 2016, 400);
				// Ventas
				crearCuenta("V", empresa, 2016, 600);
				// Longevidad
				crearCuenta("L", empresa, 2016, 11);
			});
		}
	}

	@Before
	public void inicializar() {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePruebaParaLaMetodologiaDeBuffet()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		Repositorios.obtenerRepositorioDeIndicadores().crearIndicadores();
		FuenteDeIndicadorDePrueba.crearIndicador("ROE", "UN / PN");
		FuenteDeIndicadorDePrueba.crearIndicador("NivelDeDeuda", "PT / PN");
		FuenteDeIndicadorDePrueba.crearIndicador("Margenes", "UN / V");
		FuenteDeIndicadorDePrueba.crearIndicador("Longevidad", "L");
	}

	/**
	 * Maximizar ROE: una empresa es mejor que otra si durante los últimos
	 * 10 años, su ROE fue consistentemente mejor que el de la otra.
	 */
	private CondicionComparativa crearCondicionParaMaximizarROE() {
		return new ConstructorDeCondicionComparativa("ROE").conNumeroDePeriodos(10)
				.conPrioridad(Prioridad.ALTA).construir();
	}

	/**
	 * Minimizar el nivel de deuda: una empresa es mejor que otra si su
	 * proporción de deuda es menor.
	 */
	private CondicionComparativa crearCondicionParaMinimizarElNivelDeDeuda() {
		return new ConstructorDeCondicionComparativa("NivelDeDeuda").conOrden(Orden.MENOR)
				.conPrioridad(Prioridad.MEDIA).construir();
	}

	/**
	 * Márgenes consistentemente crecientes: vale la pena invertir en una
	 * empresa en la que su margen durante los últimos 10 años fue siempre
	 * creciente.
	 */
	private CondicionTaxativa crearCondicionDeMargenesCrecientes() {
		return new ConstructorDeCondicionTaxativa("Margenes").conNumeroDePeriodos(10).construir();
	}

	/**
	 * Longevidad: sólo vale la pena invertir en empresas con más de 10 años.
	 * Además, una empresa es mejor que otra si es más antigua.
	 */
	private CondicionTaxocomparativa crearCondicionDeLongevidad() {
		return new ConstructorDeCondicionTaxocomparativa("Longevidad").conValorDeReferencia(10.0)
				.conPrioridad(Prioridad.BAJA).construir();
	}

	@Test
	public void probarMetodologia() throws Exception {
		ConstructorDeMetodologia constructor = new ConstructorDeMetodologia("Buffet");
		constructor.agregarCondicion(crearCondicionParaMaximizarROE());
		constructor.agregarCondicion(crearCondicionParaMinimizarElNivelDeDeuda());
		constructor.agregarCondicion(crearCondicionDeMargenesCrecientes());
		constructor.agregarCondicion(crearCondicionDeLongevidad());
		Metodologia metodologia = constructor.construir();

		List<Empresa> empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();
		List<Empresa> resultadoEsperado = empresas("E5", "E1", "E2");

		empresas = metodologia.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, resultadoEsperado));
	}
}