package org.YohanRodas.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.YohanRodas.report.GenerarReporte;
import org.YohanRodas.system.Principal;

public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaPacientes(){
        escenarioPrincipal.ventanaPacientes();
    }
     
    public void ventanaEspecialidades(){
        escenarioPrincipal.ventanaEspecialidades();
    }
    
    public void ventanaDoctores(){
        escenarioPrincipal.ventanaDoctores();
    }
    
    public void ventanaMedicamentos(){
        escenarioPrincipal.ventanaMedicamentos();
    }
    
    public void ventanaRecetas(){
        escenarioPrincipal.ventanaRecetas();
    }
    
    public void ventanaDetalleReceta(){
        escenarioPrincipal.ventanaDetalleReceta();
    }
    
    public void ventanaCitas(){
        escenarioPrincipal.ventanaCitas();
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
    
    public void imprimirEspecialidades(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteEspecialidades.jasper", "Reporte de Especialidades", parametros);
    }
    
    public void imprimirPacientes(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReportePacientes.jasper", "Reporte de Pacientes", parametros);
    }
    
    public void imprimirDoctores(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteDoctores.jasper", "Reporte de Doctores", parametros);
    }
    
    public void imprimirMedicamentos(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteMedicamentos.jasper", "Reporte de Medicamentos", parametros);
    }
    
    public void imprimirRecetas(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteRecetas.jasper", "Reporte de Recetas", parametros);
    }
    
    public void imprimirDetalleReceta(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteDetalleRecetas.jasper", "Reporte sobre los detalles de las recetas", parametros);
    }
    
    public void imprimirCitas(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteCitas.jasper", "Reporte de Citas", parametros);
    }
}
