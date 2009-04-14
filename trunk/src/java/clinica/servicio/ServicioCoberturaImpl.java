package clinica.servicio;

import org.springframework.transaction.annotation.Transactional;

import general.dao.DAO;
import general.dominio.FactoryDeEstrategias;
import clinica.dominio.Cobertura;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Condicion;
import clinica.dominio.reglas.Frecuencia;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;
import clinica.dominio.reglas.condiciones.CondicionCantidad;
import clinica.dominio.reglas.condiciones.CondicionCarencia;
import clinica.dominio.reglas.condiciones.CondicionCombinacion;
import clinica.vo.CondicionCantidadVO;
import clinica.vo.CondicionCarenciaVO;
import clinica.vo.CondicionCombinacionVO;


public class ServicioCoberturaImpl implements ServicioCobertura {

	private DAO<Cobertura> coberturaDAO;
	private DAO<Regla> reglaDAO;

	@Transactional
	public void aniadirCondicionCantidadARegla(Integer coberturaId,	CondicionCantidadVO condicionCantidadVO, String comportamientoKey) {
		CondicionCantidad condicionCantidad = new CondicionCantidad(condicionCantidadVO.getCantidadPruebas(), Frecuencia.valueOf(condicionCantidadVO.getFrecuencia()));
		aniadirCondicionARegla(coberturaId, condicionCantidad, comportamientoKey);
	}
	
	@Transactional
	public void aniadirCondicionCarenciaARegla(Integer coberturaId,	CondicionCarenciaVO condicionCarenciaVO, String comportamientoKey) {
		CondicionCarencia condicionCarencia = new CondicionCarencia(condicionCarenciaVO.getMesesCarencia());
		aniadirCondicionARegla(coberturaId, condicionCarencia, comportamientoKey);
	}
	
	@Transactional
	public void aniadirCondicionCombinacionARegla(Integer coberturaId, CondicionCombinacionVO condicionCombinacionVO, String comportamientoKey) {
		CondicionCombinacion condicionCombinacion = new CondicionCombinacion(condicionCombinacionVO.getCantidadPracticas(), condicionCombinacionVO.getLCodigoPractica());
		aniadirCondicionARegla(coberturaId, condicionCombinacion, comportamientoKey);
	}
	
	@Transactional
	public void eliminarRegla(Integer coberturaId) {
		Cobertura cobertura = coberturaDAO.obtener(coberturaId);
		Regla reglaAEliminar = cobertura.getRegla();
		if (reglaAEliminar != null) {
			cobertura.setRegla(null);
			coberturaDAO.actualizar(cobertura);
			reglaDAO.remover(reglaAEliminar);
		}
	}
	
	private void aniadirCondicionARegla(Integer coberturaId, Condicion condicion, String comportamientoKey) {
		Comportamiento comportamiento = FactoryDeEstrategias.instancia().getComportamiento(comportamientoKey);
		ReglaCompuesta reglaCompuesta = new ReglaCompuesta(comportamiento);
		Cobertura cobertura = coberturaDAO.obtener(coberturaId);
		Regla regla = cobertura.getRegla();
		if (regla != null) reglaCompuesta.agregarRegla(regla);
		reglaCompuesta.agregarRegla(condicion);
		cobertura.setRegla(reglaCompuesta);
		coberturaDAO.actualizar(cobertura);
	}

	public void setCoberturaDAO(DAO<Cobertura> coberturaDao) {
		this.coberturaDAO = coberturaDao;
	}

	public void setReglaDAO(DAO<Regla> reglaDAO) {
		this.reglaDAO = reglaDAO;
	}

}
