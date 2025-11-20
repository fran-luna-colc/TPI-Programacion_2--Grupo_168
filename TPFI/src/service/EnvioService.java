package service;

import dao.EnvioDao;
import entities.Envio;

import java.util.List;
import java.util.Objects;

public class EnvioService implements GenericService<Envio> {

    private final EnvioDao envioDao;

    public EnvioService(EnvioDao envioDao) {
        this.envioDao = Objects.requireNonNull(envioDao);
    }

    @Override
    public void insertar(Envio envio) throws Exception {
        validarEnvio(envio);
        envioDao.insertar(envio);
    }

    @Override
    public Envio getById(int id) throws Exception {
        return envioDao.getById(id);
    }

    @Override
    public List<Envio> getAll() throws Exception {
        return envioDao.getAll();
    }

    @Override
    public void actualizar(Envio envio) throws Exception {
        validarEnvio(envio);
        envioDao.actualizar(envio);
    }

    @Override
    public void eliminar(int id) throws Exception {
        envioDao.eliminar(id);
    }

    private void validarEnvio(Envio envio) throws Exception {
        if (envio == null) throw new Exception("El envío no puede ser nulo.");
        if (envio.getTracking() == null || envio.getTracking().isBlank()) throw new Exception("Tracking obligatorio.");
        if (envio.getTracking().length() > 40) throw new Exception("El tracking supera el largo máximo.");
        if (envio.getEmpresa() == null) throw new Exception("Debe elegir una empresa de envío.");
        if (envio.getTipo() == null) throw new Exception("Debe elegir un tipo de envío.");
        if (envio.getCosto() < 0) throw new Exception("El costo debe ser >= 0.");
        if (envio.getFechaDespacho() == null || envio.getFechaEstimada() == null) throw new Exception("Fechas obligatorias.");
        if (envio.getEstado() == null) throw new Exception("Estado obligatorio.");
    }
}

