package ibemu.logbook.plugin.widget;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import logbook.bean.*;
import logbook.internal.Ships;
import logbook.internal.gui.WindowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class TimerWidgetController extends WindowController
{
    @FXML
    private VBox root;

    @FXML
    private FlowPane missionList;

    @FXML
    private FlowPane ndockList;

    private Timeline timeline;

    private long portHashCode;
    private long ndockHashCode;

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

    private TimePane generateMissionTimePane(DeckPort port)
    {
        // 0=未出撃, 1=遠征中, 2=遠征帰還, 3=遠征中止
        int state = port.getMission().get(0).intValue();
        // 遠征先ID
        int target = port.getMission().get(1).intValue();
        // 帰還時間
        long time = port.getMission().get(2);

        if(state == 0 || target == 0 || time == 0)
        {
            // 未出撃
            String title = port.getName();
            if(AppCondition.get().isCombinedFlag() && port.getId() == 2)
            {
                // 連合編成中
                title = Optional.of(AppCondition.get().getCombinedType())
                        .map(BattleTypes.CombinedType::toCombinedType)
                        .filter(type -> type != BattleTypes.CombinedType.未結成)
                        .map(BattleTypes.CombinedType::toString)
                        .orElse("<不明な連合艦隊タイプ>");
            }
            return new TimePane(title);
        }
        else
        {
            // 出撃(遠征中・遠征帰還・遠征中止)
            Optional<Mission> mission = Optional.ofNullable(MissionCollection.get()
                    .getMissionMap()
                    .get(target));
            // 遠征の最大時間
            java.time.Duration max = java.time.Duration.ofMinutes(mission.map(Mission::getTime).orElse(0));
            return new TimePane(port.getName() + " : " + mission.map(Mission::getName).orElse("不明"), max, time);
        }
    }

    private TimePane generateNdockTimePane(Ndock ndock)
    {
        Ship ship = ShipCollection.get()
                .getShipMap()
                .get(ndock.getShipId());
        return new TimePane(Ships.toName(ship), ndock.getCompleteTime());
    }

    void update(ActionEvent e)
    {
        updateMission();
        updateNdock();
    }

    private void updateMission()
    {
        Map<Integer, DeckPort> ports = DeckPortCollection.get().getDeckPortMap();
        long newHashCode = ports.hashCode();
        ObservableList<Node> mission = this.missionList.getChildren();
        if(this.portHashCode != newHashCode)
        {
            mission.clear();
            ports.values().stream()
                    .skip(1)
                    .map(this::generateMissionTimePane)
                    .forEach(mission::add);

            //
            this.root.setPrefWidth(this.root.getWidth());
            this.getWindow().sizeToScene();

            //
            this.portHashCode = newHashCode;
        }
        else
        {
            for(Node node : mission)
            {
                if(node instanceof TimePane)
                {
                    ((TimePane) node).update();
                }
            }
        }
    }

    private void updateNdock()
    {
        Map<Integer, Ndock> ndockMap = NdockCollection.get().getNdockMap();
        ObservableList<Node> ndock = this.ndockList.getChildren();
        long newHashCode = ndockMap.hashCode();
        if(this.ndockHashCode != newHashCode)
        {
            ndock.clear();
            ndockMap.values()
                    .stream()
                    .filter(n -> 1 < n.getCompleteTime())
                    .map(this::generateNdockTimePane)
                    .forEach(ndock::add);

            //
            this.root.setPrefWidth(this.root.getWidth());
            this.getWindow().sizeToScene();

            //
            this.ndockHashCode = newHashCode;
        }
        else
        {
            for(Node node : ndock)
            {
                if(node instanceof TimePane)
                {
                    ((TimePane) node).update();
                }
            }
        }
    }

    @Override
    public void setWindow(Stage window)
    {
        super.setWindow(window);
        window.setOnCloseRequest(e -> this.timeline.stop());
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(TimerWidgetController.class);
    }
}
