package ru.fizteh.fivt.students.riskingh.Threads;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by max on 19/12/15.
 */

public class BlockingQueueTest {

    @Test
    public void testWithThreads() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<Integer>(100);
        TakeThread thread1 = new TakeThread(1, 10, 2, queue);
        PutThread thread2 = new PutThread(10, 1, 1, queue);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static class PutThread extends Thread {
        private int amount, range;
        private double sleepInterval;
        private BlockingQueue<Integer> queue;
        PutThread(int range, int amount, double sleepInterval, BlockingQueue<Integer> queue) {
            this.range = range;
            this.amount = amount;
            this.sleepInterval = sleepInterval;
            this.queue = queue;
        }

        @Override
        public void run() {
            int currentToPut = 0;
            for (int i = 0; i < amount; i++) {
                try {
                    sleep((int)sleepInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    ArrayList<Integer> list = new ArrayList<Integer>(range);
                    for (int j = 0; j < range; ++j) {
                        list.add(currentToPut++);
                    }
                    queue.offer(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class TakeThread extends Thread {
        private int amount, range;
        private double sleepInterval;
        private BlockingQueue<Integer> queue;
        TakeThread(int range, int amount, double sleepInterval, BlockingQueue<Integer> queue) {
            this.range = range;
            this.amount = amount;
            this.sleepInterval = sleepInterval;
            this.queue = queue;
        }

        @Override
        public void run() {
            Integer last = 0;
            int currentToTake = 0;
            for (int i = 0; i < amount; ++i) {
                try {
                    sleep((int)(sleepInterval));
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
                try {
                    List list = queue.take(range);
                    List goodList = new ArrayList<>();
                    for (int j = 0; j < range; ++j) {
                        goodList.add(currentToTake++);
                    }
                    Assert.assertEquals(list, goodList);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}