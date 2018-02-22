package ibemu.logbook.plugin.quest;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import logbook.internal.gui.WindowController;
import logbook.plugin.PluginContainer;

public class QuestTableController extends WindowController
{
    /** 一覧 */
    @FXML
    private TableView<Quest> questTable;

    /** ID */
    @FXML
    private TableColumn<Quest, Integer> no;

    /** 状態 */
    @FXML
    private TableColumn<Quest, Integer> state;

    /** タイトル */
    @FXML
    private TableColumn<Quest, String> title;

    /** 内容 */
    @FXML
    private TableColumn<Quest, String> detail;

    /** 燃料 */
    @FXML
    private TableColumn<Quest, List<Integer>> fuel;

    /** 弾薬 */
    @FXML
    private TableColumn<Quest, List<Integer>> ammo;

    /** 鋼材 */
    @FXML
    private TableColumn<Quest, List<Integer>> metal;

    /** ボーキ */
    @FXML
    private TableColumn<Quest, List<Integer>> bauxite;

    /** 期限 */
    @FXML
    private TableColumn<Quest, Date> due;

    /** 出撃 */
    @FXML
    private TableColumn<Quest, Set<Date>> sortie;

    /** 戦闘勝利 */
    @FXML
    private TableColumn<Quest, Set<Date>> battleWin;

    /** 戦闘S */
    @FXML
    private TableColumn<Quest, Set<Date>> battleWinS;

    /** ボス到達 */
    @FXML
    private TableColumn<Quest, Set<Date>> bossArrive;

    /** ボス勝利 */
    @FXML
    private TableColumn<Quest, Set<Date>> bossWin;

    /** 1-4S */
    @FXML
    private TableColumn<Quest, Set<Date>> boss1_4WinS;

    /** 1-5A */
    @FXML
    private TableColumn<Quest, Set<Date>> boss1_5WinA;

    /** 南西 */
    @FXML
    private TableColumn<Quest, Set<Date>> boss2Win;

    /** 3-3+ */
    @FXML
    private TableColumn<Quest, Set<Date>> boss3_3pWin;

    /** 西方 */
    @FXML
    private TableColumn<Quest, Set<Date>> boss4Win;

    /** 4-4 */
    @FXML
    private TableColumn<Quest, Set<Date>> boss4_4Win;

    /** 5-2S */
    @FXML
    private TableColumn<Quest, Set<Date>> boss5_2WinS;

    /** 6-1S */
    @FXML
    private TableColumn<Quest, Set<Date>> boss6_1WinS;

    /** 補給艦 */
    @FXML
    private TableColumn<Quest, Set<Date>> defeatAP;

    /** 空母 */
    @FXML
    private TableColumn<Quest, Set<Date>> defeatCV;

    /** 潜水艦 */
    @FXML
    private TableColumn<Quest, Set<Date>> defeatSS;

    /** 演習 */
    @FXML
    private TableColumn<Quest, Set<Date>> practice;

    /** 演習勝利 */
    @FXML
    private TableColumn<Quest, Set<Date>> practiceWin;

    /** 遠征 */
    @FXML
    private TableColumn<Quest, Set<Date>> missionSuccess;

    /** 建造 */
    @FXML
    private TableColumn<Quest, Set<Date>> createShip;

    /** 開発 */
    @FXML
    private TableColumn<Quest, Set<Date>> createItem;

    /** 解体 */
    @FXML
    private TableColumn<Quest, Set<Date>> destroyShip;

    /** 廃棄 */
    @FXML
    private TableColumn<Quest, Set<Date>> destroyItem;

    /** 補給 */
    @FXML
    private TableColumn<Quest, Set<Date>> charge;

    /** 入渠 */
    @FXML
    private TableColumn<Quest, Set<Date>> repair;

    /** 改修 */
    @FXML
    private TableColumn<Quest, Set<Date>> powerUp;

