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
	public void probarCondiciónTaxativaNoAplicable() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1", "E3");
		assertFalse(condicion.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaAplicable() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1");
		assertTrue(condicion.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaMenorAValorDeReferencia() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I1")
				.conValorDeReferencia(50000.0).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas = empresas("E1", "E2");
		List<Empresa> filtradas = empresas("E2");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónTaxativaTendenciaCreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I4").conNúmeroDePeríodos(5).construir();
		
		List<Empresa> empresas = empresas("E2", "E1", "E3");
		List<Empresa> filtradas = empresas("E2", "E1");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónTaxativaMedianaMayorAUnValor() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxativa("I4")
				.conNúmeroDePeríodos(5).conEvaluación(Evaluacion.MEDIANA).conValorDeReferencia(500.0).construir();
		
		List<Empresa> empresas = empresas("E3");
		List<Empresa> filtradas = empresas();
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónComparativaPromedioCreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionComparativa("I5")
				.conNúmeroDePeríodos(3).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E2", "E4");
		List<Empresa> ordenadas = empresas("E3", "E4", "E1", "E2");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónComparativaSumatoriaDecreciente() throws Exception {
		Condicion condicion = new ConstructorDeCondicionComparativa("I5")
				.conNúmeroDePeríodos(3).conEvaluación(Evaluacion.SUMATORIA).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas  = empresas("E4", "E2", "E1", "E3");
		List<Empresa> ordenadas = empresas("E2", "E1", "E4", "E3");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónTaxocomparativa() throws Exception {
		Condicion condicion = new ConstructorDeCondicionTaxocomparativa("I5")
				.conOrden(Orden.MENOR).conValorDeReferencia(5000.0).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E4", "E2");
		List<Empresa> ordenadas = empresas("E4", "E3");
		
		empresas = condicion.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
}