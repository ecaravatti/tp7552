package clinica.servicio;

import general.dao.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import clinica.dominio.Prestacion;
import clinica.vo.PrestacionVO;

public class ServicioPrestacionImpl implements ServicioPrestacion {

	private DAO<Prestacion> prestacionDAO;

	@Transactional
	public List<PrestacionVO> obtenerTodas() {
		
		List<PrestacionVO> prestaciones = new ArrayList<PrestacionVO>();
		
		for (Prestacion prestacion : prestacionDAO.obtenerTodos()) {
			prestaciones.add(new PrestacionVO(prestacion.getCodigo(), prestacion.getDescripcion()));
		}
		return prestaciones;
	}
	
	public void setPrestacionDAO(DAO<Prestacion> prestacionDAO) {
		this.prestacionDAO = prestacionDAO;
	}
}
