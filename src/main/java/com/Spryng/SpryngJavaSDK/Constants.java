package com.Spryng.SpryngJavaSDK;

/**
 * Extensible interface containing this library's constants
 */
public interface Constants
{
    /**
     * SDK Version.
     */
    String VERSION = "0.1";

    /**
     * HTTP Scheme to use.
     */
    String HTTP_SCHEME = "https";

    /**
     * The URL path separator.
     */
    String URL_PATH_SEPARATOR = "/";

    /**
     * Hostname for the API.
     */
    String API_HOST = "api.spryngsms.com";

    /**
     * URL endpoint for the API.
     */
    String API_PATH = URL_PATH_SEPARATOR + "api";

    /**
     * The URI used to send SMS messages.
     */
    String API_SEND_URI = "send.php";

    /**
     * The URI used to check the credit balance.
     */
    String API_BALANCE_URI = "check.php";

    /**
     * User agent with client information.
     */
    String USER_AGENT = "SpryngSDKJava/" + VERSION;

    /**
     * Encoding used for URL's
     */
    String URL_ENCODING = "UTF-8";

    /**
     * The maximum length a message body can be without enabling `allow_long`
     */
    public final int DEFAULT_MAX_BODY_LENGTH = 160;

    /**
     * The maximum length a message body can be, even when `allow_long` is enabled.
     */
    public final int MAX_BODY_LENGTH_WITH_ALLOW_LONG = 612;

    /**
     * The maximum length of the `reference` parameter.
     */
    public final int REFERENCE_MAX_LENGTH = 256;

    /**
     * The maximum length of the `sender` parameter with alphanumeric values.
     */
    public final int SENDER_ALPHANUMERIC_MAX_LENGTH = 11;
    
    /**
     * The maximum length of the `sender` parameter with only numeric values.
     */
    public final int SENDER_NUMERIC_MAX_LENGTH = 14;
}
