package general.servicio;

import general.dao.DAO;
import general.dominio.Practica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import laboratorio.dominio.excepciones.ExcepcionNumeroOrdenIncorrecto;
import clinica.dominio.Cobertura;
import clinica.dominio.Medico;
import clinica.dominio.OrdenMedica;
import clinica.dominio.Plan;
import clinica.dominio.Prestacion;
import clinica.dominio.paciente.HistoriaClinica;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Regla;
import clinica.vo.CoberturaVO;
import clinica.vo.HistoriaClinicaVO;
import clinica.vo.MedicoVO;
import clinica.vo.OrdenMedicaVO;
import clinica.vo.PacienteVO;
import clinica.vo.PlanVO;
import clinica.vo.PracticaVO;
import clinica.vo.PrestacionVO;
import clinica.vo.ReglaVO;

public class ServicioOrdenMedicaImpl implements ServicioOrdenMedica {
	
	private DAO<OrdenMedica> ordenMedicaDAO;
	private DAO<Paciente> pacienteDAO;
	private DAO<Prestacion> prestacionDAO;

	@Transactional
	public List<OrdenMedicaVO> obtenerTodos() {
		
		List<OrdenMedicaVO> ordenesmedicas = new ArrayList<OrdenMedicaVO>();
		
		for (OrdenMedica ordenMedica : ordenMedicaDAO.obtenerTodos()) {
			ordenesmedicas.add(new OrdenMedicaVO(ordenMedica.getId(), ordenMedica.getFechaEmision(), obtenerPracticasVO(ordenMedica)));
		}
		return ordenesmedicas;	
	}

	@Transactional
	public void insertar(OrdenMedicaVO ordenMedicaVo) {
		OrdenMedica ordenMedica = new OrdenMedica();
		ordenMedica.setFechaEmision(ordenMedicaVo.getFechaEmision());
		ordenMedicaDAO.insertar(ordenMedica);		
	}
	
	@Transactional
	public Integer crearOrdenMedica() {
		OrdenMedica ordenMedica = new OrdenMedica();
		ordenMedicaDAO.insertar(ordenMedica);
		return ordenMedica.getId();
	}

	@Transactional
	public OrdenMedicaVO obtener(Integer numeroOrdenMedica) {							
		OrdenMedica ordenMedica = ordenMedicaDAO.obtener(numeroOrdenMedica);		
		if (ordenMedica==null) throw new ExcepcionNumeroOrdenIncorrecto();
		PacienteVO pacienteVo;
		MedicoVO medicoVo;		
		if (ordenMedica.getPaciente()!=null){		
			pacienteVo=new PacienteVO(ordenMedica.getPaciente().getId(),ordenMedica.getPaciente().getNombre(),ordenMedica.getPaciente().getApellido());
			pacienteVo.setPlanMedico(convertirEnVO(ordenMedica.getPaciente().getPlanMedico()));
			if (ordenMedica.getPaciente().getHistClinica() != null)
				pacienteVo.setHistoriaClinica(convertirEnVO(ordenMedica.getPaciente().getHistClinica()));
		}else {
			pacienteVo=new PacienteVO(0," "," ");
		}
		if (ordenMedica.getMedicoEmisor()!=null){		
			medicoVo=new MedicoVO(ordenMedica.getMedicoEmisor().getId(),ordenMedica.getMedicoEmisor().getNombre());
		}else {
			medicoVo=new MedicoVO(0," ");
		}		
		OrdenMedicaVO ordenMedicaVO = new OrdenMedicaVO(ordenMedica.getId(), ordenMedica.getFechaEmision(), obtenerPracticasVO(ordenMedica),pacienteVo,medicoVo, ordenMedica.estaAprobada());
		return ordenMedicaVO;
	}

	@Transactional
	public void borrarPractica(Integer numeroOrdenMedica, Integer numeroPractica) {
		OrdenMedica ordenMedica = ordenMedicaDAO.obtener(numeroOrdenMedica);
		ordenMedica.borrarPractica(numeroPractica);
	}

	@Transactional
	public List<PracticaVO> obtenerPracticasDeOrden(Integer numeroOrdenMedica) {		
		return this.obtenerPracticasVO(ordenMedicaDAO.obtener(numeroOrdenMedica));
	}		
	
