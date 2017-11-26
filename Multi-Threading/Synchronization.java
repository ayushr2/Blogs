/**
 * This class contains the driver code which creates the threads and handles the output.
 *
 * @author Ayush Ranjan
 * @since 25th November 2017
 */
public class Synchronization {
    // the adder would sum numbers up to LIMIT
    public static final int LIMIT = 1000;
    // determines if the threads should be synchronised or not
    public static final boolean SYNCHRONIZE = false;
    // determines whether the program should output the thread id of the current thread being executed
    public static final boolean THREAD_ID_OUTPUT = false;

    public static void main(String[] args) {
        Adder adder = new Adder();

        // create the Threads with the same Adder object
        AddRunnable addRunnable1 = new AddRunnable(adder);
        AddRunnable addRunnable2 = new AddRunnable(adder);

        // initiate threads and start them
        addRunnable1.start();
        addRunnable2.start();

        // wait for both the threads to finish execution
        while (addRunnable1.isAlive() || addRunnable2.isAlive()) {

        }

        // find the correct output
        int correct = 0;
        for (int i = 0; i <= LIMIT; i++) {
            correct += i;
        }
        correct *= 2; // because we are doing the same addition on the same object two times using two threads

        // output the difference between correct value and actual value. Output represents error
        System.out.println(correct - adder.getCount());
    }
}

/**
 * This object is used to construct thread objects since it extends Runnable interface. When this thread is started, it
 * reads numbers from a text file and keeps adding them to the Adder instance object.
 */
class AddRunnable extends Thread {
    private final Adder adder;

    /**
     * Constructor which initiates the instance variable which holds the Adder object.
     *
     * @param adder Adder object with which we initiate our instance variable
     */
    public AddRunnable(Adder adder) {
        this.adder = adder;
    }

    /**
     * Calls the addSumToLimit method inside the synchronized block if the SYNCHRONIZE flag is true else calls it
     * normally.
     */
    @Override
    public void run() {
        if (Synchronization.SYNCHRONIZE) {
            synchronized (adder) {
                addSumToLimit();
            }
        } else {
            addSumToLimit();
        }
    }

    /**
     * Add the numbers from 0 to LIMIT to the adder. Outputs the currently running thread id if the THREAD_ID_OUTPUT flag
     * is true. Hence we can see how the program switches between the threads.
     */
    private void addSumToLimit() {
        for (int i = 0; i <= Synchronization.LIMIT; i++) {
            if (Synchronization.THREAD_ID_OUTPUT)
                System.out.println(this.getId());
            adder.add(i);
        }
    }
}

/**
 * The object is a simple adder. It can add to itself and spit out its current value.
 */
class Adder {
    private int count; // default value upon initialisation is 0

    /**
     * Getter which tells its current value
     *
     * @return current value
     */
    public int getCount() {
        return count;
    }

    /**
     * Increments its own value
     *
     * @param inc value to add onto itself
     */
    public void add(int inc) {
        count += inc;
    }
}
