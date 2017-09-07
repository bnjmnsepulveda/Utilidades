/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.template.example;

import basedatos.template.ResultSetAdapter;
import domain.Persona;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class PersonaAdapter extends ResultSetAdapter<Persona> {

    @Override
    public List<Persona> retrieveList(ResultSet resultSet) throws SQLException {
        list = new ArrayList();
        while(resultSet.next()){
            elemento = new Persona();
            elemento.setId(resultSet.getInt("id"));
            elemento.setNombre(resultSet.getString("nombre"));
            elemento.setApellido(resultSet.getString("apellido"));
            elemento.setEdad(resultSet.getInt("edad"));
            list.add(elemento);
        }
        return list;
    }

    @Override
    public Persona retrieveElement(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            elemento = new Persona();
            elemento.setId(resultSet.getInt("id"));
            elemento.setNombre(resultSet.getString("nombre"));
            elemento.setApellido(resultSet.getString("apellido"));
            elemento.setEdad(resultSet.getInt("edad"));
        }
        return elemento;                
    }

}
