package ibemu.logbook.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class Utility {
    public static String toString(InputStream is) throws IOException {
        InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[1024];
        int len;
        while (0 <= (len = reader.read(buf))) {
            builder.append(buf, 0, len);
        }
        return builder.toString();
    }
}
