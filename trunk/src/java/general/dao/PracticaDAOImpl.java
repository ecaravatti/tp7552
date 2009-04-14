package general.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import clinica.vo.PracticaVO;

public class PracticaDAOImpl extends SqlMapClientDaoSupport implements PracticaDAO {

	@SuppressWarnings("unchecked")
	public List<PracticaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad) {
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ordenarPor", ordenarPor);
		parametros.put("orden", orden);
		parametros.put("desde", desde);
		parametros.put("cantidad", cantidad);
		
		return this.getSqlMapClientTemplate().queryForList("listaPracticas", parametros);
	}
	
	public Integer obtenerCantidad() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("cantidadPracticas");
	}

	public Double obtenerCostoOrdenMedica(Integer numeroOrdenMedica) {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("orden_medica_id", numeroOrdenMedica);		
		return (Double) this.getSqlMapClientTemplate().queryForObject("costoOrdenMedica", parametros);
	}
}
