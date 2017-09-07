
package basedatos.template.example;

import basedatos.template.CRUDTemplate;
import basedatos.template.PreparedStatementListener;
import domain.Persona;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class PersonaDAO {
    
    private CRUDTemplate<Persona> template;
    private PersonaAdapter adapter;

   
    public List<Persona> readAll(){
        adapter = new PersonaAdapter();
        template = new CRUDTemplate(adapter);
        template.setConexion(null);
        List<Persona> personas =  template.select("SELECT * FROM persona",new PreparedStatementListener() {
            @Override
            public PreparedStatement addParametros(PreparedStatement ps) throws SQLException {
                return ps;
            }
        });
        return personas;
    }
    
    
}
