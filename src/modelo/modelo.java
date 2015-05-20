package modelo;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class modelo extends database{

    /** Constructor de clase */
    public modelo (){}

    /** Obtiene registros de la tabla Alumno y los devuelve en un DefaultTableModel*/
    public DefaultTableModel gettabAlumnos()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"DNI_Alum","Nombre","Apellidos","Telefono","Curso_Matri"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "alumnos"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Alumnos");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][5];
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Alumnos");
         ResultSet res = pstm.executeQuery();
         int i=0;
         while(res.next()){
                data[i][0] = res.getString( "DNI_Alum" );
                data[i][1] = res.getString( "Nombre" );
                data[i][2] = res.getString( "Apellidos" );
                data[i][3] = res.getString( "Telefono" );
                data[i][4] = res.getString( "Curso_Matri" );
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }

    /** Registra un nuevo alumno */
    public boolean NuevoAlumno(String DNI, String Nombre , String Apellidos, String Telefono, String Curso_Matri)
    {
        if( valida_datos(DNI, Nombre, Apellidos, Telefono, Curso_Matri)  )
        {
            //Se arma la consulta
            String q=" INSERT INTO Alumnos ( DNI_Alum, Nombre , Apellidos, Telefono, Curso_Matri  ) "
                    + "VALUES ( '" + DNI + "','" + Nombre + "', '" + Apellidos + "', '" + Telefono + "', '" + Curso_Matri + "' ) ";
            //se ejecuta la consulta
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
        }
        else
         return false;
    }


    /** Elimina un registro dado su ID -> Llave primaria */
    public boolean EliminarAlumno( String DNI )
    {
         boolean res=false;
        //se arma la consulta
        String q = " DELETE FROM Alumnos WHERE  DNI_Alum='" + DNI + "' " ;
        //se ejecuta la consulta
         try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return res;
    }

    /** Metodo privado para validar datos */
    private boolean valida_datos(String DNI, String Nombre , String Apellidos, String Telefono, String Curso_Matri)
    {
        if( DNI.equals("        ") )
            return false;
        else if( Nombre.length() > 0 && Apellidos.length()>0 && Telefono.length() >0 && Curso_Matri.length()>0)
        {
            return true;
        }
        else  return false;
    }

}
