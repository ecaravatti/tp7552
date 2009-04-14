package general.servicio;

import java.util.List;

import clinica.vo.OrdenMedicaVO;
import clinica.vo.PracticaVO;

public interface ServicioOrdenMedica {
	
	public List<OrdenMedicaVO> obtenerTodos();
	
	public void insertar(OrdenMedicaVO ordenMedicaVo);
	
	public Integer crearOrdenMedica();
	
	public OrdenMedicaVO obtener(Integer numeroOrdenMedica);
	
	public void actualizar(Integer ordenId, PracticaVO practica, OrdenMedicaVO ordenMedicaVO);
	
	public void borrarPractica(Integer numeroOrdenMedica, Integer numeroPractica);
	
	public List<PracticaVO> obtenerPracticasDeOrden(Integer numeroOrdenMedica);
	
	public void autorizarOrdenMedica(OrdenMedicaVO ordenMedicaVO);
		
}
