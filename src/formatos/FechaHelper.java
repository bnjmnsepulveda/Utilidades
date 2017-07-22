/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatos;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Roberto
 */
public class FechaHelper {

    public static String hora(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
        return dt.format(date);
    }

    public static String fechaHora(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-d HH:mm");
        return dt.format(date);
    }

    public static String fechaHoraTimestamp(Timestamp ts) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(ts);
    }
}
