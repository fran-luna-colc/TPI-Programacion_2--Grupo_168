
package Main;


import dao.EnvioDAO;
import dao.PedidoDAO;
import entities.Envio;
import entities.Pedido;
import service.EnvioService;
import service.PedidoService;
import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {

    private final Scanner scanner = new Scanner(System.in);

    private final PedidoService pedidoService;
    private final EnvioService envioService;

    public MenuHandler() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        PedidoDAO pedidoDAO = new PedidoDAO(conn);
        EnvioDAO envioDAO = new EnvioDAO(conn);

        this.pedidoService = new PedidoService(pedidoDAO);
        this.envioService = new EnvioService(envioDAO);
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n======================");
            System.out.println("      MENÚ PRINCIPAL");
            System.out.println("======================");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar pedido por ID");
            System.out.println("4. Eliminar pedido");
            System.out.println("5 Buscar pedido por ID" );
            System.out.println("----------------------");
            System.out.println("6 Listar envíos");
            System.out.println("7 Crear envío");
            System.out.println("8 Actualizar envío por ID");
            System.out.println("9 Eliminar envío");
            System.out.println("10 Buscar envío por ID" );
            System.out.println("----------------------");
            System.out.println("0. Salir");
            System.out.print("Ingresa tu opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> listarPedidos();
                case 2 -> crearPedido();
                case 3 -> actualizarPedido();
                case 4 -> eliminarPedido();
                case 5 -> buscarPedidoPorId();

                case 6 -> listarEnvios();
                case 7 -> crearEnvio();
                case 8 -> actualizarEnvio();
                case 9 -> eliminarEnvio();
                case 10 -> buscarEnvioPorId();

                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }

                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ============================
    //        PEDIDOS
    // ============================

    private void listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.getAll();
            pedidos.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }

    private void crearPedido() {
        try {
            Pedido p = new Pedido();
            System.out.print("Número: ");
            p.setNumero(scanner.nextLine());
            System.out.print("Fecha (YYYY-MM-DD): ");
            p.setFecha(LocalDate.parse(scanner.nextLine()));
            System.out.print("Cliente: ");
            p.setClienteNombre(scanner.nextLine());
            System.out.print("Total: ");
            p.setTotal(Double.parseDouble(scanner.nextLine()));
            p.setEstado(Pedido.Estado.NUEVO);

            System.out.println("Seleccione ID de Envío existente:");
            listarEnvios();
            System.out.print("ID: ");
            int envioId = Integer.parseInt(scanner.nextLine());
            Envio envio = envioService.getById(envioId);
            p.setEnvio(envio);

            pedidoService.insertar(p);
            System.out.println("Pedido creado.");
        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private void actualizarPedido() {
        try {
            System.out.print("ID a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Pedido p = pedidoService.getById(id);

            System.out.print("Nuevo total: ");
            p.setTotal(Double.parseDouble(scanner.nextLine()));

            System.out.println("Nuevo estado:");
            for (Pedido.Estado es : Pedido.Estado.values()) {
                System.out.println("- " + es);
            }
            p.setEstado(Pedido.Estado.valueOf(scanner.nextLine().toUpperCase()));

            pedidoService.actualizar(p);
            System.out.println("Pedido actualizado.");
        } catch (Exception e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        try {
            System.out.print("ID a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            pedidoService.eliminar(id);
            System.out.println("Pedido eliminado (soft-delete).");
        } catch (Exception e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
    
    private void buscarPedidoPorId() {
    try {
        System.out.print("Ingrese el ID del pedido: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Pedido p = pedidoService.getById(id);

        if (p == null) {
            System.out.println("No existe un pedido con ese ID.");
            return;
        }

        System.out.println("\n=== Pedido encontrado ===");
        System.out.println("ID: " + p.getId());
        System.out.println("Número: " + p.getNumero());
        System.out.println("Fecha: " + p.getFecha());
        System.out.println("Cliente: " + p.getClienteNombre());
        System.out.println("Total: " + p.getTotal());
        System.out.println("Estado: " + p.getEstado());
        System.out.println("Envío: " + (p.getEnvio() != null ? p.getEnvio().getTracking() : "Sin envío"));
        System.out.println("==========================\n");

    } catch (Exception e) {
        System.out.println("Error al buscar pedido: " + e.getMessage());
    }
}


    // ============================
    //         ENVIOS
    // ============================

    private void listarEnvios() {
        try {
            List<Envio> envios = envioService.getAll();
            envios.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error al listar envíos: " + e.getMessage());
        }
    }

    private void crearEnvio() {
        try {
            Envio e = new Envio();

            System.out.print("Tracking: ");
            e.setTracking(scanner.nextLine());

            System.out.println("Empresa (ANDREANI, OCA, CORREO_ARG): ");
            e.setEmpresa(Envio.Empresa.valueOf(scanner.nextLine().toUpperCase()));

            System.out.println("Tipo (ESTANDAR, EXPRES): ");
            e.setTipo(Envio.Tipo.valueOf(scanner.nextLine().toUpperCase()));

            System.out.print("Costo: ");
            e.setCosto(Double.parseDouble(scanner.nextLine()));

            System.out.print("Fecha despacho (YYYY-MM-DD) : ");
            e.setFechaDespacho(LocalDate.parse(scanner.nextLine()));

            System.out.print("Fecha estimada(YYYY-MM-DD) : ");
            e.setFechaEstimada(LocalDate.parse(scanner.nextLine()));

            e.setEstado(Envio.Estado.EN_PREPARACION);

            envioService.insertar(e);
            System.out.println("Envío creado.");

        } catch (Exception ex) {
            System.out.println("Error al crear envío: " + ex.getMessage());
        }
    }

    private void actualizarEnvio() {
        try {
            System.out.print("ID a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Envio e = envioService.getById(id);

            System.out.print("Nuevo costo: ");
            e.setCosto(Double.parseDouble(scanner.nextLine()));

            System.out.println("Nuevo estado (EN_PREPARACION, EN_TRANSITO, ENTREGADO):");
            e.setEstado(Envio.Estado.valueOf(scanner.nextLine().toUpperCase()));

            envioService.actualizar(e);
            System.out.println("Envio actualizado.");
        } catch (Exception ex) {
            System.out.println("Error al actualizar envio: " + ex.getMessage());
        }
    }

    private void eliminarEnvio() {
        try {
            System.out.print("ID a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            envioService.eliminar(id);
            System.out.println("Envio eliminado (soft-delete).");
        } catch (Exception ex) {
            System.out.println("Error al eliminar envio: " + ex.getMessage());
        }
    }
    
    private void buscarEnvioPorId() {
    try {
        System.out.print("Ingrese el ID del envío: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Envio e = envioService.getById(id);

        if (e == null) {
            System.out.println("No existe un envío con ese ID.");
            return;
        }

        System.out.println("\n=== Envío encontrado ===");
        System.out.println("ID: " + e.getId());
        System.out.println("Tracking: " + e.getTracking());
        System.out.println("Empresa: " + e.getEmpresa());
        System.out.println("Tipo: " + e.getTipo());
        System.out.println("Costo: $" + e.getCosto());
        System.out.println("Fecha despacho: " + e.getFechaDespacho());
        System.out.println("Fecha estimada: " + e.getFechaEstimada());
        System.out.println("Estado: " + e.getEstado());
        System.out.println("==========================\n");

    } catch (Exception ex) {
        System.out.println("Error al buscar envío: " + ex.getMessage());
    }
}


}
