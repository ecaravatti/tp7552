package laboratorio.servicio;

import java.util.List;

import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;

public interface ServicioTecnico {

	public List<TecnicoVO> obtenerTecnicosPorAreaSupervisor(Integer areaId, Integer supervisorId);
	
	public List<TareaVO> obtenerTareasPorTecnico(Integer tecnicoId);
	
}
