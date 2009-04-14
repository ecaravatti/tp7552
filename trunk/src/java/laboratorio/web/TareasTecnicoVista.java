package laboratorio.web;

import java.util.List;

import laboratorio.vo.TareaVO;

public interface TareasTecnicoVista {
	
	public Integer getAreaId();
	
	public Integer getTecnicoId();
	
	public List<TareaVO> getTareasEnProgreso();
	
	public List<TareaVO> getTareasPendientes();
	
	public List<TareaVO> getTareasOtrasAreas();

	public Integer getTecnicoOrigenId();

	public Integer getSupervisorId();

	public Integer getTareaId();
	
}
