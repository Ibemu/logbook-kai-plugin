package ibemu.logbook.plugin.quest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public final class ApiHelper
{
    public static Map<String, String> getQueryMap(InputStream req) throws UnsupportedEncodingException, IOException {
        return getQueryMap(URLDecoder.decode(IOUtils.toString(req, StandardCharsets.UTF_8), StandardCharsets.UTF_8.name()));
    }

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] splited = param.split("=");
            String name = splited[0];
            String value = null;
            if (splited.length == 2) {
                value = splited[1];
            }
            map.put(name, value);
        }
        return map;
    }
}
