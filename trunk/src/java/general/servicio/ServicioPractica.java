package general.servicio;

import java.util.List;

import laboratorio.vo.ItemResultadoVO;
import clinica.vo.PracticaVO;

public interface ServicioPractica {

	public List<ItemResultadoVO> obtenerResultadosPractica(Integer PracticaId);
	
	public List<PracticaVO> obtenerTodas();

	public void borrar(Integer id);
	
	public List<PracticaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad);
	
	public Integer obtenerCantidad();

	public void insertar(PracticaVO practicaVO);
	
	public void modificarResultados(Integer codigoPractica, List<ItemResultadoVO> resultados);
	
	public Double obtenerCostoOrdenMedica (Integer numeroOrdenMedica);
}
