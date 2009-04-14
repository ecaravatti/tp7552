package clinica.dominio.paciente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import clinica.dominio.Plan;
import clinica.dominio.Sexo;

@Entity
@Table(name = "paciente")
public class Paciente {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinColumn(name = "plan_id")
	@ForeignKey(name = "paciente_plan_fk")	
	private Plan planMedico;
	
	@OneToOne
	@Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN})
	@JoinColumn(name = "historia_clinica_id")
	@ForeignKey(name = "paciente_historia_clinia_fk")
	private HistoriaClinica histClinica;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "sexo")
	private Sexo sexo;
	
	@Column(name = "numero_documento")
	private String numeroDocumento; //Es String porque uno no sabe si siempre se usar� un n�mero.
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	
	public Paciente() {
		this.fechaIngreso = new Date();		
	}
	
	public void setPlanMedico(Plan planMedico) {
		this.planMedico = planMedico;
	}
	
	public Plan getPlanMedico() {
		return planMedico;
	}
	
	public void setHistClinica(HistoriaClinica histClinica) {
		this.histClinica = histClinica;
	}
	
	public HistoriaClinica getHistClinica() {
		return histClinica;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	public Sexo getSexo() {
		return sexo;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
}
