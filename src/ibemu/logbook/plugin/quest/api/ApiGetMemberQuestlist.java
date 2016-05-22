package ibemu.logbook.plugin.quest.api;

import java.util.HashSet;
import java.util.Set;

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
            Set<Integer> keys = new HashSet<Integer>();
            int min = 9999;
            int max = 0;
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
                    if(key < min) min = key;
                    if(max < key) max = key;
                    keys.add(key);
                }
            }
            // TODO: マシな方法
            int min2 = min;
            int max2 = max;
            QuestCollection.get().getQuestMap().entrySet().removeIf(e -> (min2 < e.getKey()) && (e.getKey() < max2) && (!keys.contains(e.getKey())));
            ThreadManager.getExecutorService().execute(Config.getDefault()::store);
        }
    }
}
