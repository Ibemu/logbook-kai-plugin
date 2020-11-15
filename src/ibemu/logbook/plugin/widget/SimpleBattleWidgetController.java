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
import logbook.internal.*;
import logbook.internal.gui.BattleLogDetail;
import logbook.internal.gui.WindowController;
import logbook.internal.log.BattleResultLogFormat;
import logbook.internal.log.LogWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            Judge judge = new Judge();
            judge.setBefore(ps);

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
                judge.setAfter(ps, log.getBattle());
                this.rank.setText(String.valueOf(judge.getRank()).charAt(0) + "?");
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

    /**
     * 勝敗判定
     */
    private static class Judge {

        private double beforeFriendTotalHp;

        private int beforeFriendAliveCount;

        private double beforeEnemyTotalHp;

        private int beforeEnemyAliveCount;

        private double afterFriendTotalHp;

        private int afterFriendAliveCount;

        private double afterEnemyTotalHp;

        private int afterEnemyAliveCount;

        /** 味方損害率 */
        private double friendDamageRatio;

        /** 敵損害率 */
        private double enemyDamageRatio;

        /** 勝敗 */
        private Rank rank;

        public Rank getRank()
        {
            return this.rank;
        }

        /**
         * 戦闘前の状態を設定します。
         * @param ps フェイズ
         */
        public void setBefore(PhaseState ps) {
            this.beforeFriendTotalHp = ps.friendTotalHp();
            this.beforeFriendAliveCount = ps.friendAliveCount();
            this.beforeEnemyTotalHp = ps.enemyTotalHp();
            this.beforeEnemyAliveCount = ps.enemydAliveCount();
        }

        /**
         * 戦闘後の状態を設定します。
         * @param ps フェイズ
         */
        public void setAfter(PhaseState ps, BattleTypes.IFormation battle) {
            this.afterFriendTotalHp = ps.friendTotalHp();
            this.afterFriendAliveCount = ps.friendAliveCount();
            this.afterEnemyTotalHp = ps.enemyTotalHp();
            this.afterEnemyAliveCount = ps.enemydAliveCount();
            this.friendDamageRatio = this.damageRatio(this.beforeFriendTotalHp, this.afterFriendTotalHp);
            this.enemyDamageRatio = this.damageRatio(this.beforeEnemyTotalHp, this.afterEnemyTotalHp);
            this.rank = this.judge(ps, battle);
        }

        /**
         * 勝敗判定
         * @param ps フェイズ
         * @param battle 戦闘
         * @return ランク
         */
        private Rank judge(PhaseState ps, BattleTypes.IFormation battle) {
            if (battle.isILdAirbattle() || battle.isILdShooting()) {
                if (this.friendDamageRatio <= 0) {
                    return Rank.S完全勝利;
                }
                if (this.friendDamageRatio < 10) {
                    return Rank.A勝利;
                }
                if (this.friendDamageRatio < 20) {
                    return Rank.B戦術的勝利;
                }
                if (this.friendDamageRatio < 50) {
                    return Rank.C戦術的敗北;
                }
                if (this.friendDamageRatio < 80) {
                    return Rank.D敗北;
                }
                return Rank.E敗北;
            } else {
                if (this.beforeFriendAliveCount == this.afterFriendAliveCount) {
                    if (this.afterEnemyAliveCount == 0) {
                        if (this.afterFriendTotalHp >= this.beforeFriendTotalHp) {
                            return Rank.S完全勝利;
                        } else {
                            return Rank.S勝利;
                        }
                    } else if (this.beforeEnemyAliveCount > 1
                            && (this.beforeEnemyAliveCount
                            - this.afterEnemyAliveCount) >= (int) (this.beforeEnemyAliveCount * 0.7D)) {
                        return Rank.A勝利;
                    }
                }
                if (Ships.isLost(ps.getAfterEnemy().get(0))
                        && (this.beforeFriendAliveCount - this.afterFriendAliveCount) < (this.beforeEnemyAliveCount
                        - this.afterEnemyAliveCount)) {
                    return Rank.B戦術的勝利;
                }
                if (this.beforeFriendAliveCount == 1 && Ships.isBadlyDamage(ps.getAfterFriend().get(0))) {
                    return Rank.D敗北;
                }
                if (Math.floor(this.enemyDamageRatio) > 2.5 * Math.floor(this.friendDamageRatio)) {
                    return Rank.B戦術的勝利;
                }
                if (Math.floor(this.enemyDamageRatio) > 0.9 * Math.floor(this.friendDamageRatio)) {
                    return Rank.C戦術的敗北;
                }
                if (this.beforeFriendAliveCount > this.afterFriendAliveCount && this.afterFriendAliveCount == 1) {
                    return Rank.E敗北;
                }
                return Rank.D敗北;
            }
        }

        /**
         * 損害率を計算する
         * @param before 前HP
         * @param after 後HP
         * @return 損害率
         */
        private double damageRatio(double before, double after) {
            return (before - after) / before * 100;
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
