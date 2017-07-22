
package basedatos.reportedinamic;

import java.io.Serializable;

/**
 *
 * @author Roberto
 */
public class Data implements Serializable{

    private String valor;
    private String paramSubreporte;
    private boolean subreporte;

    public Data(String valor, String paramSubreporte) {
        this.valor = valor;
        this.paramSubreporte = paramSubreporte;
        this.subreporte = true;
    }

    public Data(Integer valor, String paramSubreporte) {
        this.valor = valor.toString();
        this.paramSubreporte = paramSubreporte;
        this.subreporte = true;
    }
    
    public Data(Double valor, String paramSubreporte) {
        this.valor = valor.toString();
        this.paramSubreporte = paramSubreporte;
        this.subreporte = true;
    }
    
    public Data(Long valor, String paramSubreporte) {
        this.valor = valor.toString();
        this.paramSubreporte = paramSubreporte;
        this.subreporte = true;
    }
    
    public Data(String valor) {
        this.valor = valor;
        subreporte = false;
    }
    
    public Data(Integer valor) {
        this.valor = valor.toString();
        subreporte = false;
    }
    
    public Data(Double valor) {
        this.valor = valor.toString();
        subreporte = false;
    }
    
    public Data(Long valor) {
        this.valor = valor.toString();
        subreporte = false;
    }

    public Data() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getParamSubreporte() {
        return paramSubreporte;
    }

    public void setParamSubreporte(String paramSubreporte) {
        this.paramSubreporte = paramSubreporte;
    }

    public boolean isSubreporte() {
        return subreporte;
    }

    public void setSubreporte(boolean subreporte) {
        this.subreporte = subreporte;
    }

    @Override
    public String toString() {
        return valor;
    }
}
