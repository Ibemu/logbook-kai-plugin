package ibemu.logbook.plugin.quest.api;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import ibemu.logbook.plugin.Config;
import ibemu.logbook.plugin.quest.Quest;
import ibemu.logbook.plugin.quest.QuestCollection;
import ibemu.logbook.plugin.quest.QuestDue;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API("/kcsapi/api_get_member/questlist")
public class ApiGetMemberQuestlist implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        JsonObject apidata = json.getJsonObject("api_data");
        if (!apidata.isNull("api_list")) {
            JsonArray apilist = apidata.getJsonArray("api_list");
            for (JsonValue value : apilist) {
                if (value instanceof JsonObject) {
                    JsonObject questobject = (JsonObject) value;
                    int key = questobject.getInt("api_no");
                    // 任務を作成
                    Quest quest = Quest.setQuestlist(QuestCollection.get().getQuestMap().get(key), questobject);

                    switch (quest.getType())
                    {
                    case 2:
                    case 4:
                    case 5:
                        quest.setDue(QuestDue.getDaily());
                        break;
                    case 3:
                        quest.setDue(QuestDue.getWeekly());
                        break;
                    case 6:
                        quest.setDue(QuestDue.getMonthly());
                        break;
                    default:
                        break;
                    }
                    QuestCollection.get().getQuestMap().put(key, quest);
                }
            }
            ThreadManager.getExecutorService().execute(Config.getDefault()::store);
        }
    }
}
