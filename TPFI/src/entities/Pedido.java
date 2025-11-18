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

public class Pedido extends Base {

    public enum Estado { NUEVO, FACTURADO, ENVIADO }

    private String numero;            // NOT NULL, UNIQUE, máx 20
    private LocalDate fecha;          // NOT NULL
    private String clienteNombre;     // NOT NULL, máx 120
    private double total;             // NOT NULL, (12,2)
    private Estado estado;            // NOT NULL
    private Envio envio;              // Relación 1 → 1

    public Pedido() {
        super();
    }

    public Pedido(int id, boolean eliminado, String numero, LocalDate fecha,
                  String clienteNombre, double total, Estado estado, Envio envio) {
        super(id, eliminado);
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
        this.envio = envio;
    }

    // Getters & setters
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + getId() + ", numero=" + numero + ", fecha=" + fecha + ", clienteNombre=" + clienteNombre + ", total=" + total + ", estado=" + estado + ", envio=" + envio + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.numero);
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
        final Pedido other = (Pedido) obj;
        return Objects.equals(this.numero, other.numero);
    }
    
    
}

