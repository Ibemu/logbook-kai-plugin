package ibemu.logbook.plugin.logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import logbook.internal.Config;
import logbook.internal.ThreadManager;
import logbook.internal.gui.WindowController;

public class PluginConfigController extends WindowController
{
    /**
     * /kcsapi/ のリクエストを保存する
     */
    @FXML
    private CheckBox storeKcsapiRequest;

    /**
     * /kcsapi/ のリクエストを保存する
     */
    @FXML
    private TextField kcsapiRequestPath;

    /**
     * /kcsapi/ のレスポンスを保存する
     */
    @FXML
    private CheckBox storeKcsapiResponse;

    /**
     * /kcsapi/ のレスポンスを保存する
     */
    @FXML
    private TextField kcsapiResponsePath;

    /**
     * /kcs2/ のレスポンスを保存する
     */
    @FXML
    private CheckBox storeKcs2Response;

    /**
     * /kcs2/ のレスポンスを保存する
     */
    @FXML
    private TextField kcs2ResponsePath;

    /**
     * /kcs2/ のQueryStringをファイル名に反映する
     */
    @FXML
    private CheckBox addKcs2QueryString;

    /**
     * /kcs2/ のQueryStringのapi_tokenを無視する
     */
    @FXML
    private CheckBox removeApiToken;

    @FXML
    void initialize()
    {
        PluginConfig conf = PluginConfig.get();

        this.storeKcsapiRequest.setSelected(conf.isStoreKcsapiRequest());
        this.kcsapiRequestPath.setText(conf.getKcsapiRequestPath());

        this.storeKcsapiResponse.setSelected(conf.isStoreKcsapiResponse());
        this.kcsapiResponsePath.setText(conf.getKcsapiResponsePath());

        this.storeKcs2Response.setSelected(conf.isStoreKcs2Response());
        this.kcs2ResponsePath.setText(conf.getKcs2ResponsePath());
        this.addKcs2QueryString.setSelected(conf.isAddKcs2QueryString());
        this.removeApiToken.setSelected(conf.isRemoveApiToken());
    }

    /**
     * キャンセル
     *
     * @param event ActionEvent
     */
    @FXML
    void cancel(ActionEvent event)
    {
        this.getWindow().close();
    }

    /**
     * 設定の反映
     *
     * @param event ActionEvent
     */
    @FXML
    void ok(ActionEvent event)
    {
        PluginConfig conf = PluginConfig.get();

        conf.setStoreKcsapiRequest(this.storeKcsapiRequest.isSelected());
        conf.setKcsapiRequestPath(this.kcsapiRequestPath.getText());

        conf.setStoreKcsapiResponse(this.storeKcsapiResponse.isSelected());
        conf.setKcsapiResponsePath(this.kcsapiResponsePath.getText());

        conf.setStoreKcs2Response(this.storeKcs2Response.isSelected());
        conf.setKcs2ResponsePath(this.kcs2ResponsePath.getText());
        conf.setAddKcs2QueryString(this.addKcs2QueryString.isSelected());
        conf.setRemoveApiToken(this.removeApiToken.isSelected());

        ThreadManager.getExecutorService()
                .execute(Config.getDefault()::store);
        this.getWindow().close();
    }
}
