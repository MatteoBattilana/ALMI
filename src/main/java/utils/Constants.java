package utils;

public class Constants
{
    public static final int MESSAGE_PORT = 11111;
    public static final int STREAM_PORT  = 11112;

    public static final String SOCKET_SERVICE_NAME = "socket-service";

    public static final String MESSAGE_TYPE_ERROR                = "error";
    public static final String MESSAGE_TYPE_METHOD_CALL_REQUEST  = "methodCallRequest";
    public static final String MESSAGE_TYPE_METHOD_CALL_RESPONSE = "methodCallResponse";
    public static final String MESSAGE_TYPE_HANDSHAKE_REQUEST    = "handshakeRequest";
    public static final String MESSAGE_TYPE_HANDSHAKE_RESPONSE   = "handshakeResponse";
    public static final String MESSAGE_TYPE_STUB_RESPONSE        = "stubResponse";
    public static final String MESSAGE_TYPE_STREAM_REQUEST       = "streamRequest";

    public static final String JSON_MESSAGE_ID   = "messageId";
    public static final String JSON_MESSAGE_TYPE = "messageType";
    public static final String JSON_EXCEPTION    = "exception";
    public static final String JSON_METHOD_NAME  = "methodName";
    public static final String JSON_PARAMETERS   = "parameters";
    public static final String JSON_RETURN_VALUE = "returnValue";
}
