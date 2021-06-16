import java.util.Locale;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;

public class MainSimple
{
    private static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getInstance(Locale.US);
    
    public static void main(String[] args)
    {
        MainSimple.DEFAULT_FORMAT.getFormat().setMinimumFractionDigits(1);
        MainSimple.DEFAULT_FORMAT.getFormat().setMaximumFractionDigits(1);
        
        final RealMatrix matrixA = MatrixUtils.createRealMatrix(new double[][] {
            { 10, 7, 8, 7 },
            { 7, 5, 6, 5 },
            { 8, 6, 10, 9 },
            { 7, 5, 9, 10 }
        });
        
        final RealMatrix matrixAF = MatrixUtils.createRealMatrix(new double[][] {
            { 10, 7, 8.1, 7.2 },
            { 7.08, 5.04, 6, 5 },
            { 8, 5.98, 9.89, 9 },
            { 6.99, 4.99, 9, 9.98 }
        });
        
        final RealMatrix vectorB = MatrixUtils.createColumnRealMatrix(new double[] {
            32, 23, 33, 31
        });
        
        final RealMatrix vectorBF = MatrixUtils.createColumnRealMatrix(new double[] {
            32.1, 22.9, 33.1, 30.9
        });
        
        final RealMatrix inverseA = MatrixUtils.inverse(matrixA);
        final RealMatrix inverseAF = MatrixUtils.inverse(matrixAF);
        
        System.out.println("--- Assignment 53 ---");
        
        final RealMatrix resultA_B = inverseA.multiply(vectorB);
        System.out.println("x = " + MainSimple.DEFAULT_FORMAT.format(resultA_B));
        
        System.out.println("--- Assignment 54 ---");
        
        final RealMatrix resultA_BF = inverseA.multiply(vectorBF);
        System.out.println("x = " + MainSimple.DEFAULT_FORMAT.format(resultA_BF));
        
        System.out.println("--- Assignment 55 ---");
        
        final RealMatrix resultAF_B = inverseAF.multiply(vectorB);
        System.out.println("x = " + MainSimple.DEFAULT_FORMAT.format(resultAF_B));
    }
}
