package clinica.vo;

import java.io.Serializable;
import java.util.List;

public class PlanVO implements Serializable {

	private static final long serialVersionUID = -3367021153743248109L;

	private Integer codigo;	
	private String descripcion;
	private List<CoberturaVO> listaDeCoberturas;
	
	public List<CoberturaVO> getListaDeCoberturas() {
		return listaDeCoberturas;
	}

	public void setListaDeCoberturas(List<CoberturaVO> listaDeCoberturas) {
		this.listaDeCoberturas = listaDeCoberturas;
	}

	public PlanVO(Integer codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public PlanVO(Integer codigo, String descripcion, List<CoberturaVO> listaCoberturasVO) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.listaDeCoberturas = listaCoberturasVO;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer id) {
		this.codigo = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String nombre) {
		this.descripcion = nombre;
	}
}
