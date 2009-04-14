package clinica.vo;

import java.io.Serializable;
import java.util.Date;

public class PracticaVO implements Serializable {

	private static final long serialVersionUID = -6700760297832860672L;

	private Integer id;	
	private String nombre;	
	private PrestacionVO prestacion;
	private Date fechaRealizacion;
	private Integer ordenMedica;
	private String paciente;
	
		
	public PracticaVO() {
	}
	
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public PracticaVO(Integer id,String nombre) {
		this.id=id;
		this.nombre=nombre;	
	}

	public PracticaVO(Integer id, PrestacionVO prestacionVO) {
		this.id=id;
		this.nombre = prestacionVO.getDescripcion();
		this.prestacion = prestacionVO;
	}
	
	public PracticaVO(Integer id, PrestacionVO prestacionVO, Date fechaRealizacion) {
		this.id=id;
		this.nombre = prestacionVO.getDescripcion();
		this.prestacion = prestacionVO;
		this.fechaRealizacion = fechaRealizacion;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public PrestacionVO getPrestacion() {
		return prestacion;
	}

	public void setPrestacion(PrestacionVO prestacion) {
		this.prestacion = prestacion;
	}

	public void setOrdenMedica(Integer ordenMedica) {
		this.ordenMedica = ordenMedica;
	}

	public Integer getOrdenMedica() {
		return ordenMedica;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getPaciente() {
		return paciente;
	}
}
