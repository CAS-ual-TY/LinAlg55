import java.util.Locale;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;

import static java.lang.Math.*;

public class MainStatistikJulie
{
    private static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getInstance(Locale.US);
    
    public static void main(String[] args)
    {
        MainStatistikJulie.DEFAULT_FORMAT.getFormat().setMinimumFractionDigits(10);
        MainStatistikJulie.DEFAULT_FORMAT.getFormat().setMaximumFractionDigits(10);
        
        final RealMatrix matrixA = MatrixUtils.createRealMatrix(new double[][] {{
            5,
            cos(2*PI*25/365)+cos(2*PI*139/365)+cos(2*PI*169/365)+cos(2*PI*247/365)+cos(2*PI*336/365),
            sin(2*PI*25/365)+sin(2*PI*139/365)+sin(2*PI*169/365)+sin(2*PI*247/365)+sin(2*PI*336/365)
            },{
            cos(2*PI*25/365)+cos(2*PI*139/365)+cos(2*PI*169/365)+cos(2*PI*247/365)+cos(2*PI*336/365),
            cos(2*PI*25/365)*cos(2*PI*25/365)+cos(2*PI*139/365)*cos(2*PI*139/365)+cos(2*PI*169/365)*cos(2*PI*169/365)+cos(2*PI*247/365)*cos(2*PI*247/365)+cos(2*PI*336/365)*cos(2*PI*336/365),
            sin(2*PI*25/365)*cos(2*PI*25/365)+sin(2*PI*139/365)*cos(2*PI*139/365)+sin(2*PI*169/365)*cos(2*PI*169/365)+sin(2*PI*247/365)*cos(2*PI*247/365)+sin(2*PI*336/365)*cos(2*PI*336/365)
            },{
            sin(2*PI*25/365)+sin(2*PI*139/365)+sin(2*PI*169/365)+sin(2*PI*247/365)+sin(2*PI*336/365),
            sin(2*PI*25/365)*cos(2*PI*25/365)+sin(2*PI*139/365)*cos(2*PI*139/365)+sin(2*PI*169/365)*cos(2*PI*169/365)+sin(2*PI*247/365)*cos(2*PI*247/365)+sin(2*PI*336/365)*cos(2*PI*336/365),
            sin(2*PI*25/365)*sin(2*PI*25/365)+sin(2*PI*139/365)*sin(2*PI*139/365)+sin(2*PI*169/365)*sin(2*PI*169/365)+sin(2*PI*247/365)*sin(2*PI*247/365)+sin(2*PI*336/365)*sin(2*PI*336/365),
            }});
        
        final RealMatrix vectorB = MatrixUtils.createColumnRealMatrix(new double[] {
            -4+20+27+22+1,
            -4*cos(2*PI*25/365)+20*cos(2*PI*139/365)+27*cos(2*PI*169/365)+22*cos(2*PI*247/365)+1*cos(2*PI*336/365),
            -4*sin(2*PI*25/365)+20*sin(2*PI*139/365)+27*sin(2*PI*169/365)+22*sin(2*PI*247/365)+1*sin(2*PI*336/365)
            });
        
        final RealMatrix inverseA = MatrixUtils.inverse(matrixA);
        
        System.out.println("--- Assignment ---");
        
        final RealMatrix resultA_B = inverseA.multiply(vectorB);
        System.out.println("x = " + MainStatistikJulie.DEFAULT_FORMAT.format(resultA_B));
    }
}
