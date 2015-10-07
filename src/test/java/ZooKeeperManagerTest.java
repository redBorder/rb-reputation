import junit.framework.TestCase;
import net.redborder.malware.MalwareController;
import net.redborder.malware.utils.ZkUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.UnknownHostException;

@RunWith(MockitoJUnitRunner.class)
public class ZooKeeperManagerTest extends TestCase {

    TestingServer zooKeeperServer;
    CuratorFramework curatorClient;

    @Before
    public void init() {
        try {
            zooKeeperServer = new TestingServer();
            zooKeeperServer.start();

            curatorClient = CuratorFrameworkFactory.newClient("localhost:" + zooKeeperServer.getPort(), new RetryNTimes(Integer.MAX_VALUE, 30000));
            curatorClient.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After
    public void shutdown() {
        try {
            zooKeeperServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectCorrectly() {
        assertNotNull(curatorClient);
    }

    @Test
    public void createPathCorrectly() {

        ZkUtils zkUtils = null;

        try {
            zkUtils = new ZkUtils(curatorClient, "/Proof");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        zkUtils.registerNode();

        assertNotNull(zkUtils);

        zkUtils.createNodeIfNotExist("node1");

        assertTrue(zkUtils.checkIfExist("/Proof/node1"));
    }

}
