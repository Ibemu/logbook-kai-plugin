package ibemu.logbook.plugin.widget;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import logbook.internal.gui.InternalFXMLLoader;
import logbook.internal.gui.WindowController;
import logbook.plugin.PluginContainer;
import logbook.plugin.PluginServices;
import logbook.plugin.gui.MainCommandMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerWidgetMenuItem implements MainCommandMenu
{
    @Override
    public MenuItem getContent()
    {
        MenuItem content = new MenuItem();
        content.setText("遠征入渠タイマー");
        content.setOnAction(e ->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(PluginContainer.getInstance().getClassLoader().getResource("ibemu/logbook/plugin/widget/TimerWidget.fxml"));
                loader.setClassLoader(this.getClass().getClassLoader());
                Stage stage = new Stage();
                Parent root = loader.load();
                root.getStylesheets().add(PluginServices.getResource("logbook/gui/application.css").toString());
                root.getStylesheets().add(PluginServices.getResource("logbook/gui/main.css").toString());
                InternalFXMLLoader.setGlobal(root);
                stage.setScene(new Scene(root));

                WindowController controller = loader.getController();
                controller.setWindow(stage);

                stage.initOwner(((MenuItem) e.getSource()).getParentPopup().getOwnerWindow());
                stage.setTitle("遠征入渠タイマー");
                stage.show();
            }
            catch(Exception ex)
            {
                LoggerHolder.LOG.error("設定の初期化に失敗しました", ex);
            }
        });
        return content;
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(TimerWidgetMenuItem.class);
    }
}
