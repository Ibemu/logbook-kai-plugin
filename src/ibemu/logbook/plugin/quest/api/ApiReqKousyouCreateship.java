package ibemu.logbook.plugin.quest.api;

import ibemu.logbook.plugin.quest.QuestCollection;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.Config;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

import javax.json.JsonObject;
import java.util.Date;

@API("/kcsapi/api_req_kousyou/createship")
public class ApiReqKousyouCreateship implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        Date now = new Date();
        QuestCollection.get()
                .getQuestMap()
                .values()
                .stream()
                .filter(q -> q.getState() == 2)
                .forEach(q -> q.countCreateShip(now));
        ThreadManager.getExecutorService().execute(Config.getDefault()::store);
    }

}
