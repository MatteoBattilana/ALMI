package clientinformation;

public class NoInternetConnectionException extends Exception
{
    public NoInternetConnectionException()
    {
        super("No Internet connection!");
    }
}
