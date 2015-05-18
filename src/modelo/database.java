package modelo;
import java.sql.*;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class database {
 /* DATOS PARA LA CONEXION */
  /** base de datos por defecto es test*/
  private String db = "dam28_pruebas";
  /** usuario */
  private String user = "dam28";
  /** contraseña de MySql*/
  private String password = "salesianas";
  /** Cadena de conexion */
  private String url = "jdbc:mysql://192.168.48.2:3306/"+db;
  /** variable para trabajar con la conexion a la base de datos */
  private Connection conn = null;

   /** Constructor de clase */
   public database(){
        this.url = "jdbc:mysql://192.168.48.2:3306/"+this.db;
       try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection( this.url, this.user , this.password );         
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
   }


   public Connection getConexion()
   {
    return this.conn;
   }

}
