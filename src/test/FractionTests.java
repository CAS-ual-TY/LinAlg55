import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FractionTests
{
    @Test
    void testBasics()
    {
        Fraction frac1 = new Fraction(2, 1);
        
        assertEquals(frac1.getNumerator(), 2);
        assertEquals(frac1.getDenominator(), 1);
        
        frac1.divide(2);
        
        assertEquals(frac1.getDenominator(), 2);
        
        frac1.reduce();
        
        assertEquals(frac1.getNumerator(), 1);
        assertEquals(frac1.getDenominator(), 1);
        
        frac1.multiply(4);
        
        frac1.divide(2);
        frac1.divide(2);
        
        assertEquals(frac1.getNumerator(), 4);
        assertEquals(frac1.getDenominator(), 4);
        
        frac1.reduce();
        
        assertEquals(frac1.getNumerator(), 1);
        assertEquals(frac1.getDenominator(), 1);
        
        frac1.divide(3);
        
        assertEquals(frac1.getNumerator(), 1);
        assertEquals(frac1.getDenominator(), 3);
        
        Fraction frac2 = new Fraction(1, 4);
        
        frac1.equalizeDenominators(frac2);
        
        assertEquals(frac1.getNumerator(), 4);
        assertEquals(frac1.getDenominator(), 12);
        assertEquals(frac2.getNumerator(), 3);
        assertEquals(frac2.getDenominator(), 12);
        
        frac1.equalizeDenominators(frac2);
        
        assertEquals(frac1.getNumerator(), 4);
        assertEquals(frac1.getDenominator(), 12);
        assertEquals(frac2.getNumerator(), 3);
        assertEquals(frac2.getDenominator(), 12);
        
        frac1.add(frac2);
        
        assertEquals(frac1.getNumerator(), 7);
        assertEquals(frac1.getDenominator(), 12);
        assertEquals(frac2.getNumerator(), 3);
        assertEquals(frac2.getDenominator(), 12);
        
        Fraction frac3 = new Fraction(2, 24);
        
        frac1.add(frac3);
        
        assertEquals(frac1.getNumerator(), 16);
        assertEquals(frac1.getDenominator(), 24);
        
        frac1.reduce();
        
        assertEquals(frac1.getNumerator(), 2);
        assertEquals(frac1.getDenominator(), 3);
        
        frac1.multiply(new Fraction(3, 2));
        
        frac1.reduce();
        
        assertEquals(frac1.getNumerator(), 1);
        assertEquals(frac1.getDenominator(), 1);
    }
}
