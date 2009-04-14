package clinica.presenter;


import java.util.ArrayList;
import java.util.List;

import clinica.servicio.ServicioCobertura;
import clinica.servicio.ServicioPlan;
import clinica.servicio.ServicioRegla;
import clinica.vo.CoberturaVO;
import clinica.vo.CondicionCantidadVO;
import clinica.vo.CondicionCarenciaVO;
import clinica.vo.CondicionCombinacionVO;
import clinica.vo.PrestacionVO;
import clinica.web.GestionarPlanesVista;

public class GestionarPlanesPresenterImpl implements GestionarPlanesPresenter {

	private GestionarPlanesVista vista;
	
	private ServicioPlan servicioPlan;
	private ServicioRegla servicioRegla;
	private ServicioCobertura servicioCobertura;
	
	public void inicializar() {
		vista.setPlanes(servicioPlan.obtenerTodos());
		vista.setComportamientos(servicioRegla.getListadoDeComportamientos());
		vista.setCondiciones(servicioRegla.getListadoDeCondiciones());
		vista.setFrecuencias(servicioRegla.getListadoDeFrecuencias());
		
		vista.inicializar();
	}

	public void setVista(GestionarPlanesVista vista) {
		this.vista = vista;
	}

	public void setServicioPlan(ServicioPlan servicioPlan) {
		this.servicioPlan = servicioPlan;
	}

	public void actualizarCoberturas() {
		List<CoberturaVO> coberturas;
		if (this.vista.getPlanSeleccionado() == null) {
			coberturas = new ArrayList<CoberturaVO>();
		} else {
			coberturas = this.servicioPlan.obtenerCoberturas(vista.getPlanSeleccionado().getCodigo());
		}
		this.vista.setCoberturas(coberturas);
		this.vista.setPrestaciones(obtenerPrestaciones(coberturas));
	}
	
	private List<PrestacionVO> obtenerPrestaciones(List<CoberturaVO> coberturas) {
		List<PrestacionVO> prestaciones = new ArrayList<PrestacionVO>();
		for (CoberturaVO cobertura : coberturas) {
			prestaciones.add(cobertura.getPrestacionVO());
		}
		return prestaciones;
	}

	public void setServicioRegla(ServicioRegla servicioRegla) {
		this.servicioRegla = servicioRegla;
	}
	
	public void aniadirCondicion() {
		String condicionSeleccionada = vista.getCondicionSeleccionada();
		if (isCondicionCantidad(condicionSeleccionada)) {
			CondicionCantidadVO condicionCantidad = new CondicionCantidadVO(vista.getCantidadCantidadIngresada(), vista.getCantidadFrecuenciaSeleccionada());
			servicioCobertura.aniadirCondicionCantidadARegla(vista.getCoberturaSeleccionada().getId(), condicionCantidad, vista.getComportamientoSeleccionado());
		} else if (isCondicionCarencia(condicionSeleccionada)) {
			CondicionCarenciaVO condicionCarencia = new CondicionCarenciaVO(vista.getCarenciaMesesIngresado());
			servicioCobertura.aniadirCondicionCarenciaARegla(vista.getCoberturaSeleccionada().getId(), condicionCarencia, vista.getComportamientoSeleccionado());
		} else if (isCondicionCombinacion(condicionSeleccionada)) {
			List<PrestacionVO> prestaciones = vista.getCombinacionPrestacionesSeleccionadas();
			List<Integer> codigoPrestaciones = new ArrayList<Integer>();
			for (PrestacionVO prestacion : prestaciones) {
				codigoPrestaciones.add(prestacion.getCodigo());
			}
			CondicionCombinacionVO condicionCombinacion = new CondicionCombinacionVO(codigoPrestaciones, vista.getCombinacionCantidadIngresada());
			servicioCobertura.aniadirCondicionCombinacionARegla(vista.getCoberturaSeleccionada().getId(), condicionCombinacion, vista.getComportamientoSeleccionado());
		}
		actualizarCoberturaSeleccionada();
	}
	
	public void eliminarRegla() {
		servicioCobertura.eliminarRegla(vista.getCoberturaSeleccionada().getId());
		actualizarCoberturaSeleccionada();
	}
	
	private void actualizarCoberturaSeleccionada() {
		Integer coberturaSeleccionadaId = vista.getCoberturaSeleccionada().getId();
		actualizarCoberturas();
		for (CoberturaVO coberturaVO : vista.getCoberturas()) {
			if (coberturaVO.getId().equals(coberturaSeleccionadaId)) {
				vista.setCoberturaSeleccionada(coberturaVO);
				break;
			}
		}
	}
	
	public boolean isCondicionCantidad(String condicionSeleccionada) {
		return servicioRegla.isCondicionCantidad(condicionSeleccionada);
	}

	public boolean isCondicionCombinacion(String condicionSeleccionada) {
		return servicioRegla.isCondicionCombinacion(condicionSeleccionada);
	}
	
	public boolean isCondicionCarencia(String condicionSeleccionada) {
		return servicioRegla.isCondicionCarencia(condicionSeleccionada);
	}

	public void setServicioCobertura(ServicioCobertura servicioCobertura) {
		this.servicioCobertura = servicioCobertura;
	}
	
}
