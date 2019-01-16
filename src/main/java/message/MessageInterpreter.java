package message;

import message.request.HandshakeRequest;
import message.response.HandshakeResponse;

public interface MessageInterpreter<T>
{
    T interpret(HandshakeRequest handshakeRequest);
    T interpret(HandshakeResponse handshakeResponse);
}
