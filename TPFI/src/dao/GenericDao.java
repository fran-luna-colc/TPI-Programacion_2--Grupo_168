package dao;

import java.util.List;

public abstract class GenericDao<T> {

    public abstract T getById(int id) throws Exception;
    public abstract List<T> getAll() throws Exception;
    public abstract void insertar(T entity) throws Exception;
    public abstract void actualizar(T entity) throws Exception;
    public abstract void eliminar(int id) throws Exception;
}
