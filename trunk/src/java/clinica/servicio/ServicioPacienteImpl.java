package clinica.servicio;

import general.dao.DAO;
import general.dominio.Practica;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import clinica.dominio.Cobertura;
import clinica.dominio.Prestacion;
import clinica.dominio.paciente.HistoriaClinica;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;
import clinica.vo.CoberturaVO;
import clinica.vo.ComportamientoVO;
import clinica.vo.HistoriaClinicaVO;
import clinica.vo.PacienteVO;
import clinica.vo.PlanVO;
import clinica.vo.PracticaVO;
import clinica.vo.PrestacionVO;
import clinica.vo.ReglaVO;

public class ServicioPacienteImpl implements ServicioPaciente {
	
	private DAO<Paciente> pacienteDAO;

	@Transactional
	public List<PacienteVO> obtenerTodos() {
		
		List<PacienteVO> pacientes = new ArrayList<PacienteVO>();		
		for (Paciente paciente : pacienteDAO.obtenerTodos()) {
			pacientes.add(new PacienteVO(paciente.getId(),paciente.getNombre(),paciente.getApellido(),new PlanVO(paciente.getPlanMedico().getCodigo(), paciente.getPlanMedico().getDescripcion(), convertirEnCoberturasVO(paciente.getPlanMedico().getListaDeCoberturas())), convertirEnHistoriaClinicaVO(paciente.getHistClinica())));
		}
		return pacientes;
	}
	
	@Transactional
	public Paciente obtener(Integer numeroPaciente) {
		return this.pacienteDAO.obtener(numeroPaciente);
	}

	private List<CoberturaVO> convertirEnCoberturasVO(List<Cobertura> coberturas){
		List<CoberturaVO> coberturasVO = new ArrayList<CoberturaVO>();
		for (Cobertura cobertura : coberturas) {
			coberturasVO.add(new CoberturaVO(cobertura.getId(), convertirEnVO(cobertura.getPrestacion()), convertirEnReglaVO(cobertura.getRegla())));
		}
		return coberturasVO;
	}
	
	private PrestacionVO convertirEnVO(Prestacion prestacion) {		 	
		return new PrestacionVO(prestacion.getCodigo(), prestacion.getDescripcion());
	}

	private ComportamientoVO convertirEnVO(Comportamiento comportamiento) {		 	
		return new ComportamientoVO(comportamiento.getId(), comportamiento.getConector());
	}

	private ReglaVO convertirEnReglaVO(Regla regla) {		 	
		ReglaVO reglaVO;
		if (regla != null) {
			reglaVO = new ReglaVO(regla.getId(), regla.toString(), convertirEnVO(((ReglaCompuesta)regla).getComportamiento()));
		} else {
			reglaVO = null;
		}		
		return reglaVO;
	}

	private PracticaVO convertirEnPracticaVO(Practica practica) {
		return new PracticaVO(practica.getId(), convertirEnVO(practica.getPrestacion()), practica.getFechaRealizacion());
	}
	
	private List<PracticaVO> convertirEnPracticasVO(List<Practica> practicas) {		
		List<PracticaVO> practicasVO = new ArrayList<PracticaVO>();
		for (Practica practica : practicas) {
			practicasVO.add(convertirEnPracticaVO(practica));			
		}			
		return practicasVO;
	}
	
	private HistoriaClinicaVO convertirEnHistoriaClinicaVO(HistoriaClinica historiaClinica) {
		HistoriaClinicaVO historiaclinicaVO;
		if (historiaClinica != null) {
			historiaclinicaVO = new HistoriaClinicaVO(historiaClinica.getId(), convertirEnPracticasVO(historiaClinica.getListaDePracticas()));
		} else {
			historiaclinicaVO = null;
		}
		return historiaclinicaVO;
	}

	public void setPacienteDAO(DAO<Paciente> pacienteDAO) {
		this.pacienteDAO = pacienteDAO;
	}
}
