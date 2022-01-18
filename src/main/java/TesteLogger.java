import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TesteLogger {

    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        LOG.debug("THIS IS A DEBUG MESSAGE");
    }
}
