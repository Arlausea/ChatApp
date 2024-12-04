package TCP;

public class ThreadTest extends java.lang.Thread {

    int counter = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(getName() + ":" + counter++);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new ThreadTest().start();
        new ThreadTest().start();
        new ThreadTest().start();
    }
}
