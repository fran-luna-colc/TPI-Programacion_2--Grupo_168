/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.GenericDAO;
import entities.Base;
import java.util.List;

public abstract class GenericService<T extends Base> {

    protected final GenericDAO<T> dao;

    protected GenericService(GenericDAO<T> dao) {
        this.dao = dao;
    }

    public T getById(int id) throws Exception {
        return dao.getById(id);
    }

    public void insertar(T entity) throws Exception {
        dao.insertar(entity);
    }

    public void actualizar(T entity) throws Exception {
        dao.actualizar(entity);
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }

    public List<T> getAll() throws Exception {
        return dao.getAll();
    }
}

