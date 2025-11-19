package Main;

import service.PedidoService;
import service.EnvioService;
import entities.Pedido;
import entities.Envio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {

    private final Scanner scanner = new Scanner(System.in);
    private final PedidoService pedidoService;
    private final EnvioService envioService;

    public MenuHandler(PedidoService pedidoService, EnvioService envioService) {
        this.pedidoService = pedidoService;
        this.envioService = envioService;
    }

    public void mostrarMenu() {
        while (true) {
            MenuDisplay.mostrarMenuPrincipal();
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            int opcion;
            try { opcion = Integer.parseInt(line); } catch (NumberFormatException e) { System.out.println("Entrada invalida."); continue; }

            try {
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
                    case 0 -> { System.out.println("Saliendo..."); return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // PEDIDOS
    private void listarPedidos() throws Exception {
        List<Pedido> pedidos = pedidoService.getAll();
        if (pedidos.isEmpty()) System.out.println("No hay pedidos.");
        else pedidos.forEach(System.out::println);
    }

    private void crearPedido() throws Exception {
        Pedido p = new Pedido();
        System.out.print("Número: ");
        p.setNumero(scanner.nextLine().trim());
        System.out.print("Fecha (YYYY-MM-DD): ");
        p.setFecha(LocalDate.parse(scanner.nextLine().trim()));
        System.out.print("Cliente: ");
        p.setClienteNombre(scanner.nextLine().trim());
        System.out.print("Total: ");
        p.setTotal(Double.parseDouble(scanner.nextLine().trim()));
        p.setEstado(Pedido.Estado.NUEVO);

        System.out.println("Seleccione ID de Envío existente:");
        listarEnvios();
        System.out.print("ID: ");
        int envioId = Integer.parseInt(scanner.nextLine().trim());
        Envio envio = envioService.getById(envioId);
        if (envio == null) throw new Exception("Envio no encontrado.");
        p.setEnvio(envio);

        pedidoService.insertar(p);
        System.out.println("Pedido creado con ID: " + p.getId());
    }

    private void actualizarPedido() throws Exception {
        System.out.print("ID a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Pedido p = pedidoService.getById(id);
        if (p == null) throw new Exception("Pedido no encontrado.");

        System.out.print("Nuevo total: ");
        p.setTotal(Double.parseDouble(scanner.nextLine().trim()));

        System.out.println("Nuevo estado:");
        for (Pedido.Estado es : Pedido.Estado.values()) System.out.println("- " + es);
        p.setEstado(Pedido.Estado.valueOf(scanner.nextLine().trim().toUpperCase()));

        pedidoService.actualizar(p);
        System.out.println("Pedido actualizado.");
    }

    private void eliminarPedido() throws Exception {
        System.out.print("ID a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        pedidoService.eliminar(id);
        System.out.println("Pedido eliminado (soft-delete).");
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

    // ENVIOS
    private void listarEnvios() throws Exception {
        List<Envio> envios = envioService.getAll();
        if (envios.isEmpty()) System.out.println("No hay envios.");
        else envios.forEach(System.out::println);
    }

    private void crearEnvio() throws Exception {
        Envio e = new Envio();
        System.out.print("Tracking: ");
        e.setTracking(scanner.nextLine().trim());
        System.out.println("Empresa (ANDREANI, OCA, CORREO_ARG): ");
        e.setEmpresa(Envio.Empresa.valueOf(scanner.nextLine().trim().toUpperCase()));
        System.out.println("Tipo (ESTANDAR, EXPRES): ");
        e.setTipo(Envio.Tipo.valueOf(scanner.nextLine().trim().toUpperCase()));
        System.out.print("Costo: ");
        e.setCosto(Double.parseDouble(scanner.nextLine().trim()));
        System.out.print("Fecha despacho (YYYY-MM-DD): ");
        e.setFechaDespacho(LocalDate.parse(scanner.nextLine().trim()));
        System.out.print("Fecha estimada (YYYY-MM-DD): ");
        e.setFechaEstimada(LocalDate.parse(scanner.nextLine().trim()));
        e.setEstado(Envio.Estado.EN_PREPARACION);

        envioService.insertar(e);
        System.out.println("Envío creado con ID: " + e.getId());
    }

    private void actualizarEnvio() throws Exception {
        System.out.print("ID a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Envio e = envioService.getById(id);
        if (e == null) throw new Exception("Envio no encontrado.");
        System.out.print("Nuevo costo: ");
        e.setCosto(Double.parseDouble(scanner.nextLine().trim()));
        System.out.println("Nuevo estado (EN_PREPARACION, EN_TRANSITO, ENTREGADO):");
        e.setEstado(Envio.Estado.valueOf(scanner.nextLine().trim().toUpperCase()));
        envioService.actualizar(e);
        System.out.println("Envio actualizado.");
    }

    private void eliminarEnvio() throws Exception {
        System.out.print("ID a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        envioService.eliminar(id);
        System.out.println("Envio eliminado (soft-delete).");
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

