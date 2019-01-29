package clientinformation;

public interface Methods
{
    String getInformation();
    String getIpAddress() throws NoInternetConnectionException;
}
