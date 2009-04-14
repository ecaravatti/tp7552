package laboratorio.servicio;

import general.dao.DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laboratorio.dao.AreaDAO;
import laboratorio.dominio.Area;
import laboratorio.dominio.tarea.Tarea;
import laboratorio.vo.AreaVO;
import laboratorio.vo.TareaVO;

import org.springframework.transaction.annotation.Transactional;

public class ServicioAreaImpl implements ServicioArea {

	private DAO<Area> areaDAO;
	private AreaDAO areaCustomDAO;
	
	@Transactional
	public void borrar(Integer id) {
		Area area = areaDAO.obtener(id);
		areaDAO.remover(area);
	}

	@Transactional
	public List<AreaVO> obtenerTodas() {
		List<AreaVO> areas = new ArrayList<AreaVO>();
		
		for (Area area: areaDAO.obtenerTodos()) {
			areas.add(new AreaVO(area.getId(), area.getNombre()));
		}
		return areas;
	}

	@Transactional
	public List<AreaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad) {
		List<AreaVO> areas = new ArrayList<AreaVO>();
		
		for (Area area: areaDAO.obtenerTodos(ordenarPor, orden, desde, cantidad)) {
			areas.add(new AreaVO(area.getId(), area.getNombre()));
		}
		return areas;
	}

	@Transactional
	public List<AreaVO> obtenerAreasPorSupervisor(Integer supervisorId) {
		return this.areaCustomDAO.obtenerAreasPorSupervisor(supervisorId);
	}

	@Transactional
	public List<TareaVO> obtenerTareasNoAsignadas(Integer areaId) {
		List<TareaVO> tareasNoAsignadas = new ArrayList<TareaVO>();
		Area area = areaDAO.obtener(areaId);
		for (Tarea tarea : area.getTareasNoAsignadas())
			tareasNoAsignadas.add(new TareaVO(tarea.getId(), tarea.getDescripcion(), tarea.getEstado(), tarea.getArea().getId()));
		Collections.sort(tareasNoAsignadas);
		return tareasNoAsignadas;
	}

	public void setAreaDAO(DAO<Area> areaDAO) {
		this.areaDAO = areaDAO;
	}

	public void setAreaCustomDAO(AreaDAO areaCustomDAO) {
		this.areaCustomDAO = areaCustomDAO;
	}

}
