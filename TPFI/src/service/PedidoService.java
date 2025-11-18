/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.PedidoDAO;
import entities.Pedido;

public class PedidoService extends GenericService<Pedido> {

    private final PedidoDAO pedidoDAO;

    public PedidoService(PedidoDAO pedidoDAO) {
        super(pedidoDAO);
        this.pedidoDAO = pedidoDAO;
    }

    // Métodos adicionales específicos de Pedido (si los necesitás)
    // Por ejemplo:
    // public List<Pedido> getByEstado(Pedido.Estado estado) throws Exception {
    //     return pedidoDAO.getByEstado(estado);
    // }

}

