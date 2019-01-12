import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import org.junit.Before;

public class TestBase {
    protected Injector injector = Guice.createInjector(new AlmiModules());

    @Before
    public void setup () {
        injector.injectMembers(this);
    }
}