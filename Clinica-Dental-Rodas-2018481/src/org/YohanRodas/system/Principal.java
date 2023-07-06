/* Documentacion
Nombre Completo: Yohan Andres Rodas Macal
Codigo Tecnico: IN5AM
Carne: 2018418
Fecha Creacion:28/03/2022
Fecha Modificaciones:10/05/2022
*/

package org.YohanRodas.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.YohanRodas.controller.CitaController;
import org.YohanRodas.controller.DetalleRecetaController;
import org.YohanRodas.controller.DoctorController;
import org.YohanRodas.controller.EspecialidadesController;
import org.YohanRodas.controller.LoginController;
import org.YohanRodas.controller.MedicamentoController;
import org.YohanRodas.controller.MenuPrincipalController;
import org.YohanRodas.controller.PacienteController;
import org.YohanRodas.controller.ProgramadorController;
import org.YohanRodas.controller.RecetaController;
import org.YohanRodas.controller.UsuarioController;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/YohanRodas/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Clínica Dental Rodas");
        escenarioPrincipal.getIcons().add(new Image("/org/YohanRodas/image/LogoEmpresa.png"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/YohanRodas/view/MenuPrincipalview.fxml"));
//        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml", 600, 400);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuariosView.fxml", 528, 400);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController ventanaMenu = (MenuPrincipalController)cambiarEscena("MenuPrincipalview.fxml",601,337);
            ventanaMenu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController ventanaProgramador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",532,400);
            ventanaProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ventanaPacientes(){
        try{
            PacienteController ventanaPacientes = (PacienteController)cambiarEscena("PacientesView.fxml",1000,400);
            ventanaPacientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCitas(){
        try{
            CitaController ventanaCitas = (CitaController)cambiarEscena("CitasView.fxml",982,400);
            ventanaCitas.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEspecialidades(){
        try{
            EspecialidadesController ventanaEspecialidades = (EspecialidadesController)cambiarEscena("EspecialidadesView.fxml",851,400);
            ventanaEspecialidades.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaMedicamentos(){
        try{
            MedicamentoController ventanaMedicamentos = (MedicamentoController)cambiarEscena("MedicamentosView.fxml",554,400);
            ventanaMedicamentos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDoctores(){
        try{
            DoctorController ventanaDoctores = (DoctorController)cambiarEscena("DoctoresView.fxml",851,400);
            ventanaDoctores.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaRecetas(){
        try{
            RecetaController ventanaRecetas = (RecetaController)cambiarEscena("RecetasView.fxml", 582, 400);
            ventanaRecetas.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDetalleReceta(){
        try{
            DetalleRecetaController ventanaDetalleReceta = (DetalleRecetaController)cambiarEscena("DetalleRecetaView.fxml", 742, 400);
            ventanaDetalleReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto)throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = cargadorFXML.getController();
        return resultado;
    }


}
