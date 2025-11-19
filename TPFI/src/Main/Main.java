package Main;

import config.DatabaseConnection;
import dao.PedidoDao;
import dao.EnvioDao;
import service.PedidoService;
import service.EnvioService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Abrimos UNA conexión que se comparte entre DAOs y Services.
        try (Connection conn = DatabaseConnection.getConnection()) {

            PedidoDao pedidoDao = new PedidoDao(conn);
            EnvioDao envioDao = new EnvioDao(conn);

            PedidoService pedidoService = new PedidoService(pedidoDao, envioDao, conn);
            EnvioService envioService = new EnvioService(envioDao);

            MenuHandler handler = new MenuHandler(pedidoService, envioService);
            AppMenu app = new AppMenu(handler);
            app.start();

        } catch (Exception ex) {
            System.err.println("Error en la aplicación: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


