package model.repositorios.fuentes.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;

import model.Empresa;
import model.indicador.Indicador;
import model.indicador.IndicadorCalculado;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;
import model.repositorios.fuentes.FuenteDeIndicador;

public class FuenteJPADeIndicador implements FuenteDeIndicador {
	
	AdministradorJPA<Indicador> jpa = new AdministradorJPA<>(Indicador.class);
	AdministradorJPA<IndicadorCalculado> jpaCalculados = new AdministradorJPA<>(IndicadorCalculado.class);
	
	List<Indicador> indicadores;
	List<IndicadorCalculado> indicadoresCalculados;

	@Override
	public List<Indicador> cargar() {
		this.indicadores = jpa.obtenerTodos();
		this.indicadores.forEach(indicador -> {
			indicador.actualizar(indicador.getName(), indicador.obtenerDescripcion(), indicador.obtenerFormula());
		});
		
		return indicadores;
	}
	
	@Override
	public List<IndicadorCalculado> cargarCalculados() {

		this.indicadoresCalculados = jpaCalculados.obtenerTodos();
		this.indicadoresCalculados.forEach(i -> {
			i.actualizar(i.getIdIndicador(), i.getPeriodo(), i.getValor(), i.getEmpresa());
		});
		
		return this.indicadoresCalculados;
	}

	@Override
	public List<String> obtenerNombres() {
		return jpa.obtenerTodos().stream().map(i -> i.getName()).collect(Collectors.toList());
	}

	@Override
	public void guardar(RepositorioDeIndicadores repositorio,List<Indicador> indicators) {
		List<Indicador> nuevos = new ArrayList<>();
		
		EntityTransaction transaccion = jpa.iniciarTransaccion();
		indicators.forEach(indicador -> {
			if(encontrarOriginal(indicador) == null) {
				jpa.persistir(indicador);
				this.indicadores.add(indicador);
				nuevos.add(indicador);
			}
		});
		transaccion.commit();
		
		
		nuevos.forEach(i-> guardarCalculados(i));
		
		repositorio.setIndicators(this.indicadores);
		repositorio.setIndicadoresCalculados(this.indicadoresCalculados);
	}
	
	private void guardarCalculados(Indicador indicador) {
		
        List<Empresa> empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();
        List<Short> periodos = Repositorios.obtenerRepositorioDeEmpresas().getAllPeriodos();
        List<IndicadorCalculado> nuevos = new ArrayList<>();
        
        empresas.forEach(empresa -> {
        	periodos.forEach(periodo -> {
        		if(indicador.esValidoParaContexto(empresa, periodo)) {
        			nuevos.add(new IndicadorCalculado(indicador, empresa, periodo));
        		}
        	});
        });
        
		EntityTransaction transaccion = jpaCalculados.iniciarTransaccion();
		nuevos.forEach(i -> jpaCalculados.persistir(i));
		transaccion.commit();
		
		this.indicadoresCalculados.addAll(nuevos);
        
	}
	
	@Override
	public void remover(Indicador indicador) {
		Indicador original = encontrarOriginal(indicador);
		if(original == null) return;
		this.indicadores.remove(original);
		EntityTransaction transaccion = jpa.iniciarTransaccion();
		jpa.merge(original);
		jpa.remover(original);
		transaccion.commit();
		
		removerCalculados(indicador);
	}
	
	@Override
	public void removerCalculados(Indicador indicador) {
		List<IndicadorCalculado> calculados = this.indicadoresCalculados.stream().filter(i -> i.getIdIndicador() == indicador.getId()).collect(Collectors.toList());
		calculados.forEach(x -> this.indicadoresCalculados.remove(x));
		EntityTransaction transaccion = jpaCalculados.iniciarTransaccion();
		calculados.forEach(x -> {
			jpaCalculados.merge(x);
			jpaCalculados.remover(x);
		});
		transaccion.commit();
	}
	
	private Indicador encontrarOriginal(Indicador indicador) {
		return indicadores.stream().filter(i -> i.getName().equals(indicador.getName())).findFirst().orElse(null);
	}
	

	@Override
	public void actualizar(Indicador viejo, Indicador nuevo) {
		Indicador original = encontrarOriginal(viejo);
		removerCalculados(viejo);
		EntityTransaction transaccion = jpa.iniciarTransaccion();
		original.actualizar(nuevo.getName(), nuevo.obtenerDescripcion(), nuevo.obtenerFormula());
		jpa.merge(original);
		transaccion.commit();
		
		guardarCalculados(original); // ya que pudo haber cambiado la formula
	}
	
	@Override
	public double getValorIndicador(Indicador indicador, String empresa, Short periodo) {
		
		Indicador original = encontrarOriginal(indicador);
		
		IndicadorCalculado calculado = this.indicadoresCalculados.stream().
				filter(x -> x.getIdIndicador() == original.getId()).
				filter(x -> x.getEmpresa().equals(empresa)).
				filter(x->  x.getPeriodo().equals(periodo)).findFirst().orElse(null);
		
		if(calculado != null) {
			System.out.println(calculado.getValor());
			return calculado.getValor();
		}
		return 0;
	}

	
}
