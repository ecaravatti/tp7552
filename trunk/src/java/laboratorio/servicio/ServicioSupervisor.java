package laboratorio.servicio;

import java.util.List;

import laboratorio.dominio.Supervisor;
import laboratorio.dominio.estrategias.EstrategiaAsignacionTarea;
import laboratorio.vo.SupervisorVO;
import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;


public interface ServicioSupervisor {

	public void asignarTarea(Integer supervisorId, Integer tecnicoId, Integer tareaId);
	
	public void reasignarTarea(Integer supervisorId, Integer tecnicoOrigenId, Integer tecnicoDestinoId, Integer tareaId);
	
	public void desasignarTarea(Integer supervisorId, Integer tecnicoId, Integer tareaId);
	
	public void asignarTareasAutomaticamente(Integer supervisorId, String estrategia, List<TecnicoVO> tecnicosVOs, List<TareaVO> tareasVOs);

	public EstrategiaAsignacionTarea obtenerEstrategia(String keyEstrategia);
	
	public List<String> getListadoDeEstrategias();
	
	public Supervisor getSupervisor(Integer id);
	
	public List<SupervisorVO> obtenerSupervisores();
	
}
