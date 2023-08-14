package com.dd.demo.demo.thread.fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Bryce_dd 2023/7/12 15:10
 */
public class FiberDemoTest {
    public static void main(String[] args) throws InterruptedException {
//        fiberDemo();
        threadDemo();
    }

    public static void fiberDemo() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(10000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(0, 10000).forEach(i -> new Fiber<String>() {
            private static final long serialVersionUID = 6279197702238357623L;

            @Override
            protected String run() throws SuspendExecution, InterruptedException {
                Strand.sleep(1000);
                count.countDown();
                return "done";
            }
        }.start());
        count.await();
        stopWatch.stop();
        System.out.println("fiberDemo: " + stopWatch.prettyPrint());
    }

    public static void threadDemo() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(10000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 10000).forEach(i -> executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
//                System.out.println("threadDemo" + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count.countDown();
        }));
        count.await();
        stopWatch.stop();
        System.out.println("threadDemo: " + stopWatch.prettyPrint());
    }
}
