package clinica.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import clinica.dominio.excepciones.ExcepcionPrestacionNoEstaEnPlan;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Regla;

@Entity
@Table(name = "plan")
public class Plan {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "costo_mensual")
	private Float costoMensual;
	
	@OneToMany
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "plan_cobertura", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "cobertura_id"))
	@ForeignKey(name = "plan_fk", inverseName = "cobertura_fk")
	private List<Cobertura> listaDeCoberturas;
	
	public Plan() {
		this.listaDeCoberturas = new ArrayList<Cobertura>();
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setCostoMensual(Float costoMensual) {
		this.costoMensual = costoMensual;
	}

	public Float getCostoMensual() {
		return costoMensual;
	}
	
	public void agregarPrestacion(Prestacion pNuevaPrestacion) {
		Cobertura cCobertura = new Cobertura();
		cCobertura.setPrestacion(pNuevaPrestacion);
		listaDeCoberturas.add(cCobertura);
	}
	
	public void agregarReglaAPrestacion(Regla rRegla, Prestacion pNuevaPrestacion, Comportamiento comportamiento) {		
		Cobertura cCobertura=this.buscarCobertura(pNuevaPrestacion.getCodigo());		 
		cCobertura.agregarRegla(rRegla, comportamiento);
		
	}
	
	public Prestacion buscarPrestacion(int codigo) {
		for(Cobertura cCobertura : listaDeCoberturas) {
			if (cCobertura.getPrestacion().getCodigo() == codigo) 
				return cCobertura.getPrestacion();
		}
		return null;
	}
	
	public Cobertura buscarCobertura(int codigo) {
		for(Cobertura cCobertura : listaDeCoberturas) {
			if (cCobertura.getPrestacion().getCodigo() == codigo)
				return cCobertura;
		}
		return null;
	}

	public void ejecutarReglas(Prestacion pPrestacion, Paciente pPaciente) {
		Cobertura laCobertura = buscarCobertura(pPrestacion.getCodigo());
		ExcepcionPrestacionNoEstaEnPlan Ex = new ExcepcionPrestacionNoEstaEnPlan();
		Ex.setPrestacionNoCubierta(pPrestacion.getDescripcion());
		if ( laCobertura == null ) throw Ex;
		else laCobertura.ejecutarReglas(pPaciente);		
	}

	public List<Cobertura> getListaDeCoberturas() {
		return listaDeCoberturas;
	}

	public void setListaDeCoberturas(List<Cobertura> listaDeCoberturas) {
		this.listaDeCoberturas = listaDeCoberturas;
	}
	
}
