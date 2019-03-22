package ibemu.logbook.plugin.jsonviewer;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPInputStream;

import javax.json.JsonObject;

import ibemu.logbook.plugin.Utility;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiData
{
    private static final ObservableList<ApiData> APILIST = FXCollections.observableArrayList();

    /** タイムスタンプ */
    private final LocalDateTime timestamp;
    private final String timestampString;
    private final ReadOnlyStringProperty timestampProperty;

    /** APIのURI */
    private final String uri;
    private final ReadOnlyStringProperty uriProperty;

    /** APIのリクエスト */
    private final String request;
    private final ReadOnlyStringProperty requestProperty;

    /** APIのレスポンス */
    private final String response;
    private final ReadOnlyStringProperty responseProperty;

    /** Json */
    private final JsonObject json;
    private final ReadOnlyObjectProperty<TreeItem<NamedJsonValue>> jsonTreeItem;

    public static ObservableList<ApiData> getApiList()
    {
        return APILIST;
    }

    public ApiData(JsonObject json, RequestMetaData req, ResponseMetaData res) throws IOException
    {
        this.timestamp = LocalDateTime.now();
        String ts = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        switch(ts.length())
        {
        case 19:
            ts += ".000";
            break;
        case 20:
            ts += "000";
            break;
        case 21:
            ts += "00";
            break;
        case 22:
            ts += "0";
            break;
        }
        this.timestampString = ts;

        this.timestampProperty = new SimpleStringProperty(this.timestampString);

        this.uri = req.getRequestURI();
        this.uriProperty = new SimpleStringProperty(this.uri);

        if(req.getRequestBody().isPresent())
        {
            req.getRequestBody().get().reset();
            this.request = Utility.toString(req.getRequestBody().get());
        }
        else this.request = "";
        this.requestProperty = new SimpleStringProperty(this.request);

        if(res.getResponseBody().isPresent())
        {
            InputStream stream = res.getResponseBody().get();
            if(stream.markSupported())
            {
                stream.reset();
                // Check header
                int header = (stream.read() | (stream.read() << 8));
                stream.reset();
                if (header == GZIPInputStream.GZIP_MAGIC)
                    stream = new GZIPInputStream(stream);
            }
            String resp;
            try
            {
                resp = Utility.toString(stream);
            }
            catch(IOException e)
            {
                resp = "エラー: " + e.getMessage();
                LoggerHolder.LOG.warn("JsonViewer処理中に例外が発生しました", e);
            }
            this.response = resp;
        }
        else this.response = "レスポンスが存在しません";
        this.responseProperty = new SimpleStringProperty(this.response);

        this.json = json;
        this.jsonTreeItem = new SimpleObjectProperty<>(new JsonTreeItem(new NamedJsonValue("svdata", json)));
    }

    @Override
    public String toString()
    {
        return timestampString + " : " + uri;
    }

    /**
     * @return timestamp
     */
    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    /**
     * @return timestamp
     */
    public String getTimestampString()
    {
        return timestampString;
    }

    public ReadOnlyStringProperty timestampProperty()
    {
        return timestampProperty;
    }

    /**
     * @return uri
     */
    public String getUri()
    {
        return uri;
    }

    public ReadOnlyStringProperty uriProperty()
    {
        return uriProperty;
    }

    /**
     * @return request
     */
    public String getRequest()
    {
        return request;
    }

    public ReadOnlyStringProperty requestProperty()
    {
        return requestProperty;
    }

    /**
     * @return response
     */
    public String getResponse()
    {
        return response;
    }

    public ReadOnlyStringProperty responseProperty()
    {
        return responseProperty;
    }

    /**
     * @return json
     */
    public JsonObject getJson()
    {
        return json;
    }

    public ReadOnlyObjectProperty<TreeItem<NamedJsonValue>> jsonProperty()
    {
        return jsonTreeItem;
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(ApiData.class);
    }
}
