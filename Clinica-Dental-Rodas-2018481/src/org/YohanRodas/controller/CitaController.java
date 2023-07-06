package org.YohanRodas.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.YohanRodas.bean.Cita;
import org.YohanRodas.bean.Doctor;
import org.YohanRodas.bean.Paciente;
import org.YohanRodas.db.Conexion;
import org.YohanRodas.report.GenerarReporte;
import org.YohanRodas.system.Principal;

public class CitaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cita> listaCita;
    private ObservableList<Paciente> listaPaciente;
    private ObservableList<Doctor> listaDoctor;
    private DatePicker fCita;
    @FXML private TextField txtCodCita;
    @FXML private TextField txtTrata;
    @FXML private TextField txtDesCripAct;
    @FXML private TextField txthoraCita;
    @FXML private GridPane grpFechas;
    @FXML private ComboBox cmbCodPac;
    @FXML private ComboBox cmbNumCol;
    @FXML private TableView tblCitas;
    @FXML private TableColumn colCodCit;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colHora;
    @FXML private TableColumn colTratamiento;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colCodPac;
    @FXML private TableColumn colNumCol;
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
        cmbCodPac.setItems(getCodigoPaciente());
        cmbNumCol.setItems(getNumeroColegiado());
        fCita = new DatePicker(Locale.ENGLISH);
        fCita.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fCita.getCalendarView().todayButtonTextProperty().set("Today");
        fCita.getCalendarView().setShowWeeks(false);
        grpFechas.add(fCita, 1, 1);
        fCita.getStylesheets().add("/org/YohanRodas/resource/DatePicker.css");
    }

    public void cargarDatos(){
        tblCitas.setItems(getCitas());
        colCodCit.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoCita"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Cita, Date>("fechaCita"));
        colHora.setCellValueFactory(new PropertyValueFactory<Cita, String>("horaCita"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<Cita, String>("tratamiento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Cita, String>("descripcionCondicionActual"));
        colCodPac.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoPaciente"));
        colNumCol.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("numeroColegiado"));
    }

    public void seleccionarElemento(){
        try{txtCodCita.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoCita()));
        txtTrata.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getTratamiento()));
        txtDesCripAct.setText(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getDescripcionCondicionActual());
        txthoraCita.setText(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getHoraCita());
        cmbCodPac.getSelectionModel().select(buscarPaciente(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoPaciente()));
        cmbNumCol.getSelectionModel().select(buscarDoctor(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getNumeroColegiado()));
        fCita.selectedDateProperty().set(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getFechaCita());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }

    public Paciente buscarPaciente(int codigoPaciente){
        Paciente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPaciente(?)}");
            procedimiento.setInt(1, codigoPaciente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Paciente(registro.getInt("codigoPaciente"),
                                            registro.getString("nombresPacientes"),
                                            registro.getString("apellidosPaciente"),
                                            registro.getString("sexo"),
                                            registro.getDate("fechaNacimiento"),
                                            registro.getString("direccionPaciente"),
                                            registro.getString("telefonoPersonal"),
                                            registro.getDate("fechaPrimeraVisita"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public Doctor buscarDoctor(int numeroColegiado){
        Doctor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDoctor(?)}");
            procedimiento.setInt(1, numeroColegiado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Doctor(registro.getInt("numeroColegiado"),
                                            registro.getString("nombresDoctor"),
                                            registro.getString("apellidosDoctor"),
                                            registro.getString("telefonoContacto"),
                                            registro.getInt("codigoEspecialidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Doctor> getNumeroColegiado(){
        ArrayList<Doctor> lista = new ArrayList<Doctor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDoctores()}");
            ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Doctor(resultado.getInt("numeroColegiado"),
                                resultado.getString("nombresDoctor"),
                                resultado.getString("apellidosDoctor"),
                                resultado.getString("telefonoContacto"),
                                resultado.getInt("codigoEspecialidad")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDoctor = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Paciente> getCodigoPaciente(){
        ArrayList<Paciente> lista = new ArrayList<Paciente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPaciente()}");
            ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Paciente(resultado.getInt("codigoPaciente"),
                                resultado.getString("nombresPacientes"),
                                resultado.getString("apellidosPaciente"),
                                resultado.getString("sexo"),
                                resultado.getDate("fechaNacimiento"),
                                resultado.getString("DireccionPaciente"),
                                resultado.getString("telefonoPersonal"),
                                resultado.getDate("fechaPrimeraVisita")));
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPaciente = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Cita> getCitas(){
        ArrayList<Cita> lista = new ArrayList<Cita>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCitas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
            lista.add(new Cita(resultado.getInt("codigoCita"),
                                resultado.getDate("fechaCita"),
                                resultado.getString("horaCita"),
                                resultado.getString("tratamiento"),
                                resultado.getString("descripcionCondicionActual"),
                                resultado.getInt("codigoPaciente"),
                                resultado.getInt("numeroColegiado")));
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCita = FXCollections.observableArrayList(lista);
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
                if(tblCitas.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Cita",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCita(?)}");
                            procedimiento.setInt(1, ((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoCita());
                            procedimiento.execute();
                            listaCita.remove(tblCitas.getSelectionModel().getSelectedIndex());
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
                if (tblCitas.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/YohanRodas/image/Cambiar.png"));
                    imgReporte.setImage(new Image("/org/YohanRodas/image/Cancelar.png"));
                    activarControles();
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
                tblCitas.getSelectionModel().clearSelection();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar(){
        Cita registro = new Cita();
        String[] datosPaciente = ((String)cmbCodPac.getSelectionModel().getSelectedItem()).trim().split("\\|");
        String noPaciente = datosPaciente[0].trim();
        String[] datosDoctor = ((String)cmbNumCol.getSelectionModel().getSelectedItem()).trim().split("\\|");
        String noColegiado = datosDoctor[0].trim();
        registro.setFechaCita(fCita.getSelectedDate());
        registro.setHoraCita(txthoraCita.getText());
        registro.setDescripcionCondicionActual(txtDesCripAct.getText());
        registro.setTratamiento(txtTrata.getText());
        registro.setCodigoPaciente(Integer.valueOf(noPaciente));
        registro.setNumeroColegiado(Integer.valueOf(noColegiado));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCita(?, ?, ?, ?, ?, ?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setString(2, registro.getHoraCita());
            procedimiento.setString(3, registro.getTratamiento());
            procedimiento.setString(4, registro.getDescripcionCondicionActual());
            procedimiento.setInt(5, registro.getCodigoPaciente());
            procedimiento.setInt(6, registro.getNumeroColegiado());
            procedimiento.execute();
            listaCita.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCita (?, ?, ?, ?, ?, ?, ?)}");
            Cita registro = (Cita)tblCitas.getSelectionModel().getSelectedItem();
            String[] datosPaciente = ((String)cmbCodPac.getSelectionModel().getSelectedItem()).trim().split("\\|");
            String noPaciente = datosPaciente[0].trim();
            String[] datosDoctor = ((String)cmbNumCol.getSelectionModel().getSelectedItem()).trim().split("\\|");
            String noColegiado = datosDoctor[0].trim();
            registro.setFechaCita(fCita.getSelectedDate());
            registro.setHoraCita(txthoraCita.getText());
            registro.setDescripcionCondicionActual(txtDesCripAct.getText());
            registro.setTratamiento(txtTrata.getText());
            registro.setCodigoPaciente(Integer.valueOf(noPaciente));
            registro.setNumeroColegiado(Integer.valueOf(noColegiado));
            procedimiento.setInt(1, registro.getCodigoCita());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setString(3, registro.getHoraCita());
            procedimiento.setString(4, registro.getTratamiento());
            procedimiento.setString(5, registro.getDescripcionCondicionActual());
            procedimiento.setInt(6, registro.getCodigoPaciente());
            procedimiento.setInt(7, registro.getNumeroColegiado());
            procedimiento.execute();
        }catch(Exception e){
         e.printStackTrace();
        }
    }

    public void imprimirReporte(){
        Map parametros = new HashMap();
        GenerarReporte.mostrarReporte("ReporteCitas.jasper", "Reporte de Citas", parametros);
    }

    public void desactivarControles(){
        txtTrata.setEditable(false);
        txtDesCripAct.setEditable(false);
        txthoraCita.setEditable(false);
        cmbCodPac.setEditable(false);
        cmbNumCol.setEditable(false);
    }
    
    public void activarControles(){
        txtTrata.setEditable(true);
        txtDesCripAct.setEditable(true);
        txthoraCita.setEditable(true);
        cmbCodPac.setEditable(true);
        cmbNumCol.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodCita.clear();
        txtTrata.clear();
        txtDesCripAct.clear();
        txthoraCita.clear();
        cmbCodPac.getSelectionModel().clearSelection();
        cmbNumCol.getSelectionModel().clearSelection();
        tblCitas.getSelectionModel().clearSelection();
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