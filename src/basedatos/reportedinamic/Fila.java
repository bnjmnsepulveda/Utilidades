/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.reportedinamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class Fila  implements Serializable{

    private List<Data> data;
    private int dataSize;

    public Fila() {
        dataSize = 0;
    }

    public Fila(List<Data> data) {
        this.data = data;
        this.dataSize = data.size();
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
        this.dataSize = data.size();
    }

    public void addData(Data data) {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(data);
        this.dataSize = this.data.size();
    }
    
    public int getDataSize() {
        return dataSize;
    }
}
