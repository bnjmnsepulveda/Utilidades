package thread;


/**
 *
 * @author Roberto
 */
public abstract class NodoRunnable implements Runnable {

    private boolean arranque;
    private Thread thread;
    private long timeout;

    public abstract void beforeRun();
    public abstract void runThread();
    public abstract void afterRun();
    
    @Override
    public void run() {
        beforeRun();
        while (arranque) {
            runThread();
            sleep(timeout);
        }
        afterRun();
    }

    public void iniciar() {
        arranque = true;
        thread = new Thread(this);
        thread.start();
    }

    public void detener() {
        arranque = false;
    }

    public boolean estado() {
        return arranque;
    }

    public Thread getThread() {
        return thread;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    
    protected static void sleep(long espera) {
        try {
            Thread.sleep(espera);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
