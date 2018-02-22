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
    /** /kcsapi/ のリクエストを保存する */
    @FXML
    private CheckBox storeKcsapiRequest;

    /** /kcsapi/ のリクエストを保存する */
    @FXML
    private TextField kcsapiRequestPath;

    /** /kcsapi/ のレスポンスを保存する */
    @FXML
    private CheckBox storeKcsapiResponse;

    /** /kcsapi/ のレスポンスを保存する */
    @FXML
    private TextField kcsapiResponsePath;

    ///** /kcs/ のリクエストを保存する */
    //@FXML
    //private CheckBox storeKcsRequest;

    ///** /kcs/ のリクエストを保存する */
    //@FXML
    //private TextField kcsRequestPath;

    /** /kcs/ のレスポンスを保存する */
    @FXML
    private CheckBox storeKcsResponse;

    /** /kcs/ のレスポンスを保存する */
    @FXML
    private TextField kcsResponsePath;

    /** /kcs/ のQueryStringをファイル名に反映する */
    @FXML
    private CheckBox addKcsQueryString;

    /** /kcs/ のQueryStringのapi_tokenを無視する */
    @FXML
    private CheckBox removeApiToken;

    /** /kcs/ のリソースを展開して保存する */
    @FXML
    private CheckBox storeKcsResource;

    /** /kcs/ のリソースを展開して保存する */
    @FXML
    private TextField kcsResourcePath;

    /** /kcs/ のQueryStringをディレクトリ名に反映する */
    @FXML
    private CheckBox addKcsResourceQueryString;

    @FXML
    void initialize() {
        PluginConfig conf = PluginConfig.get();

        this.storeKcsapiRequest.setSelected(conf.isStoreKcsapiRequest());
        this.kcsapiRequestPath.setText(conf.getKcsapiRequestPath());

        this.storeKcsapiResponse.setSelected(conf.isStoreKcsapiResponse());
        this.kcsapiResponsePath.setText(conf.getKcsapiResponsePath());

        //this.storeKcsRequest.setSelected(conf.isStoreKcsRequest());
        //this.kcsRequestPath.setText(conf.getKcsRequestPath());

        this.storeKcsResponse.setSelected(conf.isStoreKcsResponse());
        this.kcsResponsePath.setText(conf.getKcsResponsePath());
        this.addKcsQueryString.setSelected(conf.isAddKcsQueryString());
        this.removeApiToken.setSelected(conf.isRemoveApiToken());

        this.storeKcsResource.setSelected(conf.isStoreKcsResource());
        this.kcsResourcePath.setText(conf.getKcsResourcePath());
        this.addKcsResourceQueryString.setSelected(conf.isAddKcsResourceQueryString());
    }

    /**
     * キャンセル
     *
     * @param event ActionEvent
     */
    @FXML
    void cancel(ActionEvent event) {
        this.getWindow().close();
    }

    /**
     * 設定の反映
     *
     * @param event ActionEvent
     */
    @FXML
    void ok(ActionEvent event) {
        PluginConfig conf = PluginConfig.get();

        conf.setStoreKcsapiRequest(this.storeKcsapiRequest.isSelected());
        conf.setKcsapiRequestPath(this.kcsapiRequestPath.getText());

        conf.setStoreKcsapiResponse(this.storeKcsapiResponse.isSelected());
        conf.setKcsapiResponsePath(this.kcsapiResponsePath.getText());

        //conf.setStoreKcsRequest(this.storeKcsRequest.isSelected());
        //conf.setKcsRequestPath(this.kcsRequestPath.getText());

        conf.setStoreKcsResponse(this.storeKcsResponse.isSelected());
        conf.setKcsResponsePath(this.kcsResponsePath.getText());
        conf.setAddKcsQueryString(this.addKcsQueryString.isSelected());
        conf.setRemoveApiToken(this.removeApiToken.isSelected());

        conf.setStoreKcsResource(this.storeKcsResource.isSelected());
        conf.setKcsResourcePath(this.kcsResourcePath.getText());
        conf.setAddKcsResourceQueryString(this.addKcsResourceQueryString.isSelected());

        ThreadManager.getExecutorService()
                .execute(Config.getDefault()::store);
        this.getWindow().close();
    }
}
