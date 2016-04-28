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

@API("/kcsapi/api_req_practice/battle_result")
public class ApiReqPracticeBattleResult implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        JsonObject apidata = json.getJsonObject("api_data");
        if (apidata != null) {
            String rank = apidata.getString("api_win_rank");
            boolean win = "B".equals(rank) || "A".equals(rank) || "S".equals(rank);

            Date now = new Date();
            QuestCollection.get()
                           .getQuestMap()
                           .values()
                           .stream()
                           .filter(q -> q.getState() == 2)
                           .forEach(q -> {
                               q.countPractice(now);
                               if (win) {
                                   q.countPracticeWin(now);
                               }
                           });
            ThreadManager.getExecutorService().execute(Config.getDefault()::store);
        }
    }

}
