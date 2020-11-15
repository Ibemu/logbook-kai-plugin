package ibemu.logbook.plugin.widget;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logbook.bean.DeckPort;
import logbook.bean.DeckPortCollection;
import logbook.internal.gui.FleetTabPane;
import logbook.internal.gui.WindowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class FleetWidgetController extends WindowController
{
    @FXML
    private VBox widget;

    private Timeline timeline;

    private int index = -1;

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
        Map<Integer, DeckPort> ports = DeckPortCollection.get().getDeckPortMap();
        DeckPort port = ports.get(index);
        if(port != null)
        {
            if(this.widget.getChildren().isEmpty())
            {
                this.widget.getChildren().add(new FleetTabPane(port));
            }
            else
            {
                FleetTabPane pane = (FleetTabPane) this.widget.getChildren().get(0);
                pane.update(port);
                this.getWindow().setTitle(port.getName());
            }
        }
        else if(!this.widget.getChildren().isEmpty())
        {
            this.widget.getChildren().clear();
            this.getWindow().setTitle(String.format("第%d艦隊", this.index));
        }
    }

    public void setIndex(int index)
    {
        this.index = index;
        update(null);
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(FleetWidgetController.class);
    }
}
