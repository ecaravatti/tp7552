package clinica.presenter;

import general.servicio.ServicioOrdenMedica;
import general.servicio.ServicioPractica;
import clinica.servicio.ServicioMedico;
import clinica.servicio.ServicioPaciente;
import clinica.servicio.ServicioPrestacion;
import clinica.vo.OrdenMedicaVO;
import clinica.vo.PracticaVO;
import clinica.web.EmitirOrdenesVista;

public class EmitirOrdenesPresenterImpl implements EmitirOrdenesPresenter {
	
	private EmitirOrdenesVista vista;	
	private ServicioPaciente servicioPaciente;
	private ServicioMedico servicioMedico;
	private ServicioPractica servicioPractica;
	private ServicioPrestacion servicioPrestacion;
	private ServicioOrdenMedica servicioOrdenMedica;
	
    public ServicioPrestacion getServicioPrestacion() {
		return servicioPrestacion;
	}

	public void setServicioPrestacion(ServicioPrestacion servicioPrestacion) {
		this.servicioPrestacion = servicioPrestacion;
	}

	public ServicioPractica getServicioPractica() {
		return servicioPractica;
	}

	public void setServicioPractica(ServicioPractica servicioPractica) {
		this.servicioPractica = servicioPractica;
	}

	public void setServicioPaciente(ServicioPaciente servicioPaciente) {
		this.servicioPaciente = servicioPaciente;
	}

	public void setServicioMedico(ServicioMedico servicioMedico) {
		this.servicioMedico = servicioMedico;
	}

	public ServicioOrdenMedica getServicioOrdenMedica() {
		return servicioOrdenMedica;
	}

	public void setServicioOrdenMedica(ServicioOrdenMedica servicioOrdenMedica) {
		this.servicioOrdenMedica = servicioOrdenMedica;
	}
	
	public void inicializar() {
		if (vista.getNumeroOrdenMedica() == null)
			vista.setNumeroOrdenMedica(servicioOrdenMedica.crearOrdenMedica());
		vista.setEstadoOrdenMedica(servicioOrdenMedica.obtener(vista.getNumeroOrdenMedica()).getAprobada());
		vista.setPacientes(servicioPaciente.obtenerTodos());
		vista.setMedicos(servicioMedico.obtenerTodos());
		vista.setPrestaciones(servicioPrestacion.obtenerTodas());
		vista.setNuevasPracticas(servicioPractica.obtenerTodas());
		vista.inicializar();
	}

	public void setVista(EmitirOrdenesVista vista) {
		this.vista = vista;
	}

	public void borrar() {		
		servicioOrdenMedica.borrarPractica(vista.getNumeroOrdenMedica(), vista.getPracticaSeleccionada().getId());
	}

	public void actualizarCantidad() {
		vista.setCantidadPracticas(servicioOrdenMedica.obtener(vista.getNumeroOrdenMedica()).getPracticas().size());
	}
	
	public void obtenerPracticas(Integer numeroOrdenMedica) {
		vista.setNuevasPracticas(servicioOrdenMedica.obtener(numeroOrdenMedica).getPracticas());
	}

	public void agregarPractica() {
		OrdenMedicaVO ordenMedicaVO = new OrdenMedicaVO(vista.getNumeroOrdenMedica(),null,null,vista.getPacienteSeleccionado(), vista.getMedicoSeleccionado());
		PracticaVO practicaVO = new PracticaVO(1, vista.getPrestacionSeleccionada());		
		servicioOrdenMedica.actualizar(vista.getNumeroOrdenMedica(), practicaVO, ordenMedicaVO);		
	}
	
	public void autorizarOrdenMedica() {
		OrdenMedicaVO ordenMedicaVO = new OrdenMedicaVO(vista.getNumeroOrdenMedica(),null,null,vista.getPacienteSeleccionado(), vista.getMedicoSeleccionado());		
		servicioOrdenMedica.autorizarOrdenMedica(ordenMedicaVO);			
	}
	
	public void actualizarCostoAdicional() {
		vista.setCostoOrdenMedica(servicioPractica.obtenerCostoOrdenMedica(vista.getNumeroOrdenMedica()));
	}	
}
