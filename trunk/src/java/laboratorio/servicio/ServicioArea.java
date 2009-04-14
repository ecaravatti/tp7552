package laboratorio.servicio;

import java.util.List;

import laboratorio.vo.AreaVO;
import laboratorio.vo.TareaVO;



public interface ServicioArea {

	public void borrar(Integer id);

	public List<AreaVO> obtenerTodas();
	
	public List<AreaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad);
	
	public List<AreaVO> obtenerAreasPorSupervisor(Integer supervisorId);
	
	public List<TareaVO> obtenerTareasNoAsignadas(Integer areaId);
}
