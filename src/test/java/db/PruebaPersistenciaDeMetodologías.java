package db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.indicador.Indicador;
import model.metodologia.Condicion;
import model.metodologia.CondicionComparativa;
import model.metodologia.CondicionTaxativa;
import model.metodologia.ConstructorDeCondicionComparativa;
import model.metodologia.ConstructorDeCondicionTaxativa;
import model.metodologia.ConstructorDeMetodologia;
import model.metodologia.Metodologia;
import model.repositorios.RepositorioDeMetodologias;
import model.repositorios.Repositorios;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PruebaPersistenciaDeMetodologias {
	
	private RepositorioDeMetodologias repositorio = Repositorios.obtenerRepositorioDeMetodologias();
	
	private static Indicador indicador = new Indicador("INDPRUEBA", "", "7094.62 + 1");
	private static CondicionTaxativa condicionTaxativa;
	private static CondicionComparativa condicionComparativa;
	
	@BeforeClass
	public static void inicializar() {
		Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
		condicionTaxativa = new ConstructorDeCondicionTaxativa("CONDTAX")
				.conIndicador(indicador.getName()).construir();
		condicionComparativa = new ConstructorDeCondicionComparativa("CONDCOMP")
				.conIndicador(indicador.getName()).construir();
	}
	
	@Test
	public void A_probarInsertarUnaMetodologia() throws Exception {
		String nombre = "METPRUEBA001";
		Metodologia metodologia = new ConstructorDeMetodologia(nombre).construir();
		
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(0, metodologias.size());
		
		repositorio.agregar(metodologia);
		assertEquals(1, metodologias.size());
		
		Metodologia obtenida = metodologias.get(0);
		assertNotNull(obtenida);
		assertEquals(nombre, obtenida.obtenerNombre());
	}
	
	@Test
	public void B_probarAgregarUnaCondicionTaxativa() throws Exception {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		ConstructorDeMetodologia constructor = new ConstructorDeMetodologia(metodologias.get(0));
		constructor.agregarCondicion(condicionTaxativa);
		Metodologia metodologia = constructor.construir();
		
		repositorio.reemplazar(metodologias.get(0), metodologia);
		assertEquals(1, metodologias.size());
		
		Metodologia obtenida = metodologias.get(0);
		assertNotNull(obtenida);
		assertEquals(0, obtenida.getCondicionesComparativas().size());
		assertEquals(0, obtenida.getCondicionesTaxocomparativas().size());
		assertEquals(1, obtenida.getCondicionesTaxativas().size());
		
		Condicion condicionObtenida = obtenida.getCondicionesTaxativas().get(0);
		assertNotNull(condicionObtenida);
		assertEquals(condicionTaxativa.getNombre(), condicionObtenida.getNombre());
		assertSame(indicador, condicionObtenida.obtenerIndicador());
	}
	
	@Test
	public void C_probarCambiarLaCondicionAComparativa() throws Exception {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		ConstructorDeMetodologia constructor = new ConstructorDeMetodologia(metodologias.get(0));
		constructor.eliminarCondicion(condicionTaxativa.getNombre());
		constructor.agregarCondicion(condicionComparativa);
		Metodologia metodologia = constructor.construir();
		
		repositorio.reemplazar(metodologias.get(0), metodologia);
		assertEquals(1, metodologias.size());
		
		Metodologia obtenida = metodologias.get(0);
		assertNotNull(obtenida);
		assertEquals(0, obtenida.getCondicionesTaxativas().size());
		assertEquals(0, obtenida.getCondicionesTaxocomparativas().size());
		assertEquals(1, obtenida.getCondicionesComparativas().size());
		
		Condicion condicionObtenida = obtenida.getCondicionesComparativas().get(0);
		assertNotNull(condicionObtenida);
		assertEquals(condicionComparativa.getNombre(), condicionObtenida.getNombre());
		assertSame(indicador, condicionObtenida.obtenerIndicador());
	}
	
	@Test
	public void D_probarEliminarLaMetodologia() {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		Metodologia metodologia = metodologias.get(0);
		repositorio.remover(metodologia);
		
		assertEquals(0, metodologias.size());
	}
	
	
}
