package mx.com.villavicencio.generics.print;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Gabriel J
 */
public class ClosePrint {

    public static PrintWriter closePrintWriter(PrintWriter out) {
        if (out != null) {
            out.flush();
            out.close();
        }
        return out;
    }

    public static OutputStream closeOutputStream(OutputStream out) throws IOException {
        if (out != null) {
            out.flush();
            out.close();
        }
        return out;
    }
}
