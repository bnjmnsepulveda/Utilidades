package basedatos.ejecutarquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * crea un menu de opciones para ejecutar sql en base de datos.
 * @author benjamin
 */
public class App {

    private String host;
    private String basedatos;
    private String usuario;
    private String clave;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d HH:mm:ss.000000");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App app = new App();
        app.host = "192.168.100.30";
        app.basedatos = "acdkallControl_contact";
        app.usuario = "postgres";
        app.clave = "adp2016";
        
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
 
        while (!salir) {
 
            System.out.println("1. insertar llamada contestada");
            System.out.println("2. insertar llamada perdida");
            System.out.println("3. (WARNNING) eliminar data de pruebas!!!");
            System.out.println("4. Salir");
 
            try {
 
                System.out.println("Escribe una de las opciones");
                System.out.println("formato " + sdf.format(Calendar.getInstance().getTime()));
                opcion = sn.nextInt();
                
                Calendar ringeo  = Calendar.getInstance();
                Calendar contesto  = Calendar.getInstance();
                Calendar corto  = Calendar.getInstance();
                ringeo.add(Calendar.MINUTE, -2);
                contesto.add(Calendar.MINUTE, -1);
                switch (opcion) {
                    case 1:
                        System.out.println("nueva llamada contestada!!!");
                        app.ejecutarSQL("INSERT INTO estadistica (anexo, ringeo, contesto, corto, duracion_llamada, duracion_ringeo, estado, pool, usuario, contraparte, cortado, tipo) \n" +
"	VALUES ('204810', '" + sdf.format(ringeo.getTime()) + "', '" + sdf.format(contesto.getTime()) + "', '" + sdf.format(corto.getTime()) + "', 60, 3, 1, '8200', 'TESTING MCabrera', '9976437943', '204810', NULL)");
                        break;
                    case 2:
                        System.out.println("nueva llamda perdida!!!");
                        app.ejecutarSQL("INSERT INTO estadistica (anexo, ringeo, contesto, corto, duracion_llamada, duracion_ringeo, estado, pool, usuario, contraparte, cortado, tipo) \n" +
"	VALUES ('204810', '" + sdf.format(ringeo.getTime()) + "', NULL, '" + sdf.format(corto.getTime()) + "', 0, 7, 0, '8200', 'TESTING LBerguno', '9977292647', NULL, NULL);\n" +
"");
                        break;   
                    case 3:
                        System.out.println("Eliminadn datos de prueba ");
                        app.ejecutarSQL("delete from estadistica where ringeo > '2018-07-05'");
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo opciones entre 1 y 4");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
        

    }

    public boolean ejecutarSQL(String sql) {
        boolean update = false;
        try {
            Connection cx = null;
            PreparedStatement ps = null;
            try {
                Class.forName("org.postgresql.Driver").newInstance();
                cx = DriverManager.getConnection("jdbc:postgresql://" + host + ":5432/" + basedatos, usuario, clave);
                ps = cx.prepareStatement(sql);
                update = ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            } finally {
                if (cx != null) {
                    cx.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace(System.err);
        }
        return update;
    }

}
