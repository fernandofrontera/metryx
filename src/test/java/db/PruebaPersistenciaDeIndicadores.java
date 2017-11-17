package db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.indicador.Indicador;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PruebaPersistenciaDeIndicadores {
	
	private RepositorioDeIndicadores repositorio = Repositorios.obtenerRepositorioDeIndicadores();
	
	private Indicador prueba1 = new Indicador("INDPRUEBA001", "Indicador de prueba 1", "27.5 / 3");
	private Indicador prueba2 = new Indicador("INDPRUEBA002", "Indicador de prueba 2", "23.7 * 5");
	
	@Test
	public void A_probarInsertarUnIndicador() throws Exception {
		List<Indicador> indicadores = repositorio.todos();
		assertEquals(0, indicadores.size());
		
		repositorio.agregar(prueba1);
		assertEquals(1, indicadores.size());
	}
	
	@Test
	public void B_probarObtenerElIndicador() throws Exception {
		List<Indicador> indicadores = repositorio.todos();
		assertEquals(1, indicadores.size());
		
		Indicador obtenido = indicadores.get(0);
		assertNotNull(obtenido);
		assertEquals(1l, (long) obtenido.getId());
		assertEquals(prueba1.getName(), obtenido.getName());
		assertEquals(prueba1.obtenerDescripcion(), obtenido.obtenerDescripcion());
		assertEquals(prueba1.obtenerFormula(), obtenido.obtenerFormula());
	}
	
	@Test
	public void C_probarActualizarElIndicador() throws Exception {
		List<Indicador> indicadores = repositorio.todos();
		assertEquals(1, indicadores.size());
		
		repositorio.reemplazar(indicadores.get(0), prueba2);
		assertEquals(1, indicadores.size());
		
		Indicador actualizado = indicadores.get(0);
		assertNotNull(actualizado);
		assertEquals(prueba2.getName(), actualizado.getName());
		assertEquals(prueba2.obtenerDescripcion(), actualizado.obtenerDescripcion());
		assertEquals(prueba2.obtenerFormula(), actualizado.obtenerFormula());
	}
	
	@Test
	public void D_probarEliminarElIndicador() throws Exception {
		List<Indicador> indicadores = repositorio.todos();
		assertEquals(1, indicadores.size());
		
		Indicador indicador = indicadores.get(0);
		repositorio.remover(indicador);
		
		assertEquals(0, indicadores.size());
	}
}
