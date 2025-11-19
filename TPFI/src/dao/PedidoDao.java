package dao;

import entities.Pedido;
import entities.Envio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PedidoDao
 *
 * DAO encargado de realizar todas las operaciones CRUD sobre la entidad Pedido.
 * Este DAO trabaja con una conexión externa (inyectada) para permitir que
 * PedidoService controle las transacciones que involucran Pedido + Envio.
 */

public class PedidoDao extends GenericDao<Pedido> {

    private final Connection conn;
    private final EnvioDao envioDAO;

    public PedidoDao(Connection conn) {
        this.conn = conn;
        this.envioDAO = new EnvioDao(conn);
    }

     // Obtiene un pedido por ID si no está marcado como eliminado.
  
    @Override
    public Pedido getById(int id) throws Exception {
        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) return mapPedido(rs);
            }
        }
        return null;
    }

  
     // Obtiene todos los pedidos no eliminados, ordenados por fecha descendente.

    @Override
    public List<Pedido> getAll() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false ORDER BY fecha DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre todas las filas y las convierte en objetos Pedido
            while (rs.next()) lista.add(mapPedido(rs));
        }
        return lista;
    }

    
     // Inserta un nuevo Pedido.
    
    @Override
    public void insertar(Pedido p) throws Exception {
        String sql = "INSERT INTO pedido (eliminado, numero, fecha, clienteNombre, total, estado, envio_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, p.isEliminado());
            ps.setString(2, p.getNumero());

            if (p.getFecha() != null) ps.setDate(3, Date.valueOf(p.getFecha()));
            else ps.setNull(3, Types.DATE);

            ps.setString(4, p.getClienteNombre());
            ps.setBigDecimal(5, java.math.BigDecimal.valueOf(p.getTotal()));
            ps.setString(6, p.getEstado().name());

            if (p.getEnvio() != null) ps.setInt(7, p.getEnvio().getId());
            else ps.setNull(7, Types.INTEGER);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getInt(1));
            }
        }
    }

  
    // Actualiza un Pedido existente.
    
    @Override
    public void actualizar(Pedido p) throws Exception {
        String sql = "UPDATE pedido SET numero=?, fecha=?, clienteNombre=?, total=?, estado=?, envio_id=? WHERE id=? AND eliminado = false";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNumero());

            if (p.getFecha() != null) ps.setDate(2, Date.valueOf(p.getFecha()));
            else ps.setNull(2, Types.DATE);

            ps.setString(3, p.getClienteNombre());
            ps.setBigDecimal(4, java.math.BigDecimal.valueOf(p.getTotal()));
            ps.setString(5, p.getEstado().name());

            // Validar existencia del envío
            if (p.getEnvio() != null) ps.setInt(6, p.getEnvio().getId());
            else ps.setNull(6, Types.INTEGER);

            ps.setInt(7, p.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Realiza una baja lógica del Pedido.
     * El Envío asociado NO se elimina desde aquí: eso se maneja en PedidoService.
     */
    @Override
    public void eliminar(int id) throws Exception {
        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

         //Mapea una fila del ResultSet a un objeto Pedido.
  
    private Pedido mapPedido(ResultSet rs) throws Exception {

        // Recuperar ID del envío asociado
        int envioId = rs.getInt("envio_id");
        Envio envio = null;

        // rs.wasNull() evita errores cuando envio_id es NULL
        if (!rs.wasNull() && envioId > 0) envio = envioDAO.getById(envioId);

        return new Pedido(
                rs.getInt("id"),
                rs.getBoolean("eliminado"),
                rs.getString("numero"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("clienteNombre"),
                rs.getBigDecimal("total").doubleValue(),
                Pedido.Estado.valueOf(rs.getString("estado")),
                envio
        );
    }
}
