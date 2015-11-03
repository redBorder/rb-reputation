import junit.framework.TestCase;
import net.redborder.malware.IncrementalList;
import net.redborder.malware.config.Config;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

@RunWith(MockitoJUnitRunner.class)

public class IncrementalListTest extends TestCase{

    @Before
    public void init(){
        IncrementalList.resetIncrementalList();

        Config.clearConfiguration();

        Config.addTime("malware.incrementallist.revision.time", "5S");
        Config.addTime("malware.incrementallist.incremental.time", "1S");

        IncrementalList.init(Config.createConfig());
    }

    @Test
    public void incrementalListTest(){

        try {
            IncrementalList.start();
            TimeUnit.SECONDS.sleep(10);
            IncrementalList.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Incremental : " + IncrementalList.getCurrent(IncrementalList.List.INCREMENTAL));
        System.out.println("Revision : " + IncrementalList.getCurrent(IncrementalList.List.REVISION));
    }
}
