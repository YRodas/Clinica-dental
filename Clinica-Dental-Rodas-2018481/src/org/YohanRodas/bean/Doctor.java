package org.YohanRodas.bean;

public class Doctor {
    private int numeroColegiado;
    private String nombresDoctor;
    private String apellidosDoctor;
    private String telefonoContacto;
    private int codigoEspecialidad;
    
    public Doctor(){
        
    }

    public Doctor(int numeroColegiado, String nombreDoctor, String apellidosDoctor, String telefonoContacto, int codigoEspecialidad) {
        this.numeroColegiado = numeroColegiado;
        this.nombresDoctor = nombreDoctor;
        this.apellidosDoctor = apellidosDoctor;
        this.telefonoContacto = telefonoContacto;
        this.codigoEspecialidad = codigoEspecialidad;
    }

    public int getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(int numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public String getNombresDoctor() {
        return nombresDoctor;
    }

    public void setNombresDoctor(String nombresDoctor) {
        this.nombresDoctor = nombresDoctor;
    }

    public String getApellidosDoctor() {
        return apellidosDoctor;
    }

    public void setApellidosDoctor(String apellidosDoctor) {
        this.apellidosDoctor = apellidosDoctor;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getCodigoEspecialidad() {
        return codigoEspecialidad;
    }

    public void setCodigoEspecialidad(int codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }
    
        @Override
    public String toString() {
        return getNumeroColegiado() + " | " + getNombresDoctor() + " | " + getApellidosDoctor() + " | " + getTelefonoContacto() + " | " + getCodigoEspecialidad();
    }
}    