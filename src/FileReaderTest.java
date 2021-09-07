package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    @Test
    public void TestReadFile()
    {
        FileReader file = new FileReader();
        file.ReadFile("InputFiles/test.txt");
    }

}