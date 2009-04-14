package clinica.vo;

import java.io.Serializable;

public class MedicoVO implements Serializable {

	private static final long serialVersionUID = 9164485653047700410L;

	private Integer id;
	
	private String nombre;
	
	private String especialidad;
		
	public MedicoVO(Integer id,String nombre) {
		this.id=id;
		this.nombre=nombre;	
	}
	
	public MedicoVO(Integer id, String nombre, String especialidad) {
		this.id=id;
		this.nombre=nombre;	
		this.especialidad=especialidad;
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


	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}


	public String getEspecialidad() {
		return especialidad;
	}
}
