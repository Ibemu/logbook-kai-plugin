package ibemu.logbook.plugin.quest.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ibemu.logbook.plugin.Config;
import ibemu.logbook.plugin.quest.ApiHelper;
import ibemu.logbook.plugin.quest.QuestCollection;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API("/kcsapi/api_req_quest/clearitemget")
public class ApiReqQuestClearitemget implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        if(!req.getRequestBody().isPresent()) return;
        try
        {
            Map<String, String> m = ApiHelper.getQueryMap(req.getRequestBody().get());
            String idstr = m.get("api_quest_id");
            if (idstr != null) {
                Integer id = Integer.valueOf(idstr);
                QuestCollection.get().getQuestMap().remove(id);
                ThreadManager.getExecutorService().execute(Config.getDefault()::store);
            }
        }
        catch (IOException e)
        {
            LoggerHolder.LOG.warn("任務削除に例外が発生しました", e);
        }
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(ApiReqQuestClearitemget.class);
    }
}
