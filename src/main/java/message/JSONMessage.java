package message;

import exceptions.InvalidMessageTypeException;

public interface JSONMessage
{
    enum MessageType
    {
        ERROR("error"),
        METHOD_CALL_REQUEST("methodCallRequest"),
        METHOD_CALL_RESPONSE("methodCallResponse");

        private final String mName;

        MessageType(String name) {
            mName = name;
        }

        @Override
        public String toString()
        {
            return mName;
        }

        public static MessageType fromString(String name)
          throws InvalidMessageTypeException
        {
            for (MessageType type : MessageType.values()) {
                if (type.toString().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            throw new InvalidMessageTypeException(name);
        }
    }

    MessageType getType();
    String getJSON();
}
