import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@RunWith(MockitoJUnitRunner.class)

public class PriorityQueueTest extends TestCase {

    PriorityBlockingQueue<Service> queue;

    @Before
    public void init(){
        queue = new PriorityBlockingQueue<>(1000, new ServiceComparator());

        queue.offer(new Service(1,true));
        queue.offer(new Service(2,true));
        queue.offer(new Service(3,true));

    }

    @Test
    public void sizeTest(){
        Service refService = queue.peek();
        assertEquals(1, refService.getPriority());
        assertTrue(refService.isReady());
        assertEquals(3, queue.size());

//        System.out.println(queue.toString());
    }

    @Test
    public void priorityMap(){
        SortedMap<Integer, LinkedList<String>> map = new TreeMap<>();

        for(int i = 0; i < 5; i++){

            int randomPreference = new Random().nextInt(3) + 1;

            if(!map.containsKey(randomPreference)){
                map.put(randomPreference, new LinkedList<String>());
            }

            map.get(randomPreference).push("Value : " + new Random().nextInt(100));

        }

        System.out.println(map.toString());



    }

    @Test
    public void valueTest(){
        Service refService = queue.peek();
        assertEquals(1, refService.getPriority());
        assertTrue(refService.isReady());
    }

    private class Service {
        private int priority;
        private boolean ready;

        public Service(int priority, boolean ready){
            this.priority = priority;
            this.ready = ready;
        }

        public int getPriority(){
            return priority;
        }

        public boolean isReady(){
            return ready;
        }

        public String toString(){
            return "Priority : " + priority + " & Ready : " + (ready ? 1 : 0);
        }
    }

    private class ServiceComparator implements Comparator<Service> {

        @Override
        public int compare(Service o1, Service o2) {

//            System.out.println(o1.toString());
//            System.out.println(o2.toString());
            int priority = o1.getPriority() - o2.getPriority();
            int ready = (o1.isReady() ? 1 : 0) - (o2.isReady() ? 1 : 0);
//            System.out.println("Priority : " + priority);
//            System.out.println("Ready : " + ready);

            return o1.getPriority() - o2.getPriority();
        }

    }
}
