package ibemu.logbook.plugin.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.json.JsonObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
            fname = FilenameUtils.concat(req.getRequestURI().substring(8), fname);
            // ファイルパス
            File file = new File(FilenameUtils.concat(PluginConfig.get().getKcsapiRequestPath(), fname));
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.copy(req.getRequestBody().get(), fos);
            fos.close();
        }
    }

    public void kcsapiResponse(JsonObject json, RequestMetaData req, String time) throws IOException
    {
        // ファイル名
        String fname = new StringBuilder().append(time).append(".json").toString();
        fname = FilenameUtils.concat(req.getRequestURI().substring(8), fname);
        // ファイルパス
        File file = new File(FilenameUtils.concat(PluginConfig.get().getKcsapiResponsePath(), fname));
        FileUtils.writeStringToFile(file, json.toString(), Charset.defaultCharset());
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(Kcsapi.class);
    }
}
