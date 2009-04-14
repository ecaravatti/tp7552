package clinica.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrdenMedicaVO implements Serializable {

	private static final long serialVersionUID = -2206814279884811623L;

	private Integer id;
	private Date fechaEmision;
	private List<PracticaVO> practicas;
	private PacienteVO paciente;
	private MedicoVO medico;
	private Boolean aprobada;
	private Double costoTotal;
	
	public OrdenMedicaVO(Integer id, Date fechaEmision, List<PracticaVO> practicas) {
		this.id = id;
		this.fechaEmision = fechaEmision;
		this.practicas = practicas;
	}
	
	public OrdenMedicaVO(Integer id, Date fechaEmision, List<PracticaVO> practicas,PacienteVO paciente,MedicoVO medico) {
		this.id = id;
		this.fechaEmision = fechaEmision;
		this.practicas = practicas;
		this.paciente=paciente;
		this.medico=medico;
	}
	
	public OrdenMedicaVO(Integer id, Date fechaEmision, List<PracticaVO> practicas,PacienteVO paciente,MedicoVO medico, boolean aprobada) {
		this.id = id;
		this.fechaEmision = fechaEmision;
		this.practicas = practicas;
		this.paciente=paciente;
		this.medico=medico;
		this.aprobada=aprobada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public List<PracticaVO> getPracticas() {
		return practicas;
	}

	public void setPracticas(List<PracticaVO> practicas) {
		this.practicas = practicas;
	}
	
	public PacienteVO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteVO paciente) {
		this.paciente = paciente;
	}

	public MedicoVO getMedico() {
		return medico;
	}

	public void setMedico(MedicoVO medico) {
		this.medico = medico;
	}
	
	public Boolean getAprobada() {
		return aprobada;
	}

	public void setAprobada(Boolean aprobada) {
		this.aprobada = aprobada;
	}

	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}
}