package service;

import dao.PedidoDao;
import dao.EnvioDao;
import entities.Pedido;
import entities.Envio;
import config.TransactionManager;

import java.sql.Connection;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

public class PedidoService implements GenericService<Pedido> {

    private final PedidoDao pedidoDao;
    private final EnvioDao envioDao;
    private final Connection conn;

    public PedidoService(PedidoDao pedidoDao, EnvioDao envioDao, Connection conn) {
        this.pedidoDao = Objects.requireNonNull(pedidoDao);
        this.envioDao = Objects.requireNonNull(envioDao);
        this.conn = Objects.requireNonNull(conn);
    }

    @Override
    public void insertar(Pedido pedido) throws Exception {
        validarPedido(pedido);
        if (pedido.getEnvio() == null) throw new Exception("Debe cargar los datos del envío asociado.");

        try (TransactionManager tm = new TransactionManager(conn)) {
            tm.startTransaction();
            // insertar envio primero
            envioDao.insertar(pedido.getEnvio());
            // ahora el envio tiene id
            pedidoDao.insertar(pedido);
            tm.commit();
        }
    }

    @Override
    public Pedido getById(int id) throws Exception {
        return pedidoDao.getById(id);
    }

    @Override
    public List<Pedido> getAll() throws Exception {
        return pedidoDao.getAll();
    }

    @Override
    public void actualizar(Pedido pedido) throws Exception {
        validarPedido(pedido);
        try (TransactionManager tm = new TransactionManager(conn)) {
            tm.startTransaction();
            if (pedido.getEnvio() != null) envioDao.actualizar(pedido.getEnvio());
            pedidoDao.actualizar(pedido);
            tm.commit();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        try (TransactionManager tm = new TransactionManager(conn)) {
            tm.startTransaction();
            Pedido p = pedidoDao.getById(id);
            if (p == null) throw new Exception("Pedido inexistente.");
            pedidoDao.eliminar(id);
            if (p.getEnvio() != null) envioDao.eliminar(p.getEnvio().getId());
            tm.commit();
        }
    }

    private void validarPedido(Pedido pedido) throws Exception {
        if (pedido == null) throw new Exception("Pedido nulo.");
        if (pedido.getNumero() == null || pedido.getNumero().isBlank()) throw new Exception("Numero obligatorio.");
        if (pedido.getNumero().length() > 20) throw new Exception("Numero demasiado largo.");
        if (pedido.getClienteNombre() == null || pedido.getClienteNombre().isBlank()) throw new Exception("Cliente obligatorio.");
        if (pedido.getClienteNombre().length() > 120) throw new Exception("Cliente demasiado largo.");
        if (pedido.getFecha() == null || pedido.getFecha().isAfter(LocalDate.now())) throw new Exception("Fecha invalida.");
        if (pedido.getTotal() <= 0) throw new Exception("Total debe ser > 0.");
        if (pedido.getEstado() == null) throw new Exception("Estado obligatorio.");
    }
}

