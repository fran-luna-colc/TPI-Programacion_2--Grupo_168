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
public class Pedidos extends Base {
    
    private String numero;
    private java.time.LocalDate fecha;
    private String clienteNombre;
    private double total;
    private EstadoPedidoEnum estado;
    private Envios envio;

    
    // Constructores
    public Pedidos(String numero, LocalDate fecha, String clienteNombre, double total, EstadoPedidoEnum estado,long id) {
        super(id, false);
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
    }

    public Pedidos() {
        super(); 
    }

    // Getters
    
    public String getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public double getTotal() {
        return total;
    }

    public EstadoPedidoEnum getEstado() {
        return estado;
    }

    public Envios getEnvio() {
        return envio;
    }

    // setters
    
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setEstado(EstadoPedidoEnum estado) {
        this.estado = estado;
    }

    public void setEnvio(Envios envio) {
        this.envio = envio;
    }

    // toString

    @Override
    public String toString() {
        return "Pedidos{" + "numero=" + numero + ", fecha=" + fecha + ", clienteNombre=" + clienteNombre + ", total=" + total + ", estado=" + estado + ", envio=" + envio + '}';
    }
    
    
}
