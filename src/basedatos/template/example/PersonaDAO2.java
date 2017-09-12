package basedatos.template.example;

import basedatos.template.AbstractCRUDTemplate;
import domain.Persona;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benjamin
 */
public class PersonaDAO2 extends AbstractCRUDTemplate<Persona> {

    @Override
    protected Connection getConection() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public List<Persona> retrieveEntityList(ResultSet rs) throws SQLException {
        List<Persona> personas = new ArrayList();
        Persona persona;
        while (rs.next()) {
            persona = new Persona();
            persona.setId(rs.getInt("id"));
            persona.setNombre(rs.getString("nombre"));
            persona.setApellido(rs.getString("apellido"));
            persona.setEdad(rs.getInt("edad"));
            personas.add(persona);
        }
        return personas;
    }

    @Override
    public Persona retrieveEntity(ResultSet rs) throws SQLException {
        Persona persona = null;
        if (rs.next()) {
            persona = new Persona();
            persona.setId(rs.getInt("id"));
            persona.setNombre(rs.getString("nombre"));
            persona.setApellido(rs.getString("apellido"));
            persona.setEdad(rs.getInt("edad"));
        }
        return persona;
    }
    
    public List<Persona> readAll(){
        return select("SELECT * FROM persona");
    }

    

}
