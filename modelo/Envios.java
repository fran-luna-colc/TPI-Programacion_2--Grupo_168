/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author franc
 */
public class Envios extends Base {
    
    private String tracking;
    private EmpresaEnviosEnum empresa;
    private TipoEnviosEnum tipo;
    private double costo;
    private java.time.LocalDate fechaDespacho;
    private java.time.LocalDate fechaEstimada;
    private EstadoEnvioEnum estado;

    
    //Constructores
    
    public Envios(String tracking, EmpresaEnviosEnum empresa, TipoEnviosEnum tipo, double costo, LocalDate fechaDespacho, LocalDate fechaEstimada, EstadoEnvioEnum estado, long id) {
        super(id, false);
        this.tracking = tracking;
        this.empresa = empresa;
        this.tipo = tipo;
        this.costo = costo;
        this.fechaDespacho = fechaDespacho;
        this.fechaEstimada = fechaEstimada;
        this.estado = estado;
    }

    public Envios() {
        super();
    }
    
    //Getters

    public String getTracking() {
        return tracking;
    }

    public EmpresaEnviosEnum getEmpresa() {
        return empresa;
    }

    public TipoEnviosEnum getTipo() {
        return tipo;
    }

    public double getCosto() {
        return costo;
    }

    public LocalDate getFechaDespacho() {
        return fechaDespacho;
    }

    public LocalDate getFechaEstimada() {
        return fechaEstimada;
    }

    public EstadoEnvioEnum getEstado() {
        return estado;
    }
     
    //Setters

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public void setEmpresa(EmpresaEnviosEnum empresa) {
        this.empresa = empresa;
    }

    public void setTipo(TipoEnviosEnum tipo) {
        this.tipo = tipo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setFechaDespacho(LocalDate fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public void setFechaEstimada(LocalDate fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public void setEstado(EstadoEnvioEnum estado) {
        this.estado = estado;
    }
    
    //toString

    @Override
    public String toString() {
        return "Envios{" + "tracking=" + tracking + ", empresa=" + empresa + ", tipo=" + tipo + ", costo=" + costo + ", fechaDespacho=" + fechaDespacho + ", fechaEstimada=" + fechaEstimada + ", estado=" + estado + '}';
    }

    
}
