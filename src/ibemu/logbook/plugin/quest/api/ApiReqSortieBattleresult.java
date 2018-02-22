package ibemu.logbook.plugin.quest.api;

import java.util.Calendar;
import java.util.Date;

import javax.json.JsonObject;

import ibemu.logbook.plugin.Config;
import ibemu.logbook.plugin.quest.QuestCollection;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.bean.AppCondition;
import logbook.bean.BattleLog;
import logbook.bean.Enemy;
import logbook.bean.MapStartNext;
import logbook.internal.PhaseState;
import logbook.internal.Ships;
import logbook.internal.ThreadManager;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API({"/kcsapi/api_req_sortie/battleresult", "/kcsapi/api_req_combined_battle/battleresult"})
public class ApiReqSortieBattleresult implements APIListenerSpi
{
    private static BattleLog last = null;

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        BattleLog log = AppCondition.get().getBattleResult();
        while(log == null || log == last)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                return;
            }
            log = AppCondition.get().getBattleResultConfirm();
        }
        while(log.getResult() == null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                return;
            }
        }
        last = log;
        PhaseState ps = new PhaseState(log);
        PhaseState psm = null;
        if (log.getBattle() != null) {
            ps.apply(log.getBattle());
        }
        if (log.getMidnight() != null) {
            psm = new PhaseState(log.getCombinedType(), log.getMidnight(), log.getDeckMap(), log.getItemMap(), log.getEscape());
            psm.applyMidnightBattle(log.getMidnight());
        }
        PhaseState psl = psm == null ? ps : psm;
        boolean s = "S".equals(log.getResult().getWinRank());
        boolean a = s || "A".equals(log.getResult().getWinRank());
        boolean win = a || "B".equals(log.getResult().getWinRank());

        int ap = 0;
        int cv = 0;
        int ss = 0;

        for (int i = 0; i < psl.getAfterEnemy().size(); ++i) {
            Enemy e = psl.getAfterEnemy().get(i);
            if (e.getNowhp() <= 0 && Ships.stype(e).isPresent()) {
                switch (Ships.stype(e).get().getId()) {
                case 15: //補給艦
                    ap++;
                    break;
                case 7: //軽空母
                case 11: //正規空母
                    cv++;
                    break;
                case 13: //潜水艦
                    ss++;
                    break;
                }
            }
        }

        // TODO: マシな方法
        int ap2 = ap;
        int cv2 = cv;
        int ss2 = ss;

        MapStartNext msn = log.getNext().get(log.getNext().size()-1);
        Date now = new Date();
        QuestCollection.get()
                       .getQuestMap()
                       .values()
                       .stream()
                       .filter(q -> q.getState() == 2)
                       .forEach(q -> {
                           if (win) {
                               q.countBattleWin(now);
                           }
                           if (s) {
                               q.countBattleWinS(now);
                           }
                           int color = msn.getColorNo();
                           if ((color == 5) || (color == 8)) {
                               q.countBossArrive(now);
                               if (win) {
                                   q.countBossWin(now);
                                   switch (msn.getMapareaId()) {
                                   case 1:
                                       if ((msn.getMapinfoNo() == 4) && s) {
                                           q.countBoss1_4WinS(now);
                                       }
                                       if ((msn.getMapinfoNo() == 5) && a) {
                                           q.countBoss1_5WinA(now);
                                       }
                                       break;
                                   case 2:
                                       q.countBoss2Win(now);
                                       break;
                                   case 3:
                                       if (msn.getMapinfoNo() >= 3) {
                                           q.countBoss3_3pWin(now);
                                       }
                                       break;
                                   case 4:
                                       q.countBoss4Win(now);
                                       if (msn.getMapinfoNo() == 4) {
                                           q.countBoss4_4Win(now);
                                       }
                                       break;
                                   case 5:
                                       if ((msn.getMapinfoNo() == 2) && s) {
                                           q.countBoss5_2WinS(now);
                                       }
                                       break;
                                   case 6:
                                       if ((msn.getMapinfoNo() == 1) && s) {
                                           q.countBoss6_1WinS(now);
                                       }
                                       break;
                                   }
                               }
                           }
                           Calendar c = Calendar.getInstance();
                           c.setTime(now);
                           for (int i = 0; i < ap2; ++i) {
                               q.countDefeatAP(c.getTime());
                               c.add(Calendar.MILLISECOND, -1);
                           }
                           c.setTime(now);
                           for (int i = 0; i < cv2; ++i) {
                               q.countDefeatCV(c.getTime());
                               c.add(Calendar.MILLISECOND, -1);
                           }
                           c.setTime(now);
                           for (int i = 0; i < ss2; ++i) {
                               q.countDefeatSS(c.getTime());
                               c.add(Calendar.MILLISECOND, -1);
                           }
                       });
        ThreadManager.getExecutorService().execute(Config.getDefault()::store);
    }

}
