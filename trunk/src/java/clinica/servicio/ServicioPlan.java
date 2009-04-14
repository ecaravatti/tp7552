package clinica.servicio;

import java.util.List;

import clinica.vo.CoberturaVO;
import clinica.vo.PlanVO;

public interface ServicioPlan {

	public List<PlanVO> obtenerTodos();
	
	public List<CoberturaVO> obtenerCoberturas(Integer codigoPlan);
	
}
