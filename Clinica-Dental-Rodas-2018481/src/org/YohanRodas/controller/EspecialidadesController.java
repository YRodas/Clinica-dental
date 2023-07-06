package org.YohanRodas.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.YohanRodas.bean.Especialidades;
import org.YohanRodas.db.Conexion;
import org.YohanRodas.report.GenerarReporte;
import org.YohanRodas.system.Principal;

public class EspecialidadesController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Especialidades> listaEspecialidades;
    @FXML private TextField txtCodigoEspecialidad;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblEspecialidades;
    @FXML private TableColumn colcodEspecialidad;
    @FXML private TableColumn colDes;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize (URL location, ResourceBundle resources){
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblEspecialidades.setItems(getEspecialidades());
        colcodEspecialidad.setCellValueFactory(new PropertyValueFactory<Especialidades, Integer>("codigoEspecialidad"));
        colDes.setCellValueFactory(new PropertyValueFactory<Especialidades, String>("descripcion"));
    }
    
    public void seleccionarElemento(){
        try{txtCodigoEspecialidad.setText(String.valueOf(((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getCodigoEspecialidad()));
        txtDescripcion.setText((((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getDescripcion()));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }
    
    public ObservableList<Especialidades> getEspecialidades(){
        ArrayList<Especialidades> lista = new ArrayList<Especialidades>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEspecialidades()}");
        ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Especialidades(resultado.getInt("codigoEspecialidad"),
                                resultado.getString("descripcion")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEspecialidades = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/YohanRodas/image/Guardar.png"));
                imgEliminar.setImage(new Image("/org/YohanRodas/image/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/YohanRodas/image/Button4.png"));
                imgEliminar.setImage(new Image("/org/YohanRodas/image/Button3.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }        
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/YohanRodas/image/Button4.png"));
                imgEliminar.setImage(new Image("/org/YohanRodas/image/Button3.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblEspecialidades.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Especialidad",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEspecialidad(?)}");
                            procedimiento.setInt(1, ((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getCodigoEspecialidad());
                            procedimiento.execute();
                            listaEspecialidades.remove(tblEspecialidades.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                    }if(respuesta == JOptionPane.NO_OPTION){
                        limpiarControles();
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }       
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblEspecialidades.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/YohanRodas/image/Cambiar.png"));
                    imgReporte.setImage(new Image("/org/YohanRodas/image/Cancelar.png"));
                    activarControles();
                    txtCodigoEspecialidad.setEditable(false);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/YohanRodas/image/Button2.png"));
                imgReporte.setImage(new Image("/org/YohanRodas/image/Button1.png"));
                desactivarControles();
                limpiarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEspecialidad (?, ?)}");
            Especialidades registro = (Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1,registro.getCodigoEspecialidad());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
         e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/YohanRodas/image/Button2.png"));
                imgReporte.setImage(new Image("/org/YohanRodas/image/Button1.png"));
                tblEspecialidades.getSelectionModel().clearSelection();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        Especialidades registro = new Especialidades();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEspecialidad (?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaEspecialidades.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteEspecialidades.jasper", "Reporte de Especialidades", parametros);
    }

    public void desactivarControles(){
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles(){
        txtCodigoEspecialidad.clear();
        txtDescripcion.clear();
        tblEspecialidades.getSelectionModel().clearSelection();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
