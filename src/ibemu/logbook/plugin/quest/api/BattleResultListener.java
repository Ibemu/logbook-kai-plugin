package ibemu.logbook.plugin.quest.api;

import javax.json.JsonObject;

import logbook.api.APIListenerSpi;
import logbook.bean.AppCondition;
import logbook.bean.BattleLog;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

public class BattleResultListener implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        BattleLog log = AppCondition.get().getBattleResult();
        if(log != null) ApiReqSortieBattleresult.setLog(log);
    }

}
