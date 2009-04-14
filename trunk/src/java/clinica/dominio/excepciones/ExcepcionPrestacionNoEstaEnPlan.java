package clinica.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

public class ExcepcionPrestacionNoEstaEnPlan extends ExcepcionNegocio {
	
	private static final long serialVersionUID = -1503045035981463966L;	
	private String prestacionNoCubierta; 
	
	public String getPrestacionNoCubierta() {
		return prestacionNoCubierta;
	}

	public void setPrestacionNoCubierta(String prestacionNoCubierta) {
		this.prestacionNoCubierta = prestacionNoCubierta;
	}

	public ExcepcionPrestacionNoEstaEnPlan() {
		this.clave="ExcepcionPrestacionNoEstaEnPlan";
	}	

	public boolean equals (ExcepcionNegocio e){
		boolean result = false;
		if (super.equals(e))
			result = (this.getPrestacionNoCubierta().equals(((ExcepcionPrestacionNoEstaEnPlan)e).getPrestacionNoCubierta()));			
		return result;
	}
}