    /** 一覧 */
    private ObservableList<Quest> quests = FXCollections.observableArrayList();

    private Timeline timeline;


    @FXML
    void initialize() {
        try {
            // カラムとオブジェクトのバインド
            this.no.setCellValueFactory(new PropertyValueFactory<>("no"));
            this.state.setCellValueFactory(new PropertyValueFactory<>("state"));
            this.state.setCellFactory(p -> new StateCell());
            this.title.setCellValueFactory(new PropertyValueFactory<>("title"));
            this.detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
            this.fuel.setCellValueFactory(new PropertyValueFactory<>("material"));
            this.fuel.setCellFactory(p -> new MaterialCell(0));
            this.ammo.setCellValueFactory(new PropertyValueFactory<>("material"));
            this.ammo.setCellFactory(p -> new MaterialCell(1));
            this.metal.setCellValueFactory(new PropertyValueFactory<>("material"));
            this.metal.setCellFactory(p -> new MaterialCell(2));
            this.bauxite.setCellValueFactory(new PropertyValueFactory<>("material"));
            this.bauxite.setCellFactory(p -> new MaterialCell(3));
            this.due.setCellValueFactory(new PropertyValueFactory<>("due"));
            this.due.setCellFactory(p -> new DateCell());

            this.sortie.setCellValueFactory(new PropertyValueFactory<>("sortie"));
            this.sortie.setCellFactory(p -> new SetCountCell());
            this.bossArrive.setCellValueFactory(new PropertyValueFactory<>("bossArrive"));
            this.bossArrive.setCellFactory(p -> new SetCountCell());
            this.bossWin.setCellValueFactory(new PropertyValueFactory<>("bossWin"));
            this.bossWin.setCellFactory(p -> new SetCountCell());
            this.boss1_4WinS.setCellValueFactory(new PropertyValueFactory<>("boss1_4WinS"));
            this.boss1_4WinS.setCellFactory(p -> new SetCountCell());
            this.boss1_5WinA.setCellValueFactory(new PropertyValueFactory<>("boss1_5WinA"));
            this.boss1_5WinA.setCellFactory(p -> new SetCountCell());
            this.boss2Win.setCellValueFactory(new PropertyValueFactory<>("boss2Win"));
            this.boss2Win.setCellFactory(p -> new SetCountCell());
            this.boss3_3pWin.setCellValueFactory(new PropertyValueFactory<>("boss3_3pWin"));
            this.boss3_3pWin.setCellFactory(p -> new SetCountCell());
            this.boss4Win.setCellValueFactory(new PropertyValueFactory<>("boss4Win"));
            this.boss4Win.setCellFactory(p -> new SetCountCell());
            this.boss4_4Win.setCellValueFactory(new PropertyValueFactory<>("boss4_4Win"));
            this.boss4_4Win.setCellFactory(p -> new SetCountCell());
            this.boss5_2WinS.setCellValueFactory(new PropertyValueFactory<>("boss5_2WinS"));
            this.boss5_2WinS.setCellFactory(p -> new SetCountCell());
            this.boss6_1WinS.setCellValueFactory(new PropertyValueFactory<>("boss6_1WinS"));
            this.boss6_1WinS.setCellFactory(p -> new SetCountCell());
            this.battleWin.setCellValueFactory(new PropertyValueFactory<>("battleWin"));
            this.battleWin.setCellFactory(p -> new SetCountCell());
            this.battleWinS.setCellValueFactory(new PropertyValueFactory<>("battleWinS"));
            this.battleWinS.setCellFactory(p -> new SetCountCell());
            this.defeatAP.setCellValueFactory(new PropertyValueFactory<>("defeatAP"));
            this.defeatAP.setCellFactory(p -> new SetCountCell());
            this.defeatCV.setCellValueFactory(new PropertyValueFactory<>("defeatCV"));
            this.defeatCV.setCellFactory(p -> new SetCountCell());
            this.defeatSS.setCellValueFactory(new PropertyValueFactory<>("defeatSS"));
            this.defeatSS.setCellFactory(p -> new SetCountCell());
            this.practice.setCellValueFactory(new PropertyValueFactory<>("practice"));
            this.practice.setCellFactory(p -> new SetCountCell());
            this.practiceWin.setCellValueFactory(new PropertyValueFactory<>("practiceWin"));
            this.practiceWin.setCellFactory(p -> new SetCountCell());
            this.missionSuccess.setCellValueFactory(new PropertyValueFactory<>("missionSuccess"));
            this.missionSuccess.setCellFactory(p -> new SetCountCell());
            this.createShip.setCellValueFactory(new PropertyValueFactory<>("createShip"));
            this.createShip.setCellFactory(p -> new SetCountCell());
            this.createItem.setCellValueFactory(new PropertyValueFactory<>("createItem"));
            this.createItem.setCellFactory(p -> new SetCountCell());
            this.destroyShip.setCellValueFactory(new PropertyValueFactory<>("destroyShip"));
            this.destroyShip.setCellFactory(p -> new SetCountCell());
            this.destroyItem.setCellValueFactory(new PropertyValueFactory<>("destroyItem"));
            this.destroyItem.setCellFactory(p -> new SetCountCell());
            this.charge.setCellValueFactory(new PropertyValueFactory<>("charge"));
            this.charge.setCellFactory(p -> new SetCountCell());
            this.repair.setCellValueFactory(new PropertyValueFactory<>("repair"));
            this.repair.setCellFactory(p -> new SetCountCell());
            this.powerUp.setCellValueFactory(new PropertyValueFactory<>("powerUp"));
            this.powerUp.setCellFactory(p -> new SetCountCell());
            setColmnVisible();

            this.questTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            // 行を作る
            List<Quest> items = QuestCollection.get()
                    .getQuestMap()
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(Quest::getNo))
                    .sorted(Comparator.comparing(Quest::getState).reversed())
                    .collect(Collectors.toList());
            this.quests.addAll(items);
            this.questTable.setItems(this.quests);

            this.timeline = new Timeline();
            this.timeline.setCycleCount(Timeline.INDEFINITE);
            this.timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(1),
                    this::update));
            this.timeline.play();

        } catch (Exception e) {
            LoggerHolder.LOG.error("FXMLの初期化に失敗しました", e);
        }
    }

    void update(ActionEvent e) {
        List<Quest> items = QuestCollection.get()
                .getQuestMap()
                .values()
                .stream()
                .sorted(Comparator.comparing(Quest::getNo))
                .sorted(Comparator.comparing(Quest::getState).reversed())
                .collect(Collectors.toList());
        this.quests.setAll(items);
    }

    void setColmnVisible() {
        QuestTableConfig conf = QuestTableConfig.get();
        this.no.setVisible(conf.isNo());
//        this.category.setVisible(conf.isCategory());
//        this.type.setVisible(conf.isType());
        this.state.setVisible(conf.isState());
        this.title.setVisible(conf.isTitle());
        this.detail.setVisible(conf.isDetail());
        this.fuel.setVisible(conf.isFuel());
        this.ammo.setVisible(conf.isAmmo());
        this.metal.setVisible(conf.isMetal());
        this.bauxite.setVisible(conf.isBauxite());
//        this.bonusFlag.setVisible(conf.isBonusFlag());
//        this.progressFlag.setVisible(conf.isProgressFlag());
        this.due.setVisible(conf.isDue());
        this.sortie.setVisible(conf.isSortie());
        this.bossArrive.setVisible(conf.isBossArrive());
        this.bossWin.setVisible(conf.isBossWin());
        this.boss1_4WinS.setVisible(conf.isBoss1_4WinS());
        this.boss1_5WinA.setVisible(conf.isBoss1_5WinA());
        this.boss2Win.setVisible(conf.isBoss2Win());
        this.boss3_3pWin.setVisible(conf.isBoss3_3pWin());
        this.boss4Win.setVisible(conf.isBoss4Win());
        this.boss4_4Win.setVisible(conf.isBoss4_4Win());
        this.boss5_2WinS.setVisible(conf.isBoss5_2WinS());
        this.boss6_1WinS.setVisible(conf.isBoss6_1WinS());
        this.battleWin.setVisible(conf.isBattleWin());
        this.battleWinS.setVisible(conf.isBattleWinS());
        this.defeatAP.setVisible(conf.isDefeatAP());
        this.defeatCV.setVisible(conf.isDefeatCV());
        this.defeatSS.setVisible(conf.isDefeatSS());
        this.practice.setVisible(conf.isPractice());
        this.practiceWin.setVisible(conf.isPracticeWin());
        this.missionSuccess.setVisible(conf.isMissionSuccess());
        this.createShip.setVisible(conf.isCreateShip());
        this.createItem.setVisible(conf.isCreateItem());
        this.destroyShip.setVisible(conf.isDestroyShip());
        this.destroyItem.setVisible(conf.isDestroyItem());
        this.charge.setVisible(conf.isCharge());
        this.repair.setVisible(conf.isRepair());
        this.powerUp.setVisible(conf.isPowerUp());
    }

    @Override
    public void setWindow(Stage window) {
        super.setWindow(window);
        window.setOnCloseRequest(e -> this.timeline.stop());
    }

    /**
     * 列の表示・非表示
     *
     * @param event ActionEvent
     */
    @FXML
    void selectColumn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(PluginContainer.getInstance().getClassLoader().getResource("ibemu/logbook/plugin/quest/QuestTableConfig.fxml"));
            loader.setClassLoader(this.getClass().getClassLoader());
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            WindowController controller = loader.getController();
            controller.setWindow(stage);

            stage.initOwner(this.getWindow());
            stage.setTitle("列の表示");
            stage.showAndWait();
            setColmnVisible();
        }
        catch (Exception ex)
        {
            LoggerHolder.LOG.error("設定の初期化に失敗しました", ex);
        }
    }

    /**
     * 状態を表示するセル
     */
    private static class StateCell extends TableCell<Quest, Integer> {
        @Override
        protected void updateItem(Integer state, boolean empty) {
            super.updateItem(state, empty);
            if (!empty) {
                switch(state) {
                case 2:
                    this.setText("遂行中");
                    break;
                case 3:
                    this.setText("達成");
                    break;
                default:
                    this.setText("");
                    break;
                }
            } else {
                this.setText(null);
            }
        }
    }

    /**
     * 日付を表示するセル
     */
    private static class DateCell extends TableCell<Quest, Date> {
        private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        @Override
        protected void updateItem(Date date, boolean empty) {
            super.updateItem(date, empty);
            if (!empty && date != null) {
                this.setText(FORMAT.format(date));
            } else {
                this.setText(null);
            }
        }
    }

    /**
     * Materialの項目を表示するセル
     */
    private static class MaterialCell extends TableCell<Quest, List<Integer>> {
        private int index;

        public MaterialCell(int i) {
            super();
            this.index = i;
        }

        @Override
        protected void updateItem(List<Integer> list, boolean empty) {
            super.updateItem(list, empty);
            if (!empty) {
                this.setText(String.valueOf(list.get(index)));
            } else {
                this.setText(null);
            }
        }
    }

    /**
     * 達成回数を表示するセル
     */
    private static class SetCountCell extends TableCell<Quest, Set<Date>> {
        @Override
        protected void updateItem(Set<Date> set, boolean empty) {
            super.updateItem(set, empty);
            if (!empty && set != null) {
                this.setText(String.valueOf(set.size()));
            } else {
                this.setText(null);
            }
        }
    }

    private static class LoggerHolder {
        /** ロガー */
        private static final Logger LOG = LogManager.getLogger(QuestTableController.class);
    }
}
