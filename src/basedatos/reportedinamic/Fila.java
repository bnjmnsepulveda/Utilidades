
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
    
    public void addData(String value) {
        Data newData = new Data(value);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(String value,String param) {
        Data newData = new Data(value,param);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(int value) {
        Data newData = new Data(value);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(int value,String param) {
        Data newData = new Data(value,param);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(double value) {
        Data newData = new Data(value);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(double value,String param) {
        Data newData = new Data(value,param);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(long value) {
        Data newData = new Data(value);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public void addData(long value,String param) {
        Data newData = new Data(value,param);
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(newData);
        this.dataSize = this.data.size();
    }
    
    public int getDataSize() {
        return dataSize;
    }
}
