package ibemu.logbook.plugin.widget;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logbook.bean.*;
import logbook.internal.BattleLogs;
import logbook.internal.Mapping;
import logbook.internal.PhaseState;
import logbook.internal.gui.BattleDetail;
import logbook.internal.gui.BattleLogDetail;
import logbook.internal.gui.WindowController;
import logbook.internal.log.BattleResultLogFormat;
import logbook.internal.log.LogWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class SimpleBattleWidgetController extends WindowController
{
    @FXML
    private VBox root;

    @FXML
    private Label map;

    @FXML
    private Label boss;

    @FXML
    private Label count;

    @FXML
    private Label day;

    @FXML
    private Label rank;

    @FXML
    private FlowPane fleetList;

    private Timeline timeline;

    private long logHashCode;

    @FXML
    void initialize()
    {
        try
        {
            this.timeline = new Timeline();
            this.timeline.setCycleCount(Timeline.INDEFINITE);
            this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::update));
            this.timeline.play();
        }
        catch(Exception e)
        {
            LoggerHolder.LOG.error("FXMLの初期化に失敗しました", e);
        }
    }

    void update(ActionEvent e)
    {
        BattleLog log = AppCondition.get().getBattleResult();
        if(log == null || log.getBattle() == null)
        {
            // 最後のログ
            Path dir = Paths.get(AppConfig.get().getReportPath());
            Path path = dir.resolve(new BattleResultLogFormat().fileName());
            if(Files.exists(path))
            {
                try(Stream<String> lines = Files.lines(path, LogWriter.DEFAULT_CHARSET))
                {
                    BattleLogs.SimpleBattleLog battleLog = lines.skip(1).reduce((first, second) -> second)
                            .map(BattleLogs.SimpleBattleLog::new)
                            .orElse(null);
                    if(battleLog != null)
                    {
                        log = BattleLogs.read(BattleLogDetail.toBattleLogDetail(battleLog).getDate());
                    }
                }
                catch(Exception ex)
                {
                    log = null;
                }
            }
        }
        if(log == null || log.getBattle() == null) return;
        long newHashCode = log.hashCode();
        if(this.logHashCode != newHashCode)
        {
            PhaseState ps = new PhaseState(log);
            Class<?> judgeClazz = Arrays.stream(BattleDetail.class.getDeclaredClasses()).filter(c -> c.getName().endsWith("Judge")).findAny().orElse(null);
            Object judge = null;

            if(judgeClazz != null)
            {
                try
                {
                    Constructor<?> ctor = judgeClazz.getDeclaredConstructor();
                    ctor.setAccessible(true);
                    judge = ctor.newInstance();

                    Method setBefore = judgeClazz.getDeclaredMethod("setBefore", PhaseState.class);
                    setBefore.setAccessible(true);
                    setBefore.invoke(judge, ps);
                }
                catch(Exception ex)
                {
                    LoggerHolder.LOG.warn("JudgeClazz Before", ex);
                }
            }

            // 艦隊
            ObservableList<Node> fleets = this.fleetList.getChildren();
            fleets.clear();
            SimpleBattleFleetPane f1 = null;
            SimpleBattleFleetPane f2 = null;
            SimpleBattleFleetPane e2 = null;
            SimpleBattleFleetPane e1 = null;
            Separator sep = new Separator();
            sep.setOrientation(Orientation.VERTICAL);

            if(!ps.getAfterFriend().isEmpty())
            {
                f1 = new SimpleBattleFleetPane(ps.getAfterFriend().stream().map(c -> c));
            }
            if(!ps.getAfterFriendCombined().isEmpty())
            {
                f2 = new SimpleBattleFleetPane(ps.getAfterFriendCombined().stream().map(c -> c));
            }
            if(!ps.getAfterEnemyCombined().isEmpty())
            {
                e2 = new SimpleBattleFleetPane(ps.getAfterEnemyCombined().stream().map(c -> c));
            }
            if(!ps.getAfterEnemy().isEmpty())
            {
                e1 = new SimpleBattleFleetPane(ps.getAfterEnemy().stream().map(c -> c));
            }
            ps.apply(log.getBattle());
            ps.apply(log.getMidnight());
            if(!ps.getAfterFriend().isEmpty() && f1 != null)
            {
                f1.applyAfterHp(ps.getAfterFriend().stream().map(c -> c));
                fleets.add(f1);
            }
            if(!ps.getAfterFriendCombined().isEmpty() && f2 != null)
            {
                f2.applyAfterHp(ps.getAfterFriendCombined().stream().map(c -> c));
                fleets.add(f2);
            }
            fleets.add(sep);
            if(!ps.getAfterEnemyCombined().isEmpty() && e2 != null)
            {
                e2.applyAfterHp(ps.getAfterEnemyCombined().stream().map(c -> c));
                fleets.add(e2);
            }
            if(!ps.getAfterEnemy().isEmpty() && e1 != null)
            {
                e1.applyAfterHp(ps.getAfterEnemy().stream().map(c -> c));
                fleets.add(e1);
            }

            // 情報
            MapStartNext last = log.getNext().size() > 0 ? log.getNext().get(log.getNext().size() - 1) : null;
            if(last == null)
            {
                this.map.setText("");
                this.boss.setText("");
            }
            else
            {
                boolean boss = last.getNo().equals(last.getBosscellNo()) || last.getEventId() == 5;
                this.map.setText(last.getMapareaId() + "-" + last.getMapinfoNo()
                        + "-" + Mapping.getCell(last.getMapareaId(), last.getMapinfoNo(), last.getNo()));
                this.boss.setText(boss ? "○" : "✕");
            }

            if(log.getBattleCount() == null)
            {
                this.count.setText("");
            }
            else
            {
                this.count.setText(String.format("%d戦%s", log.getBattleCount(), log.getResult() == null ? "目" : "終了"));
            }

            if(log.getMidnight() == null)
            {
                this.day.setText(log.getBattle().isINightToDayBattle() ? "夜昼" : "昼戦");
            }
            else
            {
                this.day.setText(log.getMidnight().isINightToDayBattle() ? "夜昼" : "夜戦");
            }


            if(log.getResult() == null)
            {
                if(judgeClazz != null)
                {
                    try
                    {
                        Method setAfter = judgeClazz.getDeclaredMethod("setAfter", PhaseState.class, BattleTypes.IFormation.class);
                        setAfter.setAccessible(true);
                        setAfter.invoke(judge, ps, log.getBattle());

                        Method getRank = judgeClazz.getDeclaredMethod("getRank");
                        getRank.setAccessible(true);
                        this.rank.setText(String.valueOf(getRank.invoke(judge)).charAt(0) + "?");
                    }
                    catch(Exception ex)
                    {
                        this.rank.setText("?");
                        LoggerHolder.LOG.warn("JudgeClazz After", ex);
                    }
                }
                else
                {
                    this.rank.setText("?");
                }
            }
            else
            {
                this.rank.setText(log.getResult().getWinRank());
            }

            //
            this.root.setPrefWidth(this.root.getWidth());
            this.getWindow().sizeToScene();

            //
            this.logHashCode = newHashCode;
        }
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(SimpleBattleWidgetController.class);
    }
}
