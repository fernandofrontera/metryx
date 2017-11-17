package modelo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Empresa;
import model.metodologia.Condicion;
import model.metodologia.ConstructorDeCondicionComparativa;
import model.metodologia.ConstructorDeCondicionTaxativa;
import model.metodologia.ConstructorDeCondicionTaxocomparativa;
import model.metodologia.Evaluacion;
import model.metodologia.Orden;
import model.repositorios.RepositorioDeEmpresas;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;
import modelo.fuentes.FuenteDeEmpresaDePrueba;
import modelo.fuentes.FuenteDeIndicadorDePrueba;

import static modelo.fuentes.FuenteDeEmpresaDePrueba.*;
import static org.junit.Assert.*;

public class PruebaDeCondiciones {
	
	@Before
	public void inicializar() {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePrueba()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		FuenteDeIndicadorDePrueba.crearIndicadoresDePrueba();
	}
	
	@Test
	public void probarCondicionTaxativaNoAplicable() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1", "E3");
		assertFalse(condicion.esAplicable(empresas));
	}
	
	@Test
	public void probarCondicionTaxativaAplicable() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1");
		assertTrue(condicion.esAplicable(empresas));
	}
	
	@Test
	public void probarCondicionTaxativaMenorAValorDeReferencia() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1")
				.conValorDeReferencia(50000.0).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas = empresas("E1", "E2");
		List<Empresa> filtradas = empresas("E2");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondicionTaxativaTendenciaCreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I4").conNumeroDePeriodos(5).construir();
		
		List<Empresa> empresas = empresas("E2", "E1", "E3");
		List<Empresa> filtradas = empresas("E2", "E1");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondicionTaxativaMedianaMayorAUnValor() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I4")
				.conNumeroDePeriodos(5).conEvaluacion(Evaluacion.MEDIANA).conValorDeReferencia(500.0).construir();
		
		List<Empresa> empresas = empresas("E3");
		List<Empresa> filtradas = empresas();
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondicionComparativaPromedioCreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionComparativa("I5")
				.conNumeroDePeriodos(3).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E2", "E4");
		List<Empresa> ordenadas = empresas("E3", "E4", "E1", "E2");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondicionComparativaSumatoriaDecreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionComparativa("I5")
				.conNumeroDePeriodos(3).conEvaluacion(Evaluacion.SUMATORIA).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas  = empresas("E4", "E2", "E1", "E3");
		List<Empresa> ordenadas = empresas("E2", "E1", "E4", "E3");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondicionTaxocomparativa() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxocomparativa("I5")
				.conOrden(Orden.MENOR).conValorDeReferencia(5000.0).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E4", "E2");
		List<Empresa> ordenadas = empresas("E4", "E3");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
}