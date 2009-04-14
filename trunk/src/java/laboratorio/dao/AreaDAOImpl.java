package laboratorio.dao;

import java.util.List;

import laboratorio.vo.AreaVO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AreaDAOImpl extends SqlMapClientDaoSupport implements AreaDAO {

	@SuppressWarnings("unchecked")
	public List<AreaVO> obtenerAreasPorSupervisor(Integer supervisorId) {
		return (List<AreaVO>)getSqlMapClientTemplate().queryForList("areasPorSupervisor",supervisorId);
	}

}
