package controlador;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//se importa modelo e interfaz
import modelo.modelo;
import vista.interfaz;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class controlador implements ActionListener,MouseListener{

    /** instancia a nuestra interfaz de usuario*/
    interfaz vista ;
    /** instancia a nuestro modelo */
    modelo modelo = new modelo();

    /** Se declaran en un ENUM las acciones que se realizan desde la
     * interfaz de usuario VISTA y posterior ejecución desde el controlador
     */
    public enum AccionMVC
    {
        ver_Alum,
        aniadir_Alum,
        eliminar_Alum,
        modificar_Alum,
        ver_Doc,
        aniadir_Doc,
        eliminar_Doc,
        modificar_Doc,
        ver_Aula,
        aniadir_Aula,
        eliminar_Aula,
        modificar_Aula,
        ver_Curso,
        aniadir_Curso,
        eliminar_Curso,
        modificar_Curso,
    }

    /** Constrcutor de clase
     * @param vista Instancia de clase interfaz
     */
    public controlador( interfaz vista )
    {
        this.vista = vista;
    }

    /** Inicia el skin y las diferentes variables que se utilizan */
    public void iniciar()
    {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
            vista.panPrincipal.setVisible(true);
            vista.panAlum.setVisible(false);
            vista.panDoc.setVisible(false);
            vista.panAulas.setVisible(false);
            vista.panCursos.setVisible(false);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}

        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.btnVerAlum.setActionCommand( "ver_Alum" );
        this.vista.btnVerAlum.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.btnMatricularAlum.setActionCommand( "aniadir_Alum" );
        this.vista.btnMatricularAlum.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.btnEliminarAlum.setActionCommand( "eliminar_Alum" );
        this.vista.btnEliminarAlum.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vista.btnModAlum.setActionCommand( "modificar_Alum" );
        this.vista.btnModAlum.addActionListener(this);        

        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vista.tabAlumnos.addMouseListener(this);
        this.vista.tabAlumnos.setModel( new DefaultTableModel() );
    }

    //Eventos que suceden por el mouse
    public void mouseClicked(MouseEvent e) {
        if( e.getButton()== 1)//boton izquierdo
        {
             int fila = this.vista.tabAlumnos.rowAtPoint(e.getPoint());
             if (fila > -1){                
                this.vista.txtDNI_Alum.setText( String.valueOf( this.vista.tabAlumnos.getValueAt(fila, 0) ));
                this.vista.txtNombre_Alum.setText( String.valueOf( this.vista.tabAlumnos.getValueAt(fila, 1) ));
                this.vista.txtApellidos_Alum.setText( String.valueOf( this.vista.tabAlumnos.getValueAt(fila, 2) ));
                this.vista.txtTelf_Alum.setText( String.valueOf( this.vista.tabAlumnos.getValueAt(fila, 3) ));
                this.vista.txtCurso_Alum.setText( String.valueOf( this.vista.tabAlumnos.getValueAt(fila, 4) ));
             }
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) { }
 
    //Control de eventos de los controles que tienen definido un "ActionCommand"
    public void actionPerformed(ActionEvent e) {

    switch ( AccionMVC.valueOf( e.getActionCommand() ) )
        {
            case ver_Alum:
                //obtiene del modelo los registros en un DefaultTableModel y lo asigna en la vista
                this.vista.tabAlumnos.setModel( this.modelo.gettabAlumnos() );
                break;
            case aniadir_Alum:
                //añade un nuevo registro
                if ( this.modelo.NuevoAlumno(
                        this.vista.txtDNI_Alum.getText(),
                        this.vista.txtNombre_Alum.getText() ,
                        this.vista.txtApellidos_Alum.getText(),
                        this.vista.txtTelf_Alum.getText(),
                        this.vista.txtCurso_Alum.getText() ) )
                {
                    this.vista.tabAlumnos.setModel( this.modelo.gettabAlumnos() );
                    JOptionPane.showMessageDialog(vista,"Exito: Nuevo registro agregado.");
                    this.vista.txtDNI_Alum.setText("");
                    this.vista.txtNombre_Alum.setText("") ;
                    this.vista.txtApellidos_Alum.setText("");
                    this.vista.txtTelf_Alum.setText("") ;
                    this.vista.txtCurso_Alum.setText("");

                }
                else //ocurrio un error
                    JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                break;
            case eliminar_Alum:
                if ( this.modelo.EliminarAlumno( this.vista.txtDNI_Alum.getText() ) )
                {
                    this.vista.tabAlumnos.setModel( this.modelo.gettabAlumnos() );
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    this.vista.txtDNI_Alum.setText("");
                    this.vista.txtNombre_Alum.setText("") ;
                    this.vista.txtApellidos_Alum.setText("");
                    this.vista.txtTelf_Alum.setText("") ;
                    this.vista.txtCurso_Alum.setText("");
                }   
                break;       
        }
    }

}
