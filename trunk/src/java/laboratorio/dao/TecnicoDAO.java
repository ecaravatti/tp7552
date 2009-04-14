package laboratorio.dao;

import java.util.List;

import laboratorio.vo.TecnicoVO;

public interface TecnicoDAO {

	public List<TecnicoVO> obtenerTecnicosPorAreaSupervisor(Integer areaId, Integer supervisorId);
}
