package main.java.helper;

import java.io.IOException;
import java.net.Socket;

public class SocketGuard implements Runnable {

    private Socket socket;
    private int timeout;
    private boolean active = true;

    public SocketGuard(Socket socket, int timeout) {
        this.socket = socket;
        this.timeout = timeout;
        Thread t = new Thread(this);
        t.start();
    }

    public void disable(){
        active = false;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (active) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
