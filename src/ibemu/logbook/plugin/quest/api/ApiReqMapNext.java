package ibemu.logbook.plugin.quest.api;

import java.util.concurrent.TimeUnit;

import javax.json.JsonObject;

import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.bean.AppCondition;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API({"/kcsapi/api_req_map/start", "/kcsapi/api_req_map/next"})
public class ApiReqMapNext implements APIListenerSpi
{

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        // TODO: マシな方法
        for (int i = 0; i < 10 && AppCondition.get().getBattleResult() != null; ++i)
            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {
                break;
            }
        if(AppCondition.get().getBattleResult() != null) ApiReqSortieBattleresult.setLog(AppCondition.get().getBattleResult());
    }

}
