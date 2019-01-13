package exceptions;

public class HandshakeException extends AlmiException
{
    public HandshakeException()
    {
        super(getErrorMessage());
    }

    private static String getErrorMessage()
    {
        return "Handshake exception!";
    }
}
