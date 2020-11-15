package ibemu.logbook.plugin.widget;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.Duration;
import logbook.bean.DeckPort;
import logbook.bean.DeckPortCollection;
import logbook.internal.gui.FleetTabPane;
import logbook.internal.gui.InternalFXMLLoader;
import logbook.internal.gui.WindowController;
import logbook.plugin.PluginContainer;
import logbook.plugin.PluginServices;
import logbook.plugin.gui.MainCommandMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class FleetWidgetMenuItem implements MainCommandMenu
{
    @Override
    public MenuItem getContent()
    {
        Menu content = new Menu()
        {
            private Timeline timeline;
            private long portHashCode;

            {
                try
                {
                    update(null);
                    this.timeline = new Timeline();
                    this.timeline.setCycleCount(Timeline.INDEFINITE);
                    this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::update));
                    this.timeline.play();
                }
                catch(Exception e)
                {
                    FleetWidgetMenuItem.LoggerHolder.LOG.error("FXMLの初期化に失敗しました", e);
                }
            }

            private MenuItem generateMenuItem(Map.Entry<Integer, DeckPort> port)
            {
                MenuItem item = new MenuItem();
                item.setText(port.getValue().getName());
                item.setOnAction(e ->
                {
                    try
                    {
                        FXMLLoader loader = new FXMLLoader(PluginContainer.getInstance().getClassLoader().getResource("ibemu/logbook/plugin/widget/FleetWidget.fxml"));
                        loader.setClassLoader(this.getClass().getClassLoader());
                        Stage stage = new Stage();
                        Parent root = loader.load();
                        root.getStylesheets().add(PluginServices.getResource("logbook/gui/application.css").toString());
                        root.getStylesheets().add(PluginServices.getResource("logbook/gui/main.css").toString());
                        InternalFXMLLoader.setGlobal(root);
                        stage.setScene(new Scene(root));

                        FleetWidgetController controller = loader.getController();
                        controller.setWindow(stage);
                        controller.setIndex(port.getKey());

                        stage.initOwner(((MenuItem) e.getSource()).getParentMenu().getParentPopup().getOwnerWindow());
                        stage.setTitle(port.getValue().getName());
                        stage.show();
                    }
                    catch(Exception ex)
                    {
                        FleetWidgetMenuItem.LoggerHolder.LOG.error("設定の初期化に失敗しました", ex);
                    }
                });
                return item;
            }

            void update(ActionEvent e)
            {
                Map<Integer, DeckPort> ports = DeckPortCollection.get().getDeckPortMap();
                long newHashCode = ports.hashCode();
                if(this.portHashCode != newHashCode)
                {
                    this.getItems().clear();
                    ports.entrySet().stream()
                            .map(this::generateMenuItem)
                            .forEach(this.getItems()::add);
                    this.portHashCode = newHashCode;
                }
            }
        };
        content.setText("艦隊タブウィンドウ");
        return content;
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(FleetWidgetMenuItem.class);
    }
}
