/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;


import config.DatabaseConnection;
import dao.PedidoDAO;
import dao.EnvioDAO;
import java.sql.SQLException;
import service.PedidoService;
import service.EnvioService;


public class Main {

    public static void main(String[] args) throws SQLException {

        PedidoDAO pedidoDAO = new PedidoDAO(DatabaseConnection.getConnection());
        EnvioDAO envioDAO = new EnvioDAO(DatabaseConnection.getConnection());

        PedidoService pedidoService = new PedidoService(pedidoDAO);
        EnvioService envioService = new EnvioService(envioDAO);

        MenuHandler handler = new MenuHandler();

        AppMenu app = new AppMenu(handler);
        app.start();
    }
}

