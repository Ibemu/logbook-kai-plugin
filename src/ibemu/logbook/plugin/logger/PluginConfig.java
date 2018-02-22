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

    ///** /kcs/ のリクエストを保存する */
    //private boolean storeKcsRequest = false;

    ///** /kcs/ のリクエストを保存する */
    //private String kcsRequestPath = "./plugins/logger/kcsReq/";

    /** /kcs/ のレスポンスを保存する */
    private boolean storeKcsResponse = true;

    /** /kcs/ のレスポンスを保存する */
    private String kcsResponsePath = "./plugins/logger/kcs/";

    /** /kcs/ のQueryStringをファイル名に反映する */
    private boolean addKcsQueryString = true;

    /** /kcs/ のQueryStringのapi_tokenを無視する */
    private boolean removeApiToken = true;

    /** /kcs/ のリソースを展開して保存する */
    private boolean storeKcsResource = true;

    /** /kcs/ のリソースを展開して保存する */
    private String kcsResourcePath = "./plugins/logger/resource/";

    /** /kcs/ のQueryStringをディレクトリ名に反映する */
    private boolean addKcsResourceQueryString = true;

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

    ///**
    // * @return storeKcsRequest
    // */
    //public boolean isStoreKcsRequest()
    //{
    //    return storeKcsRequest;
    //}

    ///**
    // * @param storeKcsRequest セットする storeKcsRequest
    // */
    //public void setStoreKcsRequest(boolean storeKcsRequest)
    //{
    //    this.storeKcsRequest = storeKcsRequest;
    //}

    ///**
    // * @return kcsRequestPath
    // */
    //public String getKcsRequestPath()
    //{
    //    return kcsRequestPath;
    //}

    ///**
    // * @param kcsRequestPath セットする kcsRequestPath
    // */
    //public void setKcsRequestPath(String kcsRequestPath)
    //{
    //    this.kcsRequestPath = kcsRequestPath;
    //}

    /**
     * @return storeKcsResponse
     */
    public boolean isStoreKcsResponse()
    {
        return storeKcsResponse;
    }

    /**
     * @param storeKcsResponse セットする storeKcsResponse
     */
    public void setStoreKcsResponse(boolean storeKcsResponse)
    {
        this.storeKcsResponse = storeKcsResponse;
    }

    /**
     * @return kcsResponsePath
     */
    public String getKcsResponsePath()
    {
        return kcsResponsePath;
    }

    /**
     * @param kcsResponsePath セットする kcsResponsePath
     */
    public void setKcsResponsePath(String kcsResponsePath)
    {
        this.kcsResponsePath = kcsResponsePath;
    }

    /**
     * @return addKcsQueryString
     */
    public boolean isAddKcsQueryString()
    {
        return addKcsQueryString;
    }

    /**
     * @param addKcsQueryString セットする addKcsQueryString
     */
    public void setAddKcsQueryString(boolean addKcsQueryString)
    {
        this.addKcsQueryString = addKcsQueryString;
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
     * @return storeKcsResource
     */
    public boolean isStoreKcsResource()
    {
        return storeKcsResource;
    }

    /**
     * @param storeKcsResource セットする storeKcsResource
     */
    public void setStoreKcsResource(boolean storeKcsResource)
    {
        this.storeKcsResource = storeKcsResource;
    }

    /**
     * @return kcsResourcePath
     */
    public String getKcsResourcePath()
    {
        return kcsResourcePath;
    }

    /**
     * @param kcsResourcePath セットする kcsResourcePath
     */
    public void setKcsResourcePath(String kcsResourcePath)
    {
        this.kcsResourcePath = kcsResourcePath;
    }

    /**
     * @return addKcsResourceQueryString
     */
    public boolean isAddKcsResourceQueryString()
    {
        return addKcsResourceQueryString;
    }

    /**
     * @param addKcsResourceQueryString セットする addKcsResourceQueryString
     */
    public void setAddKcsResourceQueryString(boolean addKcsResourceQueryString)
    {
        this.addKcsResourceQueryString = addKcsResourceQueryString;
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
