package message;

import utils.Constants;

public enum MessageType
{
    ERROR(Constants.MESSAGE_TYPE_ERROR),
    METHOD_CALL_REQUEST(Constants.MESSAGE_TYPE_METHOD_CALL_REQUEST),
    METHOD_CALL_RESPONSE(Constants.MESSAGE_TYPE_METHOD_CALL_RESPONSE),
    METHOD_WELCOME_REQUEST(Constants.MESSAGE_TYPE_WELCOME_REQUEST),
    METHOD_WELCOME_RESPONSE(Constants.MESSAGE_TYPE_WELCOME_RESPONSE);

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
