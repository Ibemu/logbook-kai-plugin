package ibemu.logbook.plugin.quest.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import ibemu.logbook.plugin.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ibemu.logbook.plugin.quest.Quest;
import ibemu.logbook.plugin.quest.QuestCollection;
import ibemu.logbook.plugin.quest.QuestDue;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.internal.Config;
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
            Map<Integer, Quest> questMap = QuestCollection.get().getQuestMap();
            Set<Integer> keys = new HashSet<Integer>();
            int min = 9999;
            int max = 0;
            int pagecount = apidata.getInt("api_page_count");
            int disppage = apidata.getInt("api_disp_page");
            if(pagecount > 0)
            {
                if(disppage == 1) min = 0;
                if(disppage == pagecount) max = 9999;
                for (JsonValue value : apilist) {
                    if (value instanceof JsonObject) {
                        JsonObject questobject = (JsonObject) value;
                        int key = questobject.getInt("api_no");
                        // 任務を作成
                        Quest quest = Quest.setQuestlist(questMap.get(key), questobject);

                        switch (quest.getType())
                        {
                        case 1:
                            quest.setDue(QuestDue.getDaily());
                            break;
                        case 2:
                            quest.setDue(QuestDue.getWeekly());
                            break;
                        case 3:
                            quest.setDue(QuestDue.getMonthly());
                            break;
                        case 5:
                            switch (quest.getNo())
                            {
                            case 211:
                            case 212:
                                quest.setDue(QuestDue.getDaily());
                                break;
                            }
                        }
                        questMap.put(key, quest);
                        if(key < min) min = key;
                        if(max < key) max = key;
                        keys.add(key);
                    }
                }
            }
            else
            {
                min = 0;
                max = 9999;
            }
            if(req.getRequestBody().isPresent())
            {
                try
                {
                    InputStream stream = req.getRequestBody().get();
                    if(stream.markSupported()) stream.reset();
                    Map<String, String> m = Utility.getQueryMap(stream);
                    questMap.entrySet().removeIf(new QuestRemover(min, max, Integer.parseInt(m.get("api_tab_id")), keys));
                }
                catch (IOException e)
                {
                    LoggerHolder.LOG.warn("任務削除に例外が発生しました", e);
                }
            }
            ThreadManager.getExecutorService().execute(Config.getDefault()::store);
        }
    }

    private class QuestRemover implements Predicate<Entry<Integer, Quest>>
    {
        private final int min;
        private final int max;
        private final int tab;
        private final Set<Integer> keys;

        public QuestRemover(int min, int max, int tab, Set<Integer> keys)
        {
            this.min = min;
            this.max = max;
            this.tab = tab;
            this.keys = keys;
        }

        @Override
        public boolean test(Entry<Integer, Quest> e)
        {
            int key = e.getKey();
            boolean base = (min < key) && (key < max) && (!keys.contains(key));
            int state = e.getValue().getState();
            int type = e.getValue().getType();
            switch(tab)
            {
            case 0:
                // 全 All
                return base;

            case 9:
                // 遂行中任務
                return base && (state == 2 || state == 3);

            default:
                return base && type == tab;
            }
        }

    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(ApiReqQuestClearitemget.class);
    }
}
