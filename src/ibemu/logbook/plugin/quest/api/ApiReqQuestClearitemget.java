package ibemu.logbook.plugin.quest.api;

import ibemu.logbook.plugin.Utility;
import ibemu.logbook.plugin.quest.QuestCollection;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.Config;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@API("/kcsapi/api_req_quest/clearitemget")
public class ApiReqQuestClearitemget implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        if(!req.getRequestBody().isPresent()) return;
        try
        {
            InputStream stream = req.getRequestBody().get();
            if(stream.markSupported()) stream.reset();
            Map<String, String> m = Utility.getQueryMap(stream);
            String idstr = m.get("api_quest_id");
            if(idstr != null)
            {
                Integer id = Integer.valueOf(idstr);
                QuestCollection.get().getQuestMap().remove(id);
                ThreadManager.getExecutorService().execute(Config.getDefault()::store);
            }
        }
        catch(IOException e)
        {
            LoggerHolder.LOG.warn("任務削除に例外が発生しました", e);
        }
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(ApiReqQuestClearitemget.class);
    }
}
