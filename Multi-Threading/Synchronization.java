/**
 * This class contains the driver code which creates the threads and handles the output.
 *
 * @author Ayush Ranjan
 * @since 25th November 2017
 */
public class Synchronization {
    // the adder1 would sum numbers up to LIMIT
    public static final int LIMIT = 1000;
    // determines if the threads should be synchronised or not
    public static final boolean SYNCHRONIZE = false;
    // determines whether the program should output the thread id of the current thread being executed
    public static final boolean THREAD_ID_OUTPUT = false;
    // determines whether to run code with two or one adder objects
    public static final boolean RUN_WITH_TWO_ADDERS = false;
    // determines whether we should run the two Adders within the Thread objects within diff synchronization blocks
    public static final boolean SEPARATE_SYNC_BLOCKS = false;

    // NOTE : SYNCHRONIZE = false and THREAD_ID_OUTPUT = true does not give the expected output!
    // Because System.out.println() is a heavy IO operation, it automatically synchronises the two threads outputting

    public static void main(String[] args) {
        Adder adder1 = new Adder();
        Adder adder2 = new Adder();

        AddRunnable addRunnable1;
        AddRunnable addRunnable2;

        // create the Threads with the same Adder objects
        if (RUN_WITH_TWO_ADDERS) {
            addRunnable1 = new AddRunnable(adder1, adder2);
            addRunnable2 = new AddRunnable(adder1, adder2);
        } else {
            addRunnable1 = new AddRunnable(adder1);
            addRunnable2 = new AddRunnable(adder1);
        }

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
        System.out.print("Error in first adder : ");
        System.out.println(correct - adder1.getCount());
        if (RUN_WITH_TWO_ADDERS) {
            System.out.print("Error in second adder : ");
            System.out.println(correct - adder2.getCount());
        }
    }
}

/**
 * This object is used to construct thread objects since it extends Runnable interface. When this thread is started, it
 * reads numbers from a text file and keeps adding them to the Adder instance object.
 */
class AddRunnable extends Thread {
    private final Adder adder1;
    private final Adder adder2;

    /**
     * Constructor which initiates just one of the instance variables which holds the Adder object.
     *
     * @param adder1 Adder object with which we initiate our first Adder instance variable
     */
    public AddRunnable(Adder adder1) {
        this.adder1 = adder1;
        this.adder2 = null;
    }

    /**
     * Constructor which initiates both the instance variable which holds the Adder object.
     *
     * @param adder1 Adder object with which we initiate our first Adder instance variable
     * @param adder2 Adder object with which we initiate our second Adder instance variable
     */
    public AddRunnable(Adder adder1, Adder adder2) {
        this.adder1 = adder1;
        this.adder2 = adder2;
    }

    /**
     * Calls the addSumToLimit method inside the synchronized block if the SYNCHRONIZE flag is true else calls it
     * normally.
     */
    @Override
    public void run() {
        if (Synchronization.SYNCHRONIZE) {
            if (Synchronization.SEPARATE_SYNC_BLOCKS) {
                synchronized (adder1) {
                    addSumToLimit(adder1);
                }
                synchronized (adder2) {
                    addSumToLimit(adder2);
                }
            } else {
                synchronized (adder1) {
                    addSumToLimit(adder1);
                    addSumToLimit(adder2);
                }
            }
        } else {
            addSumToLimit(adder1);
            addSumToLimit(adder2);
        }
    }

    /**
     * Add the numbers from 0 to LIMIT to the adder1. Outputs the currently running thread id if the THREAD_ID_OUTPUT flag
     * is true. Hence we can see how the program switches between the threads.
     */
    private void addSumToLimit(Adder adder) {
      if (adder == null)
        return;

        for (int i = 0; i <= Synchronization.LIMIT; i++) {
            adder.add(i);
            if (Synchronization.THREAD_ID_OUTPUT)
                System.out.println(this.getId());
        }
    }
}

/**
 * The object is a simple adder1. It can add to itself and spit out its current value.
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
