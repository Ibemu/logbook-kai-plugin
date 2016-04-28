package ibemu.logbook.plugin.quest.api;

import java.util.Date;

import javax.json.JsonObject;

import ibemu.logbook.plugin.Config;
import ibemu.logbook.plugin.quest.QuestCollection;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API("/kcsapi/api_req_hokyu/charge")
public class ApiReqHokyuCharge implements APIListenerSpi
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
                       .forEach(q -> q.countCharge(now));
        ThreadManager.getExecutorService().execute(Config.getDefault()::store);
    }

}
