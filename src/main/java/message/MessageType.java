package message;

import utils.Constants;

public enum MessageType
{
    ERROR(Constants.MESSAGE_TYPE_ERROR),
    METHOD_CALL_REQUEST(Constants.MESSAGE_TYPE_METHOD_CALL_REQUEST),
    METHOD_CALL_RESPONSE(Constants.MESSAGE_TYPE_METHOD_CALL_RESPONSE),
    HANDSHAKE_REQUEST(Constants.MESSAGE_TYPE_HANDSHAKE_REQUEST),
    HANDSHAKE_RESPONSE(Constants.MESSAGE_TYPE_HANDSHAKE_RESPONSE),
    STUB_RESPONSE(Constants.MESSAGE_TYPE_STUB_RESPONSE);

    private final String mName;

    MessageType(String name)
    {
        mName = name;
    }

    @Override
    public String toString()
    {
        return mName;
    }
}
