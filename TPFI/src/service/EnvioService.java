/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EnvioDAO;
import entities.Envio;

public class EnvioService extends GenericService<Envio> {

    private final EnvioDAO envioDAO;

    public EnvioService(EnvioDAO envioDAO) {
        super(envioDAO);
        this.envioDAO = envioDAO;
    }

    // Métodos específicos para Envio (si en el futuro necesitás)
    // Ejemplo:
    // public List<Envio> getByEstado(Envio.Estado estado) throws Exception {
    //     return envioDAO.getByEstado(estado);
    // }
}


