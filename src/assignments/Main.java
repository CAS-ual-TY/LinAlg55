
public class Main
{
    public static final Matrix A = new Matrix(
        10, 7, 8, 7,
        7, 5, 6, 5,
        8, 6, 10, 9,
        7, 5, 9, 10);
    
    public static final Matrix A_IN = Main.A.copy().inverse();
    
    public static final Fraction[] B = {
        new Fraction(32, 1),
        new Fraction(23, 1),
        new Fraction(33, 1),
        new Fraction(31, 1)
    };
    
    public static void main(String[] args)
    {
        System.out.println("A =");
        Main.A.printMatrix();
        System.out.println();
        
        System.out.println("Inverse of A =");
        Main.A_IN.printMatrix();
        System.out.println();
        
        System.out.println("b =");
        Main.printVector(Main.B);
        System.out.println();
        
        Main.assignment53();
        Main.assignment54();
        Main.assignment55();
    }
    
    public static void assignment53()
    {
        System.out.println("--- Assignment 53 ---");
        
        System.out.println("x =");
        Main.printVector(Main.A_IN.multiply(Main.B));
        
        System.out.println("  =");
        Main.printVector(Main.A_IN.multiplySimple(Main.B));
        System.out.println();
    }
    
    public static void assignment54()
    {
        final Fraction[] uB = {
            Main.tenth(321),
            Main.tenth(229),
            Main.tenth(331),
            Main.tenth(309)
        };
        
        System.out.println("--- Assignment 54 ---");
        
        System.out.println("b =");
        Main.printVector(uB);
        System.out.println();
        
        System.out.println("x =");
        Main.printVector(Main.A_IN.multiply(uB));
        
        System.out.println("  =");
        Main.printVector(Main.A_IN.multiplySimple(uB));
        System.out.println();
    }
    
    public static void assignment55()
    {
        Matrix uA = new Matrix(
            Main.hundredth(1000), Main.hundredth(700), Main.hundredth(810), Main.hundredth(720),
            Main.hundredth(708), Main.hundredth(504), Main.hundredth(600), Main.hundredth(500),
            Main.hundredth(800), Main.hundredth(598), Main.hundredth(989), Main.hundredth(900),
            Main.hundredth(699), Main.hundredth(499), Main.hundredth(900), Main.hundredth(998));
        
        System.out.println("--- Assignment 55 ---");
        
        System.out.println("A =");
        uA.printMatrix();
        
        System.out.println("  =");
        uA.printMatrixSimple();
        System.out.println();
        
        uA.inverseWithSteps();
        
        System.out.println("Inverse of A =");
        uA.printMatrix();
        
        System.out.println("  =");
        uA.printMatrixSimple();
        System.out.println();
        
        System.out.println("x =");
        Main.printVector(uA.multiplySimple(Main.B));
        
        System.out.println("  =");
        Main.printVector(uA.multiply(Main.B));
        System.out.println();
    }
    
    private static void printVector(double[] vector)
    {
        System.out.print("(  ");
        
        for(int i = 0; i < vector.length; ++i)
        {
            if(i != 0)
            {
                System.out.print("   ");
            }
            
            System.out.print(Main.round(vector[i]));
        }
        
        System.out.println("  )");
    }
    
    private static void printVector(Fraction[] vector)
    {
        System.out.print("(  ");
        
        for(int i = 0; i < vector.length; ++i)
        {
            if(i != 0)
            {
                System.out.print("   ");
            }
            
            System.out.print(vector[i]);
        }
        
        System.out.println("  )");
    }
    
    private static Fraction tenth(long numerator)
    {
        return new Fraction(numerator, 10L);
    }
    
    private static Fraction hundredth(long numerator)
    {
        return new Fraction(numerator, 100L);
    }
    
    private static double round(double d)
    {
        return Math.round(d * 10000) / 10000D;
    }
}
