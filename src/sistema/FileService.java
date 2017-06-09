package sistema;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Roberto
 */
public class FileService {

    /**
     * Leer un archivo properties.
     * @param path
     * @return 
     */
    public Properties readProperties(String path) {
        Properties p = null;
        try {
            InputStream input = null;
            try {
                p = new Properties();
                input = new FileInputStream(path);
                p.load(input);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return p;
    }
}
