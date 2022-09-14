package model;

import java.util.Timer;

/**
 * Timer and related methods to be used for various methods which need a timer.  Can handle many TimerTasks with one thread.
 */
public class RCMTimer {

    /**
     * Timer object.
     */
    private static Timer timer = new Timer();

    public static Timer getTimer() {
        return timer;
    }

    public static void setTimer(Timer timer) {
        RCMTimer.timer = timer;
    }
}
