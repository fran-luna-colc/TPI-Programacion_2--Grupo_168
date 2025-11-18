
package Main;

public class AppMenu {

    private final MenuHandler handler;

    public AppMenu(MenuHandler handler) {
        this.handler = handler;
    }

    public void start() {
        handler.mostrarMenu();
    }
}
