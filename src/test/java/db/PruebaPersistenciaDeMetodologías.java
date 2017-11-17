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
public class PruebaPersistenciaDeMetodologías {
	
	private RepositorioDeMetodologias repositorio = Repositorios.obtenerRepositorioDeMetodologias();
	
	private static Indicador indicador = new Indicador("INDPRUEBA", "", "7094.62 + 1");
	private static CondicionTaxativa condiciónTaxativa;
	private static CondicionComparativa condiciónComparativa;
	
	@BeforeClass
	public static void inicializar() {
		Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
		condiciónTaxativa = new ConstructorDeCondicionTaxativa("CONDTAX")
				.conIndicador(indicador.getName()).construir();
		condiciónComparativa = new ConstructorDeCondicionComparativa("CONDCOMP")
				.conIndicador(indicador.getName()).construir();
	}
	
	@Test
	public void A_probarInsertarUnaMetodología() throws Exception {
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
	public void B_probarAgregarUnaCondiciónTaxativa() throws Exception {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		ConstructorDeMetodologia constructor = new ConstructorDeMetodologia(metodologias.get(0));
		constructor.agregarCondición(condiciónTaxativa);
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
		assertEquals(condiciónTaxativa.getNombre(), condicionObtenida.getNombre());
		assertSame(indicador, condicionObtenida.obtenerIndicador());
	}
	
	@Test
	public void C_probarCambiarLaCondiciónAComparativa() throws Exception {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		ConstructorDeMetodologia constructor = new ConstructorDeMetodologia(metodologias.get(0));
		constructor.eliminarCondicion(condiciónTaxativa.getNombre());
		constructor.agregarCondición(condiciónComparativa);
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
		assertEquals(condiciónComparativa.getNombre(), condicionObtenida.getNombre());
		assertSame(indicador, condicionObtenida.obtenerIndicador());
	}
	
	@Test
	public void D_probarEliminarLaMetodología() {
		List<Metodologia> metodologias = repositorio.todos();
		assertEquals(1, metodologias.size());
		
		Metodologia metodologia = metodologias.get(0);
		repositorio.remover(metodologia);
		
		assertEquals(0, metodologias.size());
	}
	
	
}
