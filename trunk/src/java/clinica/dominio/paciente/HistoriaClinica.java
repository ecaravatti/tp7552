package clinica.dominio.paciente;


import general.dominio.Practica;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import clinica.dominio.Prestacion;
import clinica.dominio.reglas.Frecuencia;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToMany
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "historia_clinica_practica", joinColumns = @JoinColumn(name = "historia_clinica_id"), inverseJoinColumns = @JoinColumn(name = "practica_id"))
	private List<Practica> listaDePracticas;
	
	public HistoriaClinica() {		
		this.listaDePracticas = new ArrayList<Practica>();
	}
	
	public void agregarPractica(Practica practica) {
		this.listaDePracticas.add(practica);
	}

	public void borrarPractica(Integer idPractica) {
		Practica practicaAEliminar = new Practica();
		for (Practica practica: this.listaDePracticas) {
			if (practica.getId().equals(idPractica)) {				
				practicaAEliminar = practica;
			}
		}		
		this.listaDePracticas.remove(practicaAEliminar);
	}
	
	public int obtenerCantidadDePracticasPorPeriodo(Prestacion prestacion, Frecuencia frecuencia) {
		int cantidadPracticas = 0;
		int cantidadDeDiasARestar = 0;
		Calendar fechaActual = Calendar.getInstance();		
		
		switch (frecuencia) {
			case DIARIO:
				cantidadDeDiasARestar = 1;
				break;						
			case SEMANAL:
				cantidadDeDiasARestar = 7;
				break;			
			case QUINCENAL:
				cantidadDeDiasARestar = 15;
				break;
			case BIMESTRAL:
				cantidadDeDiasARestar = 60;
				break;												
			case TRIMESTRAL:
				cantidadDeDiasARestar = 90;
				break;								
			case CUATRIMESTRAL:
				cantidadDeDiasARestar = 120;
				break;				
			case SEMESTRAL:
				cantidadDeDiasARestar = 180;
				break;
			case ANUAL:
				cantidadDeDiasARestar = 360;				
				break;
			case MENSUAL:
				cantidadDeDiasARestar = 30;				
				break;				
		}
		
		Calendar fechaPractica = Calendar.getInstance();		
		Calendar fechaInicio = Calendar.getInstance(); 		
		fechaInicio.add(Calendar.DAY_OF_MONTH, -1 * cantidadDeDiasARestar);
		
		for (Practica practica : this.listaDePracticas) 
		{			
			fechaPractica.setTime(practica.getFechaRealizacion());			
			if (practica.getPrestacion().getCodigo().compareTo(prestacion.getCodigo()) == 0 && (fechaPractica.after(fechaInicio) && fechaPractica.before(fechaActual)))  
				cantidadPracticas++;
		}
		return cantidadPracticas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Practica> getListaDePracticas() {
		return listaDePracticas;
	}

	public void setListaDePracticas(List<Practica> listaDePracticas) {
		this.listaDePracticas = listaDePracticas;
	}
}
