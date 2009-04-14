package clinica.vo;

import java.io.Serializable;

public class PacienteVO implements Serializable {

	private static final long serialVersionUID = 8245913125320948205L;

	private Integer id;

	private String nombre;	
	private String apellido;
	private PlanVO planMedico;
	private HistoriaClinicaVO historiaClinica;
	
	public HistoriaClinicaVO getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(HistoriaClinicaVO historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public PacienteVO(Integer id,String nombre,String apellido) {
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
	}

	public PacienteVO(Integer id,String nombre,String apellido, PlanVO plan) {
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
		this.planMedico = plan;
	}

	public PacienteVO(Integer id,String nombre,String apellido, PlanVO plan, HistoriaClinicaVO historiaClinica) {
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
		this.planMedico = plan;
		this.historiaClinica = historiaClinica;
	}
	
	public PlanVO getPlanMedico() {
		return planMedico;
	}
	
	
	public void setPlanMedico(PlanVO planMedico) {
		this.planMedico = planMedico;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
