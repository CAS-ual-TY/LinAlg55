import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class MatrixTests
{
    @Test
    void testIdentity()
    {
        Matrix m = new Matrix(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
        
        Matrix m2 = m.copy();
        
        m.inverse();
        
        assertTrue(m.equals(m2));
    }
    
    @Test
    void testUnequals()
    {
        Matrix m = new Matrix(
            1, 0, 0, 1,
            0, 1, 1, 0,
            0, 1, 1, 0,
            1, 0, 0, 1);
        
        Matrix m2 = m.copy();
        
        m.inverse();
        
        assertTrue(!m.equals(m2));
    }
    
    @Test
    void testInverseAndOutput() throws IOException
    {
        Matrix m = new Matrix(
            10, 7, 8, 7,
            7, 5, 6, 5,
            8, 6, 10, 9,
            7, 5, 9, 10);
        
        Matrix i = new Matrix(
            25, -41, 10, -6,
            -41, 68, -17, 10,
            10, -17, 5, -3,
            -6, 10, -3, 2);
        
        m.inverse();
        
        assertTrue(m.equals(i));
        
        String output;
        
        final String utf8 = StandardCharsets.UTF_8.name();
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos, true, utf8))
        {
            m.printMatrix(ps);
            output = baos.toString(utf8);
        }
        
        assertEquals(output, "(  " +
            "25/1   -41/1   10/1   -6/1\r\n" +
            "   -41/1   68/1   -17/1   10/1\r\n" +
            "   10/1   -17/1   5/1   -3/1\r\n" +
            "   -6/1   10/1   -3/1   2/1  )\r\n");
    }
    
}
