package ibemu.logbook.plugin.quest;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ibemu.logbook.plugin.Config;

public class QuestCollection implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 8182642465722256708L;

    /** 任務 */
    private Map<Integer, Quest> questMap = new LinkedHashMap<>();

    /**
     * 任務を取得します。
     * @return 任務
     */
    public Map<Integer, Quest> getQuestMap() {
        questMap.entrySet().removeIf(x -> (x.getValue().getDue() != null) && x.getValue().getDue().before(new Date()));
        return this.questMap;
    }

    /**
     * 任務を設定します。
     * @param shipMap 任務
     */
    public void setQuestMap(Map<Integer, Quest> questMap) {
        this.questMap = questMap;
    }

    /**
     * アプリケーションのデフォルト設定ディレクトリから{@link QuestCollection}を取得します、
     * これは次の記述と同等です
     * <blockquote>
     *     <code>Config.getDefault().get(QuestCollection.class, QuestCollection::new)</code>
     * </blockquote>
     *
     * @return {@link ShipCollection}
     */
    public static QuestCollection get() {
        return Config.getDefault().get(QuestCollection.class, QuestCollection::new);
    }
}
