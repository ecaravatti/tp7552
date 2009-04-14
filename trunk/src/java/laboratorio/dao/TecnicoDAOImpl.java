package laboratorio.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laboratorio.vo.TecnicoVO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TecnicoDAOImpl extends SqlMapClientDaoSupport implements TecnicoDAO {

	@SuppressWarnings("unchecked")
	public List<TecnicoVO> obtenerTecnicosPorAreaSupervisor(Integer areaId, Integer supervisorId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("areaId", areaId);
		map.put("supervisorId", supervisorId);
		return (List<TecnicoVO>)getSqlMapClientTemplate().queryForList("tecnicosPorAreaSupervisor", map);
	}

}
