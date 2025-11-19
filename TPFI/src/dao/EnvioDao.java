package dao;

import entities.Envio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EnvioDao
 *
 * DAO encargado de gestionar el acceso a la base de datos para la entidad Envio.
 * Implementa todas las operaciones CRUD y utiliza la conexión provista desde la
 * capa Service.
 */

public class EnvioDao extends GenericDao<Envio> {

    private final Connection conn;

    public EnvioDao(Connection conn) {
        this.conn = conn;
    }

    // Obtiene un Envío por ID.
     
    @Override
    public Envio getById(int id) throws Exception {
        String sql = "SELECT * FROM envio WHERE id = ? AND eliminado = false";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                // Si existe, mapear la fila a un objeto Envio
                if (rs.next()) return mapRow(rs);
            }
        }
        return null; // si no se encuentra
    }

    
    //Obtiene la lista completa de envíos.
    
    @Override
    public List<Envio> getAll() throws Exception {
        List<Envio> lista = new ArrayList<>();
        String sql = "SELECT * FROM envio WHERE eliminado = false";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Mapea cada fila y agrega a la lista
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    
    //Inserta un nuevo Envío.
     
    @Override
    public void insertar(Envio envio) throws Exception {
        String sql = "INSERT INTO envio (eliminado, tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, envio.isEliminado());
            ps.setString(2, envio.getTracking());
            ps.setString(3, envio.getEmpresa().name());
            ps.setString(4, envio.getTipo().name());
            ps.setBigDecimal(5, java.math.BigDecimal.valueOf(envio.getCosto()));

            if (envio.getFechaDespacho() != null)
                ps.setDate(6, Date.valueOf(envio.getFechaDespacho()));
            else
                ps.setNull(6, Types.DATE);

            if (envio.getFechaEstimada() != null)
                ps.setDate(7, Date.valueOf(envio.getFechaEstimada()));
            else
                ps.setNull(7, Types.DATE);

            ps.setString(8, envio.getEstado().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) envio.setId(keys.getInt(1));
            }
        }
    }

    
    // Actualiza un Envío existente.
    
    @Override
    public void actualizar(Envio envio) throws Exception {
        String sql = "UPDATE envio SET eliminado=?, tracking=?, empresa=?, tipo=?, costo=?, fechaDespacho=?, fechaEstimada=?, estado=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, envio.isEliminado());
            ps.setString(2, envio.getTracking());
            ps.setString(3, envio.getEmpresa().name());
            ps.setString(4, envio.getTipo().name());
            ps.setBigDecimal(5, java.math.BigDecimal.valueOf(envio.getCosto()));

            if (envio.getFechaDespacho() != null)
                ps.setDate(6, Date.valueOf(envio.getFechaDespacho()));
            else
                ps.setNull(6, Types.DATE);

            if (envio.getFechaEstimada() != null)
                ps.setDate(7, Date.valueOf(envio.getFechaEstimada()));
            else
                ps.setNull(7, Types.DATE);

            ps.setString(8, envio.getEstado().name());
            ps.setInt(9, envio.getId());

            ps.executeUpdate();
        }
    }

    // Realiza una baja lógica del envío.
  
    @Override
    public void eliminar(int id) throws Exception {
        String sql = "UPDATE envio SET eliminado = true WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //Mapea una fila del ResultSet a un objeto Envio.
  
    private Envio mapRow(ResultSet rs) throws Exception {

        Envio e = new Envio();

        e.setId(rs.getInt("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setTracking(rs.getString("tracking"));

        e.setEmpresa(Envio.Empresa.valueOf(rs.getString("empresa")));
        e.setTipo(Envio.Tipo.valueOf(rs.getString("tipo")));

        e.setCosto(rs.getBigDecimal("costo").doubleValue());

        Date fd = rs.getDate("fechaDespacho");
        if (fd != null) e.setFechaDespacho(fd.toLocalDate());

        Date fe = rs.getDate("fechaEstimada");
        if (fe != null) e.setFechaEstimada(fe.toLocalDate());

        e.setEstado(Envio.Estado.valueOf(rs.getString("estado")));

        return e;
    }
}
