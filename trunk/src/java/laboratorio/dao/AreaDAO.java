package laboratorio.dao;

import java.util.List;

import laboratorio.vo.AreaVO;

public interface AreaDAO {

	public List<AreaVO> obtenerAreasPorSupervisor(Integer supervisorId);
}
