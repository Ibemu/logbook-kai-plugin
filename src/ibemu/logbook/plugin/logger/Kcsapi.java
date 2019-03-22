package ibemu.logbook.plugin.logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import logbook.api.APIListenerSpi;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

public class Kcsapi implements APIListenerSpi
{
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd/HH-mm-ss.SSS");

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        try {
            String time = FORMAT.format(Calendar.getInstance().getTime());
            if(PluginConfig.get().isStoreKcsapiRequest()) kcsapiRequest(req, time);
            if(PluginConfig.get().isStoreKcsapiResponse()) kcsapiResponse(json, req, time);
        } catch(IOException e) {
            LoggerHolder.LOG.warn("kcsapi処理中に例外が発生しました", e);
        }
    }

    public void kcsapiRequest(RequestMetaData req, String time) throws IOException
    {
        if(req.getRequestBody().isPresent()) {
            // ファイル名
            String fname = new StringBuilder().append(time).append(".txt").toString();
            // ファイルパス
            Path path = Paths.get(PluginConfig.get().getKcsapiRequestPath(), req.getRequestURI().substring(8), fname);
            Files.createDirectories(path.getParent());
            Files.copy(req.getRequestBody().get(), path);
        }
    }

    public void kcsapiResponse(JsonObject json, RequestMetaData req, String time) throws IOException
    {
        // ファイル名
        String fname = new StringBuilder().append(time).append(".json").toString();
        // ファイルパス
        Path path = Paths.get(PluginConfig.get().getKcsapiResponsePath(), req.getRequestURI().substring(8), fname);
        Files.createDirectories(path.getParent());
        Files.write(path, json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(Kcsapi.class);
    }
}
