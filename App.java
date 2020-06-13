import java.util.concurrent.Semaphore;

import controllers.*;

public class App extends Thread {

    public static void main(String[] args) {
        Semaphore semaCPU = new Semaphore(1);
        Semaphore semaSch = new Semaphore(1);

        Shell user = new Shell();
        user.start();

    }

}