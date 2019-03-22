package ibemu.logbook.plugin.logger;

import java.io.Serializable;

import logbook.internal.Config;

public class PluginConfig implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -6328701211417273922L;

    /** /kcsapi/ のリクエストを保存する */
    private boolean storeKcsapiRequest = false;

    /** /kcsapi/ のリクエストを保存する */
    private String kcsapiRequestPath = "./plugins/logger/kcsapiReq/";

    /** /kcsapi/ のレスポンスを保存する */
    private boolean storeKcsapiResponse = true;

    /** /kcsapi/ のレスポンスを保存する */
    private String kcsapiResponsePath = "./plugins/logger/kcsapi/";

    /** /kcs2/ のレスポンスを保存する */
    private boolean storeKcs2Response = true;

    /** /kcs2/ のレスポンスを保存する */
    private String kcs2ResponsePath = "./plugins/logger/kcs2/";

    /** /kcs2/ のQueryStringをファイル名に反映する */
    private boolean addKcs2QueryString = true;

    /** /kcs2/ のQueryStringのapi_tokenを無視する */
    private boolean removeApiToken = true;

    /**
     * @return storeKcsapiRequest
     */
    public boolean isStoreKcsapiRequest()
    {
        return storeKcsapiRequest;
    }

    /**
     * @param storeKcsapiRequest セットする storeKcsapiRequest
     */
    public void setStoreKcsapiRequest(boolean storeKcsapiRequest)
    {
        this.storeKcsapiRequest = storeKcsapiRequest;
    }

    /**
     * @return kcsapiRequestPath
     */
    public String getKcsapiRequestPath()
    {
        return kcsapiRequestPath;
    }

    /**
     * @param kcsapiRequestPath セットする kcsapiRequestPath
     */
    public void setKcsapiRequestPath(String kcsapiRequestPath)
    {
        this.kcsapiRequestPath = kcsapiRequestPath;
    }

    /**
     * @return storeKcsapiResponse
     */
    public boolean isStoreKcsapiResponse()
    {
        return storeKcsapiResponse;
    }

    /**
     * @param storeKcsapiResponse セットする storeKcsapiResponse
     */
    public void setStoreKcsapiResponse(boolean storeKcsapiResponse)
    {
        this.storeKcsapiResponse = storeKcsapiResponse;
    }

    /**
     * @return kcsapiResponsePath
     */
    public String getKcsapiResponsePath()
    {
        return kcsapiResponsePath;
    }

    /**
     * @param kcsapiResponsePath セットする kcsapiResponsePath
     */
    public void setKcsapiResponsePath(String kcsapiResponsePath)
    {
        this.kcsapiResponsePath = kcsapiResponsePath;
    }

    /**
     * @return storeKcs2Response
     */
    public boolean isStoreKcs2Response()
    {
        return storeKcs2Response;
    }

    /**
     * @param storeKcs2Response セットする storeKcs2Response
     */
    public void setStoreKcs2Response(boolean storeKcs2Response)
    {
        this.storeKcs2Response = storeKcs2Response;
    }

    /**
     * @return kcs2ResponsePath
     */
    public String getKcs2ResponsePath()
    {
        return kcs2ResponsePath;
    }

    /**
     * @param kcs2ResponsePath セットする kcs2ResponsePath
     */
    public void setKcs2ResponsePath(String kcs2ResponsePath)
    {
        this.kcs2ResponsePath = kcs2ResponsePath;
    }

    /**
     * @return addKcs2QueryString
     */
    public boolean isAddKcs2QueryString()
    {
        return addKcs2QueryString;
    }

    /**
     * @param addKcs2QueryString セットする addKcs2QueryString
     */
    public void setAddKcs2QueryString(boolean addKcs2QueryString)
    {
        this.addKcs2QueryString = addKcs2QueryString;
    }

    /**
     * @return removeApiToken
     */
    public boolean isRemoveApiToken()
    {
        return removeApiToken;
    }

    /**
     * @param removeApiToken セットする removeApiToken
     */
    public void setRemoveApiToken(boolean removeApiToken)
    {
        this.removeApiToken = removeApiToken;
    }

    /**
     * アプリケーションのデフォルト設定ディレクトリからアプリケーション設定を取得します、
     * これは次の記述と同等です
     * <blockquote>
     *     <code>Config.getDefault().get(AppConfig.class, AppConfig::new)</code>
     * </blockquote>
     *
     * @return アプリケーションの設定
     */
    public static PluginConfig get() {
        return Config.getDefault().get(PluginConfig.class, PluginConfig::new);
    }
}
