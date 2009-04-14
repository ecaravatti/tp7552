package laboratorio.presenter;

import laboratorio.web.PanelAreaVista;

public interface PanelAreaPresenter {

	public void cargarTareasNoAsignadas(PanelAreaVista vista);
	public void cargarTecnicosArea(PanelAreaVista vista);
	public void cargarEstrategias(PanelAreaVista vista);
	public void agregarTareaNoAsignada(PanelAreaVista vista);
	public void asignarTareasAutomaticamente(PanelAreaVista vista);
}
