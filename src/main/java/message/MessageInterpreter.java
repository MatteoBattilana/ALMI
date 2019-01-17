package message;

public interface MessageInterpreter<T>
{
    T interpret(MethodCallResponse methodCallResponse);
    T interpret(MethodCallRequest methodCallRequest);
    T interpret(ErrorMessage errorMessage);
}
