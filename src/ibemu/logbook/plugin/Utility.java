package ibemu.logbook.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> getQueryMap(InputStream req) throws IOException {
        return getQueryMap(URLDecoder.decode(toString(req), StandardCharsets.UTF_8.name()));
    }

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] pair = param.split("=");
            String name = pair[0];
            String value = null;
            if (pair.length == 2) {
                value = pair[1];
            }
            map.put(name, value);
        }
        return map;
    }
}
