package ibemu.logbook.plugin.logger;

import java.io.IOException;
import java.nio.file.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import logbook.proxy.ContentListenerSpi;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

public class Kcs2 implements ContentListenerSpi
{

    @Override
    public boolean test(RequestMetaData req)
    {
        if(PluginConfig.get().isStoreKcs2Response())
            return req.getRequestURI().startsWith("/kcs2/");
        return false;
    }

    @Override
    public void accept(RequestMetaData req, ResponseMetaData res)
    {
        try {
            if(PluginConfig.get().isStoreKcs2Response()) kcs2Response(req, res);
        } catch(IOException e) {
            LoggerHolder.LOG.warn("kcs2処理中に例外が発生しました", e);
        }
    }

    public void kcs2Response(RequestMetaData req, ResponseMetaData res) throws IOException
    {
        if(res.getResponseBody().isPresent()) {
            // ファイル名
            int i = req.getRequestURI().lastIndexOf(".");
            StringBuilder fname;
            String qs = processQueryString(req.getQueryString());
            if(i<0) {
                //拡張子なし
                fname = new StringBuilder(req.getRequestURI().substring(5));
                if(qs != null) {
                    fname.append('_').append(qs);
                }
            }
            else {
                fname = new StringBuilder(req.getRequestURI().substring(5,i));
                if(qs != null) {
                    fname.append('_').append(qs);
                }
                fname.append(req.getRequestURI().substring(i));
            }
            // ファイルパス
            Path path = Paths.get(PluginConfig.get().getKcs2ResponsePath(), fname.toString());
            Files.createDirectories(path.getParent());
            Files.copy(res.getResponseBody().get(), path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private String processQueryString(String qs) {
        if(!PluginConfig.get().isAddKcs2QueryString()) return null;
        if(!PluginConfig.get().isRemoveApiToken()) return qs;
        if(qs == null) return null;
        return qs.replaceAll("&api(_|%5F)token=\\w+|api(_|%5F)token=\\w+&?", "");
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(Kcs2.class);
    }
}
