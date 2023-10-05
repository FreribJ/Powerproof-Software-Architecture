package main.java.helper;

import java.io.PrintWriter;
import java.io.Writer;

public class RPCWriter extends PrintWriter {
    public RPCWriter(Writer out) {
        super(out);
    }

    @Override
    public void println(String s) {
//        System.out.println("Writing: " + s);
        super.println(s);
        super.flush();
    }

    @Override
    public void println(int i) {
//        System.out.println("Writing: " + i);
        super.println(i);
        super.flush();
    }

}
