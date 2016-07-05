package tests;

import scripts.ServerAndClientInitializationScript;

import com.jcraft.jsch.JSchException;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Testing extends TestCase {

    @Test
    public void testTesting () throws JSchException, IOException {

        ServerAndClientInitializationScript script = new ServerAndClientInitializationScript("");
        List<String> list = new ArrayList<>();
        list.add("penis");
        list.add("va--j");
        list.add("anus");
        try {
            script.runScript(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}