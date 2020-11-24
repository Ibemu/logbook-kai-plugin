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
import javafx.stage.WindowEvent;
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
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
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
    private Label intercept;

    @FXML
    private Label dispSeiku;

    @FXML
    private Label rank;

    @FXML
    private FlowPane fleetList;

    private Timeline timeline;

    private long logHashCode;
    private long cellHashCode;

    private ZonedDateTime nextUpdate;

    @FXML
    void initialize()
    {
        try
        {
            this.nextUpdate = ZonedDateTime.now();
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
        // スリープ復帰時に連続で呼ばれフリーズする問題
        if(ZonedDateTime.now().isBefore(this.nextUpdate)) return;
        this.nextUpdate = ZonedDateTime.now().plusNanos(500_000_000);

        BattleLog log = AppCondition.get().getBattleResult();
        if(log == null)
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
                    return;
                }
            }
        }
        if(log == null) return;
        boolean updated = updateBattle(log);
        if(!updated)
        {
            MapStartNext last = log.getNext().size() > 0 ? log.getNext().get(log.getNext().size() - 1) : null;
            updated = updateCell(last, false);
        }
        if(updated && this.getWindow() != null)
        {
            this.root.setPrefWidth(this.root.getWidth());
            this.getWindow().sizeToScene();
        }
    }

    private boolean updateBattle(BattleLog log)
    {
        if(log.getBattle() == null) return false;
        long newHashCode = Objects.hash(log.getBattle(), log.getMidnight(), log.getResult());
        if(this.logHashCode == newHashCode) return false;
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
                LoggerHolder.LOG.warn("戦闘情報の更新に失敗しました", ex);
            }
        }

        // 陣形
        String fFormation = BattleTypes.Formation.toFormation(log.getBattle().getFormation().get(0)).toString();
        String eFormation = BattleTypes.Formation.toFormation(log.getBattle().getFormation().get(1)).toString();
        String combineType = log.getCombinedType().toString();

        // 艦隊
        ObservableList<Node> fleets = this.fleetList.getChildren();
        fleets.clear();
        SimpleBattleFleetPane f1 = null;
        SimpleBattleFleetPane f2 = null;
        SimpleBattleFleetPane e2 = null;
        SimpleBattleFleetPane e1 = null;
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);

        // - 味方1
        if(!ps.getAfterFriend().isEmpty())
        {
            f1 = new SimpleBattleFleetPane("自艦隊", fFormation, ps.getAfterFriend().stream().map(c -> c));
        }
        // - 味方2
        if(!ps.getAfterFriendCombined().isEmpty())
        {
            f2 = new SimpleBattleFleetPane("第二艦隊", combineType, ps.getAfterFriendCombined().stream().map(c -> c));
        }
        // - 敵2
        if(!ps.getAfterEnemyCombined().isEmpty())
        {
            e2 = new SimpleBattleFleetPane("敵随伴艦隊", "", ps.getAfterEnemyCombined().stream().map(c -> c));
        }
        // - 敵1
        if(!ps.getAfterEnemy().isEmpty())
        {
            e1 = new SimpleBattleFleetPane("敵艦隊", eFormation, ps.getAfterEnemy().stream().map(c -> c));
        }

        // 戦闘後艦隊
        ps.apply(log.getBattle());
        ps.apply(log.getMidnight());

        // - 味方1
        if(!ps.getAfterFriend().isEmpty() && f1 != null)
        {
            f1.applyAfterHp(ps.getAfterFriend().stream().map(c -> c));
            fleets.add(f1);
        }
        // - 味方2
        if(!ps.getAfterFriendCombined().isEmpty() && f2 != null)
        {
            f2.applyAfterHp(ps.getAfterFriendCombined().stream().map(c -> c));
            fleets.add(f2);
        }
        fleets.add(sep);
        // - 敵2
        if(!ps.getAfterEnemyCombined().isEmpty() && e2 != null)
        {
            e2.applyAfterHp(ps.getAfterEnemyCombined().stream().map(c -> c));
            fleets.add(e2);
        }
        // - 敵1
        if(!ps.getAfterEnemy().isEmpty() && e1 != null)
        {
            e1.applyAfterHp(ps.getAfterEnemy().stream().map(c -> c));
            fleets.add(e1);
        }

        // 情報
        // - マップ
        // - ボス
        MapStartNext last = log.getNext().size() > 0 ? log.getNext().get(log.getNext().size() - 1) : null;
        updateCell(last, true);
        // - 戦闘数
        if(log.getBattleCount() == null)
        {
            this.count.setText("");
        }
        else
        {
            this.count.setText(String.format("%d戦%s", log.getBattleCount(), log.getResult() == null ? "目" : "終了"));
        }
        // - 昼夜
        if(log.getMidnight() == null)
        {
            this.day.setText(log.getBattle().isINightToDayBattle() ? "夜昼" : "昼戦");
        }
        else
        {
            this.day.setText(log.getMidnight().isINightToDayBattle() ? "夜昼" : "夜戦");
        }
        // - 対峙
        this.intercept.setText(BattleTypes.Intercept.toIntercept(log.getBattle().getFormation().get(2)).toString());
        // - 制空
        this.dispSeiku.setText("");
        if (log.getBattle().isIKouku())
        {
            BattleTypes.Kouku kouku = log.getBattle().asIKouku().getKouku();
            if(kouku != null && kouku.getStage1() != null)
            {
                this.dispSeiku.setText(BattleTypes.DispSeiku.toDispSeiku(kouku.getStage1().getDispSeiku()).toString());
            }
        }
        // - ランク
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
                    LoggerHolder.LOG.warn("戦闘情報の更新に失敗しました", ex);
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
        this.logHashCode = newHashCode;
        return true;
    }

    private boolean updateCell(MapStartNext cell, boolean battling)
    {
        if(cell == null)
        {
            this.map.setText("");
            this.boss.setText("");
            return false;
        }
        else
        {
            long newHashCode = cell.hashCode();
            if(this.cellHashCode == newHashCode) return false;
            boolean boss = cell.getNo().equals(cell.getBosscellNo()) || cell.getEventId() == 5;
            this.map.setText(cell.getMapareaId() + "-" + cell.getMapinfoNo()
                    + "-" + Mapping.getCell(cell.getMapareaId(), cell.getMapinfoNo(), cell.getNo()));
            this.boss.setText(boss ? (battling ? "○" : "次") : "✕");
            this.cellHashCode = newHashCode;
            return true;
        }
    }

    @Override
    protected void onWindowHidden(WindowEvent e)
    {
        if(this.timeline != null)
        {
            this.timeline.stop();
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
