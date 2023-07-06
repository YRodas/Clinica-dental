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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.YohanRodas.bean.DetalleReceta;
import org.YohanRodas.bean.Medicamento;
import org.YohanRodas.bean.Receta;
import org.YohanRodas.db.Conexion;
import org.YohanRodas.report.GenerarReporte;
import org.YohanRodas.system.Principal;

public class DetalleRecetaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<DetalleReceta> listaDetalleReceta;
    private ObservableList<Receta> listaReceta;
    private ObservableList<Medicamento> listaMedicamento;
    @FXML private TextField txtCodDetRec;
    @FXML private TextField txtDos;
    @FXML private ComboBox cmbCodRec;
    @FXML private ComboBox cmbCodMed;
    @FXML private TableView tblDetalleReceta;
    @FXML private TableColumn colCodDetRec;
    @FXML private TableColumn colDos;
    @FXML private TableColumn colCodRec;
    @FXML private TableColumn colCodMed;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodRec.setItems(getRecetas());
        cmbCodMed.setItems(getMedicamento());
    }
    
    public void cargarDatos(){
        tblDetalleReceta.setItems(getDetalleReceta());
        colCodDetRec.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoDetalleReceta"));
        colDos.setCellValueFactory(new PropertyValueFactory<DetalleReceta, String>("dosis"));
        colCodRec.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoReceta"));
        colCodMed.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoMedicamento"));
    }
    
    public void seleccionarElemento(){
        try{txtCodDetRec.setText(String.valueOf(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoDetalleReceta()));
        txtDos.setText(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getDosis());
        cmbCodRec.getSelectionModel().select(buscarReceta(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoReceta()));
        cmbCodMed.getSelectionModel().select(buscarMedicamento(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoMedicamento()));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }
    
    public Receta buscarReceta(int codigoReceta){
        Receta resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarReceta(?)}");
            procedimiento.setInt(1, codigoReceta);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Receta(registro.getInt("codigoReceta"),
                                            registro.getDate("fechaReceta"),
                                            registro.getInt("numeroColegiado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Medicamento buscarMedicamento(int codigoMedicamento){
        Medicamento resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarMedicamento(?)}");
            procedimiento.setInt(1, codigoMedicamento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Medicamento(registro.getInt("codigoMedicamento"),
                                            registro.getString("nombreMedicamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Receta> getRecetas(){
        ArrayList<Receta> lista = new ArrayList<Receta>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarRecetas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
            lista.add(new Receta(resultado.getInt("codigoReceta"),
                                resultado.getDate("fechaReceta"),
                                resultado.getInt("numeroColegiado")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaReceta = FXCollections.observableArrayList(lista);
    }    
    
    public ObservableList<Medicamento> getMedicamento(){
        ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarMedicamentos()}");
        ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Medicamento(resultado.getInt("codigoMedicamento"),
                                resultado.getString("nombreMedicamento")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaMedicamento = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<DetalleReceta> getDetalleReceta(){
        ArrayList<DetalleReceta> lista = new ArrayList<DetalleReceta>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleReceta()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
            lista.add(new DetalleReceta(resultado.getInt("codigoDetalleReceta"),
                                resultado.getString("dosis"),
                                resultado.getInt("codigoReceta"),
                                resultado.getInt("codigoMedicamento")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDetalleReceta = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
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
                limpiarControles();
                desactivarControles();
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
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/YohanRodas/image/Button4.png"));
                imgEliminar.setImage(new Image("/org/YohanRodas/image/Button3.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblDetalleReceta.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Detalle de Receta",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleReceta(?)}");
                            procedimiento.setInt(1, ((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoDetalleReceta());
                            procedimiento.execute();
                            listaDetalleReceta.remove(tblDetalleReceta.getSelectionModel().getSelectedIndex());
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
                if (tblDetalleReceta.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/YohanRodas/image/Cambiar.png"));
                    imgReporte.setImage(new Image("/org/YohanRodas/image/Cancelar.png"));
                    activarControles();
                    txtCodDetRec.setEditable(false);
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
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        DetalleReceta registro = new DetalleReceta();
        registro.setDosis(txtDos.getText());
        String[] datosRecdeta = ((String)cmbCodRec.getSelectionModel().getSelectedItem()).trim().split("\\|");
        String noReceta = datosRecdeta[0].trim();
        String[] datosMedicamento = ((String)cmbCodMed.getSelectionModel().getSelectedItem()).trim().split("\\|");
        String noMedicamento = datosMedicamento[0].trim();
        registro.setCodigoReceta(Integer.valueOf(noReceta));
        registro.setCodigoMedicamento(Integer.valueOf(noMedicamento));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleReceta(?, ?, ?)}");
            procedimiento.setString(1, registro.getDosis());
            procedimiento.setInt(2, registro.getCodigoReceta());
            procedimiento.setInt(3, registro.getCodigoMedicamento());
            procedimiento.execute();
            listaDetalleReceta.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleReceta(?, ?, ?, ?)}");
            DetalleReceta registro = (DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem();
            registro.setCodigoReceta(Integer.parseInt(txtCodDetRec.getText()));
            
            String[] datosRecdeta = ((String)cmbCodRec.getSelectionModel().getSelectedItem()).trim().split("\\|");
            String noReceta = datosRecdeta[0].trim();
            
            String[] datosMedicamento = ((String)cmbCodMed.getSelectionModel().getSelectedItem()).trim().split("\\|");
            String noMedicamento = datosMedicamento[0].trim();
            
            registro.setCodigoReceta(Integer.valueOf(noReceta));
            
            registro.setDosis(txtDos.getText());
            
            registro.setCodigoMedicamento(Integer.valueOf(noMedicamento));
            
            procedimiento.setInt(1, registro.getCodigoDetalleReceta());
            procedimiento.setString(2, registro.getDosis());
            procedimiento.setInt(3, registro.getCodigoReceta());
            procedimiento.setInt(4, registro.getCodigoMedicamento());
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
                limpiarControles();
                desactivarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/YohanRodas/image/Button2.png"));
                imgReporte.setImage(new Image("/org/YohanRodas/image/Button1.png"));
                tblDetalleReceta.getSelectionModel().clearSelection();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteDetalleRecetas.jasper", "Reporte sobre los detalles de las recetas", parametros);
    }
    
    public void desactivarControles(){
        txtDos.setEditable(false);
        cmbCodRec.setEditable(false);
        cmbCodMed.setEditable(false);
    }
    
    public void activarControles(){
        txtDos.setEditable(true);
        cmbCodRec.setEditable(true);
        cmbCodMed.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodDetRec.clear();
        txtDos.clear();
        cmbCodRec.getSelectionModel().clearSelection();
        cmbCodMed.getSelectionModel().clearSelection();
        tblDetalleReceta.getSelectionModel().clearSelection();
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