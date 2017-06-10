package ibemu.logbook.plugin.jsonviewer.api;

import java.io.IOException;

import javax.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ibemu.logbook.plugin.jsonviewer.ApiData;
import javafx.collections.ObservableList;
import logbook.api.APIListenerSpi;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

public class Api implements APIListenerSpi
{
    public static final int MAX_APIDATA = 50;

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        try
        {
            ObservableList<ApiData> list = ApiData.getApiList();
            list.add(0, new ApiData(json, req, res));
            if(list.size() > MAX_APIDATA) list.remove(MAX_APIDATA, list.size());
        }
        catch (IOException e)
        {
            LoggerHolder.LOG.warn("JsonViewer処理中に例外が発生しました", e);
        }
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(Api.class);
    }
}
