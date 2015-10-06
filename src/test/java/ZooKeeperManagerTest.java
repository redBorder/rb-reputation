import junit.framework.TestCase;
import net.redborder.malware.utils.ZkUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZooKeeperManagerTest extends TestCase {


    @Test
    public void connectCorrectly(){

        TestingServer zooKeeperServer;

        try {

            zooKeeperServer = new TestingServer();
            zooKeeperServer.start();

            CuratorFramework curatorClient = CuratorFrameworkFactory.newClient("localhost:"+zooKeeperServer.getPort(),new RetryNTimes(Integer.MAX_VALUE, 30000));

            assertNotNull(curatorClient);

            zooKeeperServer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createPathCorrectly(){
        TestingServer zooKeeperServer;

        try {

            zooKeeperServer = new TestingServer();
            zooKeeperServer.start();

            CuratorFramework curatorClient = CuratorFrameworkFactory.newClient("localhost:"+zooKeeperServer.getPort(),new RetryNTimes(Integer.MAX_VALUE, 30000));
            curatorClient.start();

            ZkUtils zkUtils = new ZkUtils(curatorClient, "/Proof");
            zkUtils.registerNode();

            assertNotNull(zkUtils);

            zkUtils.createNodeIfNotExist("node1");

            assertTrue(zkUtils.checkIfExist("/Proof/node1"));

            zooKeeperServer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