	@Transactional
	public void actualizar(Integer ordenId, PracticaVO practica, OrdenMedicaVO ordenMedicaVO) {		
		OrdenMedica ordenMedica = ordenMedicaDAO.obtener(ordenId);
		if (ordenMedica.getMedicoEmisor() == null)
			ordenMedica.setMedicoEmisor(convertirEnMedico(ordenMedicaVO.getMedico()));

		Paciente paciente = pacienteDAO.obtener(ordenMedicaVO.getPaciente().getId());
		ordenMedica.setPaciente(paciente);
		
		ordenMedica.agregarPractica(convertirEnPractica(practica));
	}
	
	@Transactional
	public void autorizarOrdenMedica(OrdenMedicaVO ordenMedicaVO) {
		OrdenMedica ordenMedica = ordenMedicaDAO.obtener(ordenMedicaVO.getId());		
		ordenMedica.setMedicoEmisor(convertirEnMedico(ordenMedicaVO.getMedico()));		
		ordenMedica.setPaciente(pacienteDAO.obtener(ordenMedicaVO.getPaciente().getId()));
		ordenMedica.autorizar();
	}
	
	private List<PracticaVO> obtenerPracticasVO(OrdenMedica ordenMedica) {		
		List<PracticaVO> practicasVO = new ArrayList<PracticaVO>();
		for (Practica practica : ordenMedica.getPracticas()) {
			practicasVO.add(convertirEnVO(practica));			
		}			
		return practicasVO;
	}

	private List<CoberturaVO> convertirEnListaCoberturasVO(List<Cobertura> coberturas){
		List<CoberturaVO> coberturasVO = new ArrayList<CoberturaVO>();
		for (Cobertura cobertura : coberturas) {
			coberturasVO.add(convertirEnVO(cobertura));
		}
		return coberturasVO;
	}

	private List<PracticaVO> convertirEnListaPracticasVO(List<Practica> practicas) {		
		List<PracticaVO> practicasVO = new ArrayList<PracticaVO>();
		for (Practica practica : practicas) {
			practicasVO.add(convertirEnVO(practica));			
		}			
		return practicasVO;
	}
	
	private PlanVO convertirEnVO(Plan plan){
		return new PlanVO(plan.getCodigo(), plan.getDescripcion(), convertirEnListaCoberturasVO(plan.getListaDeCoberturas()));
	}

	private CoberturaVO convertirEnVO(Cobertura cobertura){
		return new CoberturaVO(cobertura.getId(), convertirEnVO(cobertura.getPrestacion()), convertirEnVO(cobertura.getRegla()));
	}

	private ReglaVO convertirEnVO(Regla regla) {		 	
		ReglaVO reglaVO;
		if (regla != null) {
			reglaVO = new ReglaVO(regla.getId(), regla.toString());
		} else {
			reglaVO = null;
		}		
		return reglaVO;
	}
	
	private PracticaVO convertirEnVO(Practica practica) {
		return new PracticaVO(practica.getId(), convertirEnVO(practica.getPrestacion()), practica.getFechaRealizacion());
	}

	private PrestacionVO convertirEnVO(Prestacion prestacion) {		 	
		return new PrestacionVO(prestacion.getCodigo(), prestacion.getDescripcion());
	}

	private HistoriaClinicaVO convertirEnVO(HistoriaClinica historiaClinica) {
		return new HistoriaClinicaVO(historiaClinica.getId(), convertirEnListaPracticasVO(historiaClinica.getListaDePracticas()));
	}
	
	private Practica convertirEnPractica(PracticaVO practicaVO) {
		Practica practica = new Practica();
		Prestacion prestacion = prestacionDAO.obtener(practicaVO.getPrestacion().getCodigo());
		practica.setFechaRealizacion(new Date());
		practica.setPrestacion(prestacion);
		return practica;
	}
	
	private Medico convertirEnMedico(MedicoVO medicoVO) {
		Medico medico = new Medico();
		medico.setId(medicoVO.getId());
		medico.setNombre(medicoVO.getNombre());
		return medico;
	}

	public void setPrestacionDAO(DAO<Prestacion> prestacionDAO) {
		this.prestacionDAO = prestacionDAO;
	}

	public void setPacienteDAO(DAO<Paciente> pacienteDAO) {
		this.pacienteDAO = pacienteDAO;
	}

	public void setOrdenMedicaDAO(DAO<OrdenMedica> medicoDAO) {
		this.ordenMedicaDAO = medicoDAO;
	}
}
