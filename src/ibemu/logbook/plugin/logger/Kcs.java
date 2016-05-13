package ibemu.logbook.plugin.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import logbook.proxy.ContentListenerSpi;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

public class Kcs implements ContentListenerSpi
{

    @Override
    public boolean test(RequestMetaData req)
    {
        if(/*PluginConfig.get().isStoreKcsRequest()||*/
                PluginConfig.get().isStoreKcsResource()||
                PluginConfig.get().isStoreKcsResponse())
            return req.getRequestURI().startsWith("/kcs/");
        return false;
    }

    @Override
    public void accept(RequestMetaData req, ResponseMetaData res)
    {
        try {
            //if(PluginConfig.get().isStoreKcsRequest()) kcsRequest(req);
            if(PluginConfig.get().isStoreKcsResponse()) kcsResponse(req, res);
            if(PluginConfig.get().isStoreKcsResource()) kcsResource(req, res);
        } catch(IOException e) {
            LoggerHolder.LOG.warn("kcs処理中に例外が発生しました", e);
        }
    }
/*
    public void kcsRequest(RequestMetaData req)
    {
        if(req.getRequestBody().isPresent()) {
            // ファイル名
            int i = req.getRequestURI().lastIndexOf("/kcs/");
            StringBuilder fname = new StringBuilder(req.getRequestURI().substring(5));

            // ファイルパス
            try
            {
                FileOutputStream fos = new FileOutputStream(FilenameUtils.concat(PluginConfig.get().getKcsRequestPath(), fname.toString()));
                IOUtils.copy(req.getRequestBody().get(), fos);
                fos.close();
            }
            catch (Exception e)
            {
                LOG.warn(e.getLocalizedMessage());
            }
        }
    }
*/
    public void kcsResponse(RequestMetaData req, ResponseMetaData res) throws IOException
    {
        if(res.getResponseBody().isPresent()) {
            // ファイル名
            int i = req.getRequestURI().lastIndexOf(".");
            StringBuilder fname;
            if(i<0) {
                //拡張子なし
                fname = new StringBuilder(req.getRequestURI().substring(5));
                if(PluginConfig.get().isAddKcsQueryString() && req.getQueryString() != null) {
                    fname.append('_').append(req.getQueryString());
                }
            }
            else {
                fname = new StringBuilder(req.getRequestURI().substring(5,i));
                if(PluginConfig.get().isAddKcsQueryString() && req.getQueryString() != null) {
                    fname.append('_').append(req.getQueryString());
                }
                fname.append(req.getRequestURI().substring(i));
            }
            // ファイルパス
            File file = new File(FilenameUtils.concat(PluginConfig.get().getKcsResponsePath(), fname.toString()));
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.copy(res.getResponseBody().get(), fos);
            fos.close();
        }
    }

    public void kcsResource(RequestMetaData req, ResponseMetaData res)
    {
        //TODO
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(Kcs.class);
    }
}
