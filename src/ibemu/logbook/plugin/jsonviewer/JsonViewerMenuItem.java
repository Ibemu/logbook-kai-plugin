package ibemu.logbook.plugin.jsonviewer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import logbook.internal.gui.WindowController;
import logbook.plugin.PluginContainer;
import logbook.plugin.gui.MainExtMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonViewerMenuItem implements MainExtMenu
{

    @Override
    public MenuItem getContent()
    {
        MenuItem content = new MenuItem();
        content.setText("JSONビューア");
        content.setOnAction(e ->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(PluginContainer.getInstance().getClassLoader().getResource("ibemu/logbook/plugin/jsonviewer/JsonViewer.fxml"));
                loader.setClassLoader(this.getClass().getClassLoader());
                Stage stage = new Stage();
                Parent root = loader.load();
                stage.setScene(new Scene(root));

                WindowController controller = loader.getController();
                controller.setWindow(stage);

                stage.initOwner(((MenuItem) e.getSource()).getParentPopup().getOwnerWindow());
                stage.setTitle("JSONビューア");
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
        private static final Logger LOG = LogManager.getLogger(JsonViewerMenuItem.class);
    }
}
