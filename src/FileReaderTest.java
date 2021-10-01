/*
 * FileReaderTest.java
 * CS 490 Team 3 Fall 2021
 * Test file reading process
 */
package src;

//import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FileReaderTest {
    FileReader file = new FileReader();
    List<String> process = new ArrayList<>();
    //@Test
    public void TestReadFile()
    {
        file.ReadFile("InputFiles/test.txt");
    }

    //@Test
    public void TestGetProcessName()
    {
        process.add("0, process q, 10, 1");
        process.add("0, process j, 17, 1");
        process.add("0, process l, 4, 1");
        assert (file.getProcessName(process, 0).equals("process q"));
        process.clear();
    }

    //@Test
    public void TestGetServiceTime()
    {

        process.add("0, process q, 4, 1");
        process.add("0, process j, 17, 1");
        process.add("0, process l, 4, 1");
        assert (file.getServiceTime(process, 0).equals("4"));
        process.clear();
    }


}