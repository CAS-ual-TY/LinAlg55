import java.io.PrintStream;

public class Matrix
{
    private final int size;
    private Fraction[][] matrix;
    
    private Matrix(int size)
    {
        this.size = size;
        this.matrix = new Fraction[this.size][this.size];
    }
    
    public Matrix(long... fractions)
    {
        this(Matrix.longsToFractions(fractions));
    }
    
    public Matrix(Fraction... fractions)
    {
        if(fractions.length <= 0)
        {
            throw new IllegalArgumentException();
        }
        
        int size = -1;
        
        for(int i = 1; i < fractions.length; i++)
        {
            if(i * i == fractions.length)
            {
                size = i;
                break;
            }
            else if(i * i > fractions.length)
            {
                throw new IllegalArgumentException();
            }
        }
        
        if(size == -1)
        {
            throw new IllegalArgumentException();
        }
        
        this.size = size;
        
        this.matrix = new Fraction[this.size][this.size];
        
        // filling from left to right
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                this.matrix[y][x] = fractions[x + y * size];
            }
        }
    }
    
    public Matrix inverse()
    {
        Matrix inverse = new Matrix(this.size);
        
        for(int y = 0; y < this.size; y++)
        {
            for(int x = 0; x < this.size; x++)
            {
                inverse.matrix[y][x] = x == y ? new Fraction(1, 1) : new Fraction(0, 1);
            }
        }
        
        Fraction factor;
        
        for(int i = 0; i < this.size; i++)
        {
            factor = this.matrix[i][i].copy().flip();
            this.multiplyRow(i, factor);
            inverse.multiplyRow(i, factor);
            
            for(int y = 0; y < this.size; y++)
            {
                if(y != i)
                {
                    factor = this.matrix[y][i].copy();
                    
                    for(int j = 0; j < this.size; j++)
                    {
                        this.matrix[y][j].subtract((this.matrix[i][j].copy().multiply(factor))).reduce();
                        inverse.matrix[y][j].subtract((inverse.matrix[i][j].copy().multiply(factor))).reduce();
                    }
                }
            }
        }
        
        this.matrix = inverse.matrix;
        
        for(int y = 0; y < this.size; y++)
        {
            for(int x = 0; x < this.size; x++)
            {
                this.matrix[y][x].reduce();
            }
        }
        
        return this;
    }
    
    public void addRow(int y, int rowToAdd)
    {
        for(int x = 0; x < this.size; x++)
        {
            this.matrix[y][x].add(this.matrix[rowToAdd][x]);
        }
    }
    
    public void subtractRow(int y, int rowToAdd)
    {
        for(int x = 0; x < this.size; x++)
        {
            this.matrix[y][x].subtract(this.matrix[rowToAdd][x]);
        }
    }
    
    public void multiplyRow(int y, Fraction factor)
    {
        for(int x = 0; x < this.size; x++)
        {
            this.matrix[y][x].multiply(factor);
        }
    }
    
    public void addColumn(int x, int columnToAdd)
    {
        for(int y = 0; y < this.size; y++)
        {
            this.matrix[y][x].add(this.matrix[y][columnToAdd]);
        }
    }
    
    public void subtractColumn(int x, int columnToAdd)
    {
        for(int y = 0; y < this.size; y++)
        {
            this.matrix[y][x].subtract(this.matrix[y][columnToAdd]);
        }
    }
    
    public void multiplyColumn(int x, Fraction factor)
    {
        for(int y = 0; y < this.size; y++)
        {
            this.matrix[y][x].multiply(factor);
        }
    }
    
    public Fraction[] multiply(Fraction[] vector)
    {
        if(vector.length != this.size)
        {
            throw new IllegalArgumentException();
        }
        
        Fraction[] result = new Fraction[vector.length];
        
        for(int y = 0; y < this.size; y++)
        {
            result[y] = new Fraction(0, 1);
            
            for(int x = 0; x < this.size; x++)
            {
                result[y].add(vector[x].copy().multiply(this.matrix[y][x]).reduce()).reduce();
            }
        }
        
        return result;
    }
    
    public double[] multiplySimple(Fraction[] vector)
    {
        if(vector.length != this.size)
        {
            throw new IllegalArgumentException();
        }
        
        double[] result = new double[vector.length];
        
        for(int y = 0; y < this.size; y++)
        {
            result[y] = 0;
            
            for(int x = 0; x < this.size; x++)
            {
                result[y] += ((double)vector[x].getNumerator() / (double)vector[x].getDenominator()) * ((double)this.matrix[y][x].getNumerator() / (double)this.matrix[y][x].getDenominator());
            }
        }
        
        return result;
    }
    
    public Matrix copy()
    {
        Matrix matrix = new Matrix(this.size);
        
        for(int y = 0; y < this.size; y++)
        {
            for(int x = 0; x < this.size; x++)
            {
                matrix.matrix[y][x] = this.matrix[y][x].copy();
            }
        }
        
        return matrix;
    }
    
    public void printMatrix()
    {
        this.printMatrix(System.out);
    }
    
    public void printMatrix(PrintStream out)
    {
        out.print("(  ");
        
        for(int y = 0; y < this.size; y++)
        {
            if(y > 0)
            {
                out.print("   ");
            }
            
            for(int x = 0; x < this.size; x++)
            {
                out.print(this.matrix[y][x]);
                
                if(x < this.size - 1)
                {
                    out.print("   ");
                }
            }
            
            if(y < this.size - 1)
            {
                out.println();
            }
        }
        
        out.println("  )");
    }
    
    public void printMatrixSimple()
    {
        System.out.print("(  ");
        
        for(int y = 0; y < this.size; y++)
        {
            if(y > 0)
            {
                System.out.print("   ");
            }
            
            for(int x = 0; x < this.size; x++)
            {
                System.out.print((double)this.matrix[y][x].getNumerator() / (double)this.matrix[y][x].getDenominator());
                
                if(x < this.size - 1)
                {
                    System.out.print("   ");
                }
            }
            
            if(y < this.size - 1)
            {
                System.out.println();
            }
        }
        
        System.out.println("  )");
    }
    
    public boolean equals(Matrix matrix)
    {
        if(this.size != matrix.size)
        {
            return false;
        }
        
        for(int y = 0; y < this.size; y++)
        {
            for(int x = 0; x < this.size; x++)
            {
                if(!this.matrix[y][x].equals(matrix.matrix[y][x]))
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static Fraction[] longsToFractions(long... longs)
    {
        Fraction[] fractions = new Fraction[longs.length];
        
        for(int i = 0; i < longs.length; i++)
        {
            fractions[i] = new Fraction(longs[i], 1);
        }
        
        return fractions;
    }
}
