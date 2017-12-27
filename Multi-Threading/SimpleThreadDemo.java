/**
 * This file contains the code which creates two different threads and demonstrates how the CPU switches between the
 * two threads.
 *
 * @author Ayush Ranjan
 * @since 27th December 2017
 */
public class SimpleThreadDemo {
    // number of times each thread would print its own ID
    public static final int NUM_PRINT = 100;
    // number of threads to create
    public static final int NUM_THREADS = 2;

    public static void main(String[] args) {
        // create NUM_THREADS threads
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new SimpleThread();
        }
        
        // run them all together
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].start();
        }
    }
}

/**
 * A simple Thread object which prints its own ID NUM_PRINT times while its running.
 */
class SimpleThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < SimpleThreadDemo.NUM_PRINT; i++) {
            System.out.println(this.getId());
        }
    }
}
