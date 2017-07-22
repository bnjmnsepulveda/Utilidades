package basedatos.reportedinamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class Reporte  implements Serializable{

    private String titulo;
    private String pie;
    private List<String> cabezeras;
    private List<Fila> filas;

    public Reporte() {
    }

    public void addFila(Fila fila) {
        if (filas == null) {
            filas = new ArrayList();
        }
        filas.add(fila);
    }
    
    public void addCabezera(String cabezera) {
        if (cabezeras == null) {
            cabezeras = new ArrayList();
        }
        cabezeras.add(cabezera);
    }

    public Reporte(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public List<String> getCabezeras() {
        return cabezeras;
    }

    public void setCabezeras(List<String> cabezeras) {
        this.cabezeras = cabezeras;
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public void setFilas(List<Fila> filas) {
        this.filas = filas;
    }
  
}
