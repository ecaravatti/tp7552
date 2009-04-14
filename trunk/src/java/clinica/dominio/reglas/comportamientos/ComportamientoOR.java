package clinica.dominio.reglas.comportamientos;

import general.dominio.excepciones.ExcepcionNegocio;

import java.util.Iterator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import clinica.dominio.Prestacion;
import clinica.dominio.excepciones.ExcepcionValidacionReglas;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Regla;

@Entity
@DiscriminatorValue("OR")
public class ComportamientoOR extends Comportamiento {

	public void evaluar(Paciente paciente, Prestacion prestacion, Iterator<Regla> reglasIterator) {
		boolean hayReglas = false;
		ExcepcionValidacionReglas excepciones = new ExcepcionValidacionReglas();
		while (reglasIterator.hasNext()) {
			hayReglas = true;
			try {
				reglasIterator.next().evaluar(paciente, prestacion);
				return; //Al menos una cumplió, no arrojo excepción y termino inmediatamente.
			} catch (ExcepcionNegocio e) {				
				excepciones.agregarExcepcion(e);
			}
		}
		if (hayReglas) throw excepciones; //Terminé de iterar (había al menos una regla para iterar) y ninguna cumplió.
	}
	
	@Override
	public String getConector() {
		return "OR";
	}
	

}
