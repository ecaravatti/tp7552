package general.dao;

import java.util.List;

import clinica.vo.PracticaVO;

public interface PracticaDAO {
	
	public List<PracticaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad);
	
	public Integer obtenerCantidad();
	
	public Double obtenerCostoOrdenMedica (Integer numeroOrdenMedica);

}
