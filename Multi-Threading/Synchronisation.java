/**
 * This class contains the driver code which creates the threads and handles the output.
 *
 * @author Ayush Ranjan
 * @since 25th November 2017
 */
public class Synchronisation {
    // the adder would sum numbers up to LIMIT
    public static final int LIMIT = 1000;
    // determines if the add method in Adder should be synchronised or not
    public static final boolean SYNCHRONIZE_ADDER = false;
    // determines if addSumToLimit method should be called with a synchronised block or no.
    public static final boolean SYNCHRONIZE_CALL_TO_METHOD = false;
    // determines whether the program should output the thread id of the current thread being executed
    public static final boolean THREAD_ID_OUTPUT = false;

    // NOTE : SYNCHRONIZE = false and THREAD_ID_OUTPUT = true does not give the expected output!
    // Because System.out.println() is a heavy IO operation, it automatically synchronises the two threads outputting

    public static void main(String[] args) {
        Adder adder;
        if (SYNCHRONIZE_ADDER)
            adder = new AdderSynchronised();
        else
            adder = new AdderNotSynchronised();

        AddRunnable addRunnable1;
        AddRunnable addRunnable2;

        addRunnable1 = new AddRunnable(adder);
        addRunnable2 = new AddRunnable(adder);

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
        System.out.println("Error : " + String.valueOf(correct - adder.getVal()));
    }
}

/**
 * This object represents a thread. It is initiated with an Adder object and when this thread is run it adds 0 to LIMIT
 * to the adder.
 */
class AddRunnable extends Thread {
    private final Adder adder;

    /**
     * Constructor which initiates just one of the instance variables which holds the Adder object.
     *
     * @param adder Adder object with which we initiate our first Adder instance variable
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
        if (Synchronisation.SYNCHRONIZE_CALL_TO_METHOD) {
            synchronized (adder) {
                addSumToLimit(adder);
            }
        } else
            addSumToLimit(adder);
    }

    /**
     * Add the numbers from 0 to LIMIT to the adder. Outputs the currently running thread id if the THREAD_ID_OUTPUT flag
     * is true. Hence we can see how the program switches between the threads.
     */
    private void addSumToLimit(Adder adder) {
        for (int i = 0; i <= Synchronisation.LIMIT; i++) {
            adder.add(i);
            if (Synchronisation.THREAD_ID_OUTPUT)
                System.out.println(this.getId());
        }
    }
}

/**
 * The object is a simple adder. It can add to itself and spit out its current value.
 */
abstract class Adder {
    protected int val; // default value upon initialisation is 0

    /**
     * Getter which tells its current value
     *
     * @return current value
     */
    public int getVal() {
        return val;
    }

    /**
     * Increments its own value
     *
     * @param inc value to add onto itself
     */
    public abstract void add(int inc);
}

/**
 * This class does not implement any synchronisation
 */
class AdderNotSynchronised extends Adder {
    @Override
    public void add(int inc) {
        val += inc;
    }
}

/**
 * This class makes the add method synchronised
 */
class AdderSynchronised extends Adder {
    @Override
    public synchronized void add(int inc) {
        val += inc;
    }
}
