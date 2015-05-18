package controlador;
import vista.interfaz;
/**
*  @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class Main {
    
    public static void main(String[] args) {
        //ejecuta el controlador y este la vista
        new controlador( new interfaz() ).iniciar() ;
    }

}
