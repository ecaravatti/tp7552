package clinica.web;

import java.util.List;

import clinica.vo.CoberturaVO;
import clinica.vo.PlanVO;
import clinica.vo.PrestacionVO;


public interface GestionarPlanesVista {

	public void inicializar();

	public void setPlanes(List<PlanVO> planes);
	
	public PlanVO getPlanSeleccionado();
	
	public void setCoberturas(List<CoberturaVO> coberturas);
	
	public void setComportamientos(List<String> comportamientos);
	
	public void setCondiciones(List<String> condiciones);
	
	public void setFrecuencias(List<String> frecuencias);
	
	public void setPrestaciones(List<PrestacionVO> prestaciones);
	
	public CoberturaVO getCoberturaSeleccionada();
	
	public String getCondicionSeleccionada();
	
	public Integer getCantidadCantidadIngresada();
	
	public String getCantidadFrecuenciaSeleccionada();
	
	public String getComportamientoSeleccionado();
	
	public void setCoberturaSeleccionada(CoberturaVO coberturaSeleccionada);
	
	public void setDescripcionRegla(String descripcionRegla);
	
	public List<CoberturaVO> getCoberturas();
	
	public Integer getCarenciaMesesIngresado();
	
	public List<PrestacionVO> getCombinacionPrestacionesSeleccionadas();
	
	public Integer getCombinacionCantidadIngresada();

}
