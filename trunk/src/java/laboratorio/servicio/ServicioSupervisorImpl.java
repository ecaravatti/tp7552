package laboratorio.servicio;

import general.dao.DAO;
import general.dominio.FactoryDeEstrategias;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import laboratorio.dominio.Supervisor;
import laboratorio.dominio.Tecnico;
import laboratorio.dominio.estrategias.EstrategiaAsignacionTarea;
import laboratorio.dominio.tarea.Tarea;
import laboratorio.vo.SupervisorVO;
import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;

public class ServicioSupervisorImpl implements ServicioSupervisor {
	
	private DAO<Supervisor> supervisorDAO;
	private DAO<Tecnico> tecnicoDAO;
	private DAO<Tarea> tareaDAO;

	@Transactional
	public void asignarTarea(Integer supervisorId, Integer tecnicoId, Integer tareaId) {
		
		Supervisor supervisor = supervisorDAO.obtener(supervisorId);
		Tecnico tecnico = tecnicoDAO.obtener(tecnicoId);
		Tarea tarea = tareaDAO.obtener(tareaId);
		
		supervisor.asignarTarea(tecnico, tarea);
	}

	@Transactional
	public void desasignarTarea(Integer supervisorId, Integer tecnicoId, Integer tareaId) {
		
		Supervisor supervisor = supervisorDAO.obtener(supervisorId);
		Tecnico tecnico = tecnicoDAO.obtener(tecnicoId);
		Tarea tarea = tareaDAO.obtener(tareaId);
		
		supervisor.desasignarTarea(tecnico, tarea);
	}

	@Transactional
	public void reasignarTarea(Integer supervisorId, Integer tecnicoOrigenId, Integer tecnicoDestinoId, Integer tareaId) {
		
		Supervisor supervisor = supervisorDAO.obtener(supervisorId);
		Tecnico tecnicoOrigen = tecnicoDAO.obtener(tecnicoOrigenId);
		Tecnico tecnicoDestino = tecnicoDAO.obtener(tecnicoDestinoId);
		Tarea tarea = tareaDAO.obtener(tareaId);
		
		supervisor.reasignarTarea(tecnicoOrigen, tecnicoDestino, tarea);
	}
	
	@Transactional
	public void asignarTareasAutomaticamente(Integer supervisorId, String estrategia, List<TecnicoVO> tecnicosVOs, List<TareaVO> tareasVOs) {
		
		List<Tecnico> tecnicos = new ArrayList<Tecnico>();
		List<Tarea> tareas = new ArrayList<Tarea>();
		
		for (TecnicoVO tecnicoVO : tecnicosVOs)
			tecnicos.add(tecnicoDAO.obtener(tecnicoVO.getId()));
		
		for (TareaVO tareaVO : tareasVOs)
			tareas.add(tareaDAO.obtener(tareaVO.getId()));
		
		EstrategiaAsignacionTarea estrategiaAsignacion = FactoryDeEstrategias.instancia().getEstrategia(estrategia);
		Supervisor supervisor = supervisorDAO.obtener(supervisorId);
		supervisor.setEstrategiaDeAsignacion(estrategiaAsignacion);
		supervisor.asignarTareas(tecnicos, tareas);
	}
	
	public EstrategiaAsignacionTarea obtenerEstrategia(String keyEstrategia) {
		return FactoryDeEstrategias.instancia().getEstrategia(keyEstrategia);
	}
	
	public List<String> getListadoDeEstrategias(){
		return FactoryDeEstrategias.getListadoEstrategias();
	}
	
	@Transactional
	public Supervisor getSupervisor(Integer id) {
		return supervisorDAO.obtener(id);
	}

	@Transactional
	public List<Supervisor> getListadoSupervisores() {
		return supervisorDAO.obtenerTodos();
	}
	
	@Transactional
	public List<SupervisorVO> obtenerSupervisores() {
		List<SupervisorVO> supervisores = new ArrayList<SupervisorVO>();
		
		for (Supervisor supervisor: supervisorDAO.obtenerTodos()) {
			supervisores.add(new SupervisorVO(supervisor.getId(), supervisor.getNombre()));
		}
		
		return supervisores;
	}

	public void setSupervisorDAO(DAO<Supervisor> supervisorDAO) {
		this.supervisorDAO = supervisorDAO;
	}
	
	public void setTecnicoDAO(DAO<Tecnico> tecnicoDAO) {
		this.tecnicoDAO = tecnicoDAO;
	}
	
	public void setTareaDAO(DAO<Tarea> tareaDAO) {
		this.tareaDAO = tareaDAO;
	}

}
