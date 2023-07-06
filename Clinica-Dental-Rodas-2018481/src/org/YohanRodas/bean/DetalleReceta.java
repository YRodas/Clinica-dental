package org.YohanRodas.bean;

public class DetalleReceta {
    private int codigoDetalleReceta;
    private String dosis;
    private int codigoReceta;
    private int codigoMedicamento;
    
    public DetalleReceta(){
    
    }

    public DetalleReceta(int codigoDetalleReceta, String dosis, int codigoReceta, int codigoMedicamento) {
        this.codigoDetalleReceta = codigoDetalleReceta;
        this.dosis = dosis;
        this.codigoReceta = codigoReceta;
        this.codigoMedicamento = codigoMedicamento;
    }

    public int getCodigoDetalleReceta() {
        return codigoDetalleReceta;
    }

    public void setCodigoDetalleReceta(int codigoDetalleReceta) {
        this.codigoDetalleReceta = codigoDetalleReceta;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getCodigoReceta() {
        return codigoReceta;
    }

    public void setCodigoReceta(int codigoReceta) {
        this.codigoReceta = codigoReceta;
    }

    public int getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(int codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }
}
