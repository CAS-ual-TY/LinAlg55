import java.util.Locale;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;

import static java.lang.Math.*;

public class MainStatistik
{
    private static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getInstance(Locale.US);
    
    public static void main(String[] args)
    {
        MainStatistik.DEFAULT_FORMAT.getFormat().setMinimumFractionDigits(10);
        MainStatistik.DEFAULT_FORMAT.getFormat().setMaximumFractionDigits(10);
        
        final RealMatrix matrixA = MatrixUtils.createRealMatrix(new double[][] {{
            5,
            cos(2 * PI * 53 / 365) + cos(2 * PI * 109 / 365) + cos(2 * PI * 167 / 365) + cos(2 * PI * 245 / 365) + cos(2 * PI * 349 / 365),
            sin(2 * PI * 53 / 365) + sin(2 * PI * 109 / 365) + sin(2 * PI * 167 / 365) + sin(2 * PI * 245 / 365) + sin(2 * PI * 349 / 365)
        }, {
            cos(2 * PI * 53 / 365) + cos(2 * PI * 109 / 365) + cos(2 * PI * 167 / 365) + cos(2 * PI * 245 / 365) + cos(2 * PI * 349 / 365),
            cos(2 * PI * 53 / 365) * cos(2 * PI * 53 / 365) + cos(2 * PI * 109 / 365) * cos(2 * PI * 109 / 365) + cos(2 * PI * 167 / 365) * cos(2 * PI * 167 / 365) + cos(2 * PI * 245 / 365) * cos(2 * PI * 245 / 365) + cos(2 * PI * 349 / 365) * cos(2 * PI * 349 / 365),
            sin(2 * PI * 53 / 365) * cos(2 * PI * 53 / 365) + sin(2 * PI * 109 / 365) * cos(2 * PI * 109 / 365) + sin(2 * PI * 167 / 365) * cos(2 * PI * 167 / 365) + sin(2 * PI * 245 / 365) * cos(2 * PI * 245 / 365) + sin(2 * PI * 349 / 365) * cos(2 * PI * 349 / 365)
        }, {
            sin(2 * PI * 53 / 365) + sin(2 * PI * 109 / 365) + sin(2 * PI * 167 / 365) + sin(2 * PI * 245 / 365) + sin(2 * PI * 349 / 365),
            sin(2 * PI * 53 / 365) * cos(2 * PI * 53 / 365) + sin(2 * PI * 109 / 365) * cos(2 * PI * 109 / 365) + sin(2 * PI * 167 / 365) * cos(2 * PI * 167 / 365) + sin(2 * PI * 245 / 365) * cos(2 * PI * 245 / 365) + sin(2 * PI * 349 / 365) * cos(2 * PI * 349 / 365),
            sin(2 * PI * 53 / 365) * sin(2 * PI * 53 / 365) + sin(2 * PI * 109 / 365) * sin(2 * PI * 109 / 365) + sin(2 * PI * 167 / 365) * sin(2 * PI * 167 / 365) + sin(2 * PI * 245 / 365) * sin(2 * PI * 245 / 365) + sin(2 * PI * 349 / 365) * sin(2 * PI * 349 / 365),
        }});
        
        final RealMatrix vectorB = MatrixUtils.createColumnRealMatrix(new double[] {
            -1 * 1 + 11 * 1 + 25 * 1 + 22 * 1 + (-2) * 1,
            -1 * cos(2 * PI * 53 / 365) + 11 * cos(2 * PI * 109 / 365) + 25 * cos(2 * PI * 167 / 365) + 22 * cos(2 * PI * 245 / 365) + (-2) * cos(2 * PI * 349 / 365),
            -1 * sin(2 * PI * 53 / 365) + 11 * sin(2 * PI * 109 / 365) + 25 * sin(2 * PI * 167 / 365) + 22 * sin(2 * PI * 245 / 365) + (-2) * sin(2 * PI * 349 / 365)
        });
        
        final RealMatrix inverseA = MatrixUtils.inverse(matrixA);
        
        System.out.println("--- Assignment ---");
        
        final RealMatrix resultA_B = inverseA.multiply(vectorB);
        System.out.println("x = " + MainStatistik.DEFAULT_FORMAT.format(resultA_B));
    }
}
