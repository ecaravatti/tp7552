package clinica.servicio;

import general.dao.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import clinica.dominio.Cobertura;
import clinica.dominio.Plan;
import clinica.dominio.Prestacion;
import clinica.dominio.reglas.Regla;
import clinica.vo.CoberturaVO;
import clinica.vo.PlanVO;
import clinica.vo.PrestacionVO;
import clinica.vo.ReglaVO;


public class ServicioPlanImpl implements ServicioPlan {

	private DAO<Plan> planDAO;

	@Transactional
	public List<PlanVO> obtenerTodos() {
		List<PlanVO> planes = new ArrayList<PlanVO>();
		
		for (Plan plan : planDAO.obtenerTodos()) {
			planes.add(new PlanVO(plan.getCodigo(), plan.getDescripcion()));
		}
		return planes;
	}

	@Transactional
	public List<CoberturaVO> obtenerCoberturas(Integer codigoPlan) {
		Plan plan = planDAO.obtener(codigoPlan);
		List<CoberturaVO> coberturasVO = new ArrayList<CoberturaVO>();
		Prestacion prestacion;
		Regla regla;
		PrestacionVO prestacionVO;
		ReglaVO reglaVO;
		for(Cobertura cobertura : plan.getListaDeCoberturas()) {
			prestacion = cobertura.getPrestacion();
			regla = cobertura.getRegla();
			if (regla != null) {
				reglaVO = new ReglaVO(regla.getId(), regla.toString());
			} else {
				reglaVO = null;
			}
			prestacionVO = new PrestacionVO(prestacion.getCodigo(), prestacion.getDescripcion());
			coberturasVO.add(new CoberturaVO(cobertura.getId(), prestacionVO, reglaVO));
		}
		return coberturasVO;
	}

	public void setPlanDAO(DAO<Plan> planDAO) {
		this.planDAO = planDAO;
	}
}
