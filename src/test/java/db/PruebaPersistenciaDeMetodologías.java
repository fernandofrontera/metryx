package db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.indicador.Indicador;
import model.metodología.Condición;
import model.metodología.CondiciónComparativa;
import model.metodología.CondiciónTaxativa;
import model.metodología.ConstructorDeCondiciónComparativa;
import model.metodología.ConstructorDeCondiciónTaxativa;
import model.metodología.ConstructorDeMetodología;
import model.metodología.Metodología;
import model.repositorios.RepositorioDeMetodologias;
import model.repositorios.Repositorios;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PruebaPersistenciaDeMetodologías {
	
	private RepositorioDeMetodologias repositorio = Repositorios.obtenerRepositorioDeMetodologias();
	
	private static Indicador indicador = new Indicador("INDPRUEBA", "", "7094.62 + 1");
	private static CondiciónTaxativa condiciónTaxativa;
	private static CondiciónComparativa condiciónComparativa;
	
	@BeforeClass
	public static void inicializar() {
		Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
		condiciónTaxativa = new ConstructorDeCondiciónTaxativa("CONDTAX")
				.conIndicador(indicador.getName()).construir();
		condiciónComparativa = new ConstructorDeCondiciónComparativa("CONDCOMP")
				.conIndicador(indicador.getName()).construir();
	}
	
	@Test
	public void A_probarInsertarUnaMetodología() throws Exception {
		String nombre = "METPRUEBA001";
		Metodología metodología = new ConstructorDeMetodología(nombre).construir();
		
		List<Metodología> metodologías = repositorio.todos();
		assertEquals(0, metodologías.size());
		
		repositorio.agregar(metodología);
		assertEquals(1, metodologías.size());
		
		Metodología obtenida = metodologías.get(0);
		assertNotNull(obtenida);
		assertEquals(nombre, obtenida.obtenerNombre());
	}
	
	@Test
	public void B_probarAgregarUnaCondiciónTaxativa() throws Exception {
		List<Metodología> metodologías = repositorio.todos();
		assertEquals(1, metodologías.size());
		
		ConstructorDeMetodología constructor = new ConstructorDeMetodología(metodologías.get(0));
		constructor.agregarCondición(condiciónTaxativa);
		Metodología metodología = constructor.construir();
		
		repositorio.reemplazar(metodologías.get(0), metodología);
		assertEquals(1, metodologías.size());
		
		Metodología obtenida = metodologías.get(0);
		assertNotNull(obtenida);
		assertEquals(0, obtenida.getCondicionesComparativas().size());
		assertEquals(0, obtenida.getCondicionesTaxocomparativas().size());
		assertEquals(1, obtenida.getCondicionesTaxativas().size());
		
		Condición condiciónObtenida = obtenida.getCondicionesTaxativas().get(0);
		assertNotNull(condiciónObtenida);
		assertEquals(condiciónTaxativa.getNombre(), condiciónObtenida.getNombre());
		assertSame(indicador, condiciónObtenida.obtenerIndicador());
	}
	
	@Test
	public void C_probarCambiarLaCondiciónAComparativa() throws Exception {
		List<Metodología> metodologías = repositorio.todos();
		assertEquals(1, metodologías.size());
		
		ConstructorDeMetodología constructor = new ConstructorDeMetodología(metodologías.get(0));
		constructor.eliminarCondicion(condiciónTaxativa.getNombre());
		constructor.agregarCondición(condiciónComparativa);
		Metodología metodología = constructor.construir();
		
		repositorio.reemplazar(metodologías.get(0), metodología);
		assertEquals(1, metodologías.size());
		
		Metodología obtenida = metodologías.get(0);
		assertNotNull(obtenida);
		assertEquals(0, obtenida.getCondicionesTaxativas().size());
		assertEquals(0, obtenida.getCondicionesTaxocomparativas().size());
		assertEquals(1, obtenida.getCondicionesComparativas().size());
		
		Condición condiciónObtenida = obtenida.getCondicionesComparativas().get(0);
		assertNotNull(condiciónObtenida);
		assertEquals(condiciónComparativa.getNombre(), condiciónObtenida.getNombre());
		assertSame(indicador, condiciónObtenida.obtenerIndicador());
	}
	
	@Test
	public void D_probarEliminarLaMetodología() {
		List<Metodología> metodologías = repositorio.todos();
		assertEquals(1, metodologías.size());
		
		Metodología metodología = metodologías.get(0);
		repositorio.remover(metodología);
		
		assertEquals(0, metodologías.size());
	}
	
	
}
