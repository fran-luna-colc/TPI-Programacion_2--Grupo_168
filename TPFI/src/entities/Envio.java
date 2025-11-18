/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author franc
 */

import java.time.LocalDate;
import java.util.Objects;

public class Envio extends Base {

    public enum Empresa { ANDREANI, OCA, CORREO_ARG }
    public enum Tipo { ESTANDAR, EXPRES }
    public enum Estado { EN_PREPARACION, EN_TRANSITO, ENTREGADO }

    private String tracking; // UNIQUE, máx 40
    private Empresa empresa;
    private Tipo tipo;
    private double costo; // (10,2)
    private LocalDate fechaDespacho;
    private LocalDate fechaEstimada;
    private Estado estado;

    public Envio() {
        super();
    }

    public Envio(int id, boolean eliminado, String tracking, Empresa empresa, Tipo tipo,
                 double costo, LocalDate fechaDespacho, LocalDate fechaEstimada, Estado estado) {
        super(id, eliminado);
        this.tracking = tracking;
        this.empresa = empresa;
        this.tipo = tipo;
        this.costo = costo;
        this.fechaDespacho = fechaDespacho;
        this.fechaEstimada = fechaEstimada;
        this.estado = estado;
    }

    // Getters & setters
    public String getTracking() { return tracking; }
    public void setTracking(String tracking) { this.tracking = tracking; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public LocalDate getFechaDespacho() { return fechaDespacho; }
    public void setFechaDespacho(LocalDate fechaDespacho) { this.fechaDespacho = fechaDespacho; }

    public LocalDate getFechaEstimada() { return fechaEstimada; }
    public void setFechaEstimada(LocalDate fechaEstimada) { this.fechaEstimada = fechaEstimada; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Envio{" + "id=" + getId() + ", tracking=" + tracking + ", empresa=" + empresa + ", tipo=" + tipo + ", costo=" + costo + ", fechaDespacho=" + fechaDespacho + ", fechaEstimada=" + fechaEstimada + ", estado=" + estado + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.tracking);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Envio other = (Envio) obj;
        return Objects.equals(this.tracking, other.tracking);
    }
    
}