package org.YohanRodas.bean;

public class Especialidades {
    private int codigoEspecialidad;
    private String descripcion;

    public Especialidades(){
        
    }

    public Especialidades(int codigoEspecialidad, String descripcion) {
        this.codigoEspecialidad = codigoEspecialidad;
        this.descripcion = descripcion;
    }

    public int getCodigoEspecialidad() {
        return codigoEspecialidad;
    }

    public void setCodigoEspecialidad(int codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoEspecialidad()+" | " + getDescripcion();
    }
}
