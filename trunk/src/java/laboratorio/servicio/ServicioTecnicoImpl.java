package laboratorio.servicio;

import general.dao.DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laboratorio.dao.TecnicoDAO;
import laboratorio.dominio.Tecnico;
import laboratorio.dominio.tarea.Tarea;
import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;

import org.springframework.transaction.annotation.Transactional;

public class ServicioTecnicoImpl implements ServicioTecnico {

	private DAO<Tecnico> tecnicoDAO;
	private TecnicoDAO tecnicoCustomDAO;
	
	@Transactional
	public List<TecnicoVO> obtenerTecnicosPorAreaSupervisor(Integer areaId, Integer supervisorId) {
		return tecnicoCustomDAO.obtenerTecnicosPorAreaSupervisor(areaId, supervisorId);
	}
	
	@Transactional
	public List<TareaVO> obtenerTareasPorTecnico(Integer tecnicoId) {
		List<TareaVO> tareas = new ArrayList<TareaVO>();
		Tecnico tecnico = tecnicoDAO.obtener(tecnicoId);
		for (Tarea t : tecnico.getTareas())
			tareas.add(new TareaVO(t.getId(), t.getDescripcion(), t.getEstado(), t.getArea().getId()));
		Collections.sort(tareas);
		return tareas;
	}
	
	public void setTecnicoDAO(DAO<Tecnico> tecnicoDAO) {
		this.tecnicoDAO = tecnicoDAO;
	}

	public void setTecnicoCustomDAO(TecnicoDAO tecnicoCustomDAO) {
		this.tecnicoCustomDAO = tecnicoCustomDAO;
	}

}
