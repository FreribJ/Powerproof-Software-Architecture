package main.java.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class RPCReader extends BufferedReader {
    public RPCReader(Reader in) {
        super(in);
    }

    @Override
    public String readLine() throws IOException {
        String message = super.readLine();
        System.out.println("Reading: " + message);
        return message;
    }
}
