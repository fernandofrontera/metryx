package model.indicador;

import javax.persistence.Entity;

import model.Empresa;
import model.Entidad;

@Entity(name="IndicadoresCalculados")
public class IndicadorCalculado extends Entidad {

	private Long idIndicador;
	
	private String empresa;
	
	private Short periodo;

	private double valor;
	
	@SuppressWarnings("unused")
	private IndicadorCalculado() {}

	public IndicadorCalculado(Indicador indicador, Empresa empresa, Short periodo) {
		
		this.setIdIndicador(indicador.getId());
		this.setEmpresa(empresa.getNombre());
		this.setPeriodo(periodo);
		this.valor = indicador.calcular(empresa, periodo);
	}
	

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Short getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Short periodo) {
		this.periodo = periodo;
	}


	public Long getIdIndicador() {
		return idIndicador;
	}


	public void setIdIndicador(Long idIndicador) {
		this.idIndicador = idIndicador;
	}
	
	public void actualizar(Long idIndicador, Short periodo, double valor, String empresa) {
		
		this.idIndicador = idIndicador;
		this.empresa = empresa;
		this.periodo = periodo;
		this.valor = valor;
	}
	
}
