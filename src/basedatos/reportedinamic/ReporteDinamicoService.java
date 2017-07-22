/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.reportedinamic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roberto
 */
public class ReporteDinamicoService {

    private static final Logger logger = LogManager.getLogger(ReporteDinamicoService.class);

    public Reporte readReporte(String sql, ReporteAdapter adapter) {
        Reporte reporte = null;
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
              //  conn = ConexionPostgres.conectar();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                reporte = adapter.read(rs);
            } catch (SQLException e) {
                logger.error(sql);
                logger.error(e.getMessage(), e);
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return reporte;
    }

    public Reporte readReporte(String sql, PrepareStatementListener parametros, ReporteAdapter adapter) {
        Reporte reporte = null;
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
              //  conn = ConexionPostgres.conectar();
                ps = conn.prepareStatement(sql);
                ps = parametros.getParametros(ps);
                rs = ps.executeQuery();
                reporte = adapter.read(rs);
            } catch (SQLException e) {
                logger.error(sql);
                logger.error(e.getMessage(), e);
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return reporte;
    }

    public Object readSingleValue(String sql, SingleValueAdapter adapter) {
        Object value = null;
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
//                conn = ConexionPostgres.conectar();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                value = adapter.read(rs);
            } catch (SQLException e) {
                logger.error(sql);
                logger.error(e.getMessage(), e);
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public Object readSingleValue(String sql, PrepareStatementListener parametros, SingleValueAdapter adapter) {
        Object value = null;
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
//                conn = ConexionPostgres.conectar();
                ps = conn.prepareStatement(sql);
                ps = parametros.getParametros(ps);
                rs = ps.executeQuery();
                value = adapter.read(rs);
            } catch (SQLException e) {
                logger.error(sql);
                logger.error(e.getMessage(), e);
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    /**
     * Permite agregar parametros a el PreparementStatement de la operacion de
     * base de datos.
     */
    public interface PrepareStatementListener {
        PreparedStatement getParametros(PreparedStatement ps) throws SQLException;
    }

    /**
     * Adaptador de reporte para ReporteDinamico
     */
    public interface ReporteAdapter {

        Reporte read(ResultSet resultSet) throws SQLException;
    }

    public interface SingleValueAdapter {

        Object read(ResultSet resultSet) throws SQLException;
    }

    public static void main(String[] args) {
        ReporteDinamicoService dao = new ReporteDinamicoService();
        Object o = dao.readSingleValue("SELECT COUNT(*) FROM secretaria", new PrepareStatementListener() {
            @Override
            public PreparedStatement getParametros(PreparedStatement ps) throws SQLException {
                return ps;
            }
        }, new SingleValueAdapter() {
            @Override
            public Object read(ResultSet resultSet) throws SQLException {
                Integer i = null;
                if (resultSet.next()) {
                    i = resultSet.getInt(1);
                }
                return i;
            }
        });
        System.out.println("agentes:" + o);

        Reporte r = dao.readReporte("SELECT * FROM secretaria", new PrepareStatementListener() {
            @Override
            public PreparedStatement getParametros(PreparedStatement ps) throws SQLException {
                return ps;
            }
        }, new ReporteAdapter() {
            @Override
            public Reporte read(ResultSet rs) throws SQLException {
                Reporte r = new Reporte("Reporte secretaria");
                r.addCabezera("id");
                r.addCabezera("nombre");
                r.addCabezera("anexo piloto");
                Fila f;
                while (rs.next()) {
                    f = new Fila();
                    f.addData(new Data(rs.getInt("id") + ""));
                    f.addData(new Data(rs.getString("nombre")));
                    f.addData(new Data(rs.getString("piloto_asociado")));
                    r.addFila(f);
                }
                return r;
            }
        });

        System.out.println("titulo reporte: " + r.getTitulo());
        for (String c : r.getCabezeras()) {
            System.out.print(c + " ");
        }
        System.out.println("");
        System.out.println("====================================");
        for (Fila f : r.getFilas()) {
            for (Data d : f.getData()) {
                System.out.print(d + " ");
            }
            System.out.println("");
            System.out.println("--------------------------------");
        }
    }
}
