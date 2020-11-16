package ibemu.logbook.plugin.jsonviewer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import logbook.internal.gui.WindowController;

public class JsonViewerController extends WindowController
{
    /**
     * 履歴
     */
    @FXML
    private ListView<ApiData> history;

    /**
     * 自動更新
     */
    @FXML
    private CheckBox auto;

    /**
     * タイムスタンプ
     */
    @FXML
    private TextField timestamp;

    /**
     * APIのURI
     */
    @FXML
    private TextField uri;

    /**
     * APIのリクエスト
     */
    @FXML
    private TextArea request;

    /**
     * APIのレスポンス
     */
    @FXML
    private TextArea response;

    /**
     * APIのレスポンス
     */
    @FXML
    private TreeView<NamedJsonValue> jsonTree;

    private ApiData last;
    private Timeline timeline;

    @FXML
    void initialize()
    {
        history.setCellFactory(lv -> new ListCell<ApiData>()
        {
            @Override
            protected void updateItem(ApiData item, boolean empty)
            {
                super.updateItem(item, empty);
                if(item == null || empty)
                    setText("");
                else
                    setText(item.toString());
            }
        });

        ObservableList<ApiData> list = ApiData.getApiList();
        history.setItems(list);
        if(!list.isEmpty())
        {
            last = list.get(0);
            history.getSelectionModel().selectFirst();
        }
        else last = null;
        /*
        list.addListener((ListChangeListener.Change<? extends ApiData> c) ->
        {
            if(auto.isSelected())
            {
                history.getSelectionModel().selectFirst();
            }
        });
        */

        timestamp.textProperty().bind(Bindings.selectString(history.getSelectionModel().selectedItemProperty(), "timestamp"));
        uri.textProperty().bind(Bindings.selectString(history.getSelectionModel().selectedItemProperty(), "uri"));
        request.textProperty().bind(Bindings.selectString(history.getSelectionModel().selectedItemProperty(), "request"));
        response.textProperty().bind(Bindings.selectString(history.getSelectionModel().selectedItemProperty(), "response"));
        jsonTree.rootProperty().bind(Bindings.select(history.getSelectionModel().selectedItemProperty(), "json"));

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(100),
                this::update));
        timeline.play();
    }

    void update(ActionEvent e)
    {
        ObservableList<ApiData> list = ApiData.getApiList();
        if(auto.isSelected() && (!list.isEmpty()) && list.get(0) != last)
        {
            last = list.get(0);
            history.getSelectionModel().selectFirst();
        }
    }
}
