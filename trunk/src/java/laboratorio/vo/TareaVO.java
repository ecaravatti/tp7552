package laboratorio.vo;

import java.io.Serializable;

import laboratorio.dominio.tarea.EstadoTarea;


public class TareaVO implements Serializable, Comparable<TareaVO> {

	private static final long serialVersionUID = -1669878591433599733L;

	private Integer id;
	private String descripcion;
	private EstadoTarea estado;
	private Integer areaId;

	public TareaVO() {}
	
	public TareaVO(Integer id, String descripcion, EstadoTarea estado, Integer areaId) {
		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado;
		this.areaId = areaId;
	}
	
	public int compareTo(TareaVO tareaVO) {
		return this.getDescripcion().compareToIgnoreCase(tareaVO.getDescripcion());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public EstadoTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadoTarea estado) {
		this.estado = estado;
	}
	
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

}
