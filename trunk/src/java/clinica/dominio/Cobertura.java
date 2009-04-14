package clinica.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;

@Entity
@Table(name = "cobertura")
public class Cobertura {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "prestacion_id")
	@ForeignKey(name = "cobertura_prestacion_fk")
	private Prestacion prestacion;
	
	@ManyToOne
	@Cascade(value = {CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "regla_id")
	@ForeignKey(name = "cobertura_regla_fk")
	private Regla regla;
	
	@Column(name = "costo_adicional")
	private double costoAdicional;
	
	public Cobertura() {
		this.costoAdicional = 0;
	}
	
	public void setCostoAdicional(double costoAdicional) {
		this.costoAdicional = costoAdicional;
	}

	public double getCostoAdicional() {
		return costoAdicional;
	}

	public void setPrestacion(Prestacion prestacion) {
		this.prestacion = prestacion;
	}
	
	public Prestacion getPrestacion() {
		return prestacion;
	}
	
	public void agregarRegla(Regla mRegla, Comportamiento comportamiento) {		
		Regla reglaCompuesta = new ReglaCompuesta(comportamiento);
		reglaCompuesta.agregarRegla(this.regla);
		reglaCompuesta.agregarRegla(mRegla);
		this.regla = reglaCompuesta;		
	}
	
	public void ejecutarReglas(Paciente pPaciente) {
		if (regla != null)
			regla.evaluar(pPaciente, this.prestacion);
	}

	public Regla getRegla() {
		return regla;
	}

	public void setRegla(Regla regla) {
		this.regla = regla;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
