import java.util.concurrent.atomic.AtomicInteger;

public class Fraction
{
    private SpecialLinkedList<Long> numerator;
    private SpecialLinkedList<Long> denominator;
    
    private Fraction(SpecialLinkedList<Long> numerator, SpecialLinkedList<Long> denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public Fraction()
    {
        this(SpecialLinkedList.makeLongsList(), SpecialLinkedList.makeLongsList());
    }
    
    public Fraction(long numerator, long denominator)
    {
        this();
        this.multiply(numerator);
        this.divide(denominator);
    }
    
    public void equalizeDenominators(Fraction fraction) // extend both fractions to get equal denominators
    {
        SpecialLinkedList<Long> missing1 = this.denominator.getAllMissingEntriesOf(fraction.denominator);
        SpecialLinkedList<Long> missing2 = fraction.denominator.getAllMissingEntriesOf(this.denominator);
        
        fraction.numerator.addAll(missing2);
        fraction.denominator.addAll(missing2);
        this.numerator.addAll(missing1);
        this.denominator.addAll(missing1);
    }
    
    public Fraction flip()
    {
        SpecialLinkedList<Long> temp = this.numerator;
        this.numerator = this.denominator;
        this.denominator = temp;
        
        return this;
    }
    
    private void tidy()
    {
        this.tidyList(this.numerator);
        this.tidyList(this.denominator);
        
        AtomicInteger num = new AtomicInteger(0);
        
        this.numerator.filter((l) ->
        {
            if(l == -1)
            {
                num.getAndIncrement();
                return false;
            }
            return true;
        });
        
        this.denominator.filter((l) ->
        {
            if(l == -1)
            {
                num.getAndIncrement();
                return false;
            }
            
            return true;
        });
        
        if(num.get() % 2 == 1)
        {
            this.numerator.add(-1L);
        }
    }
    
    private void tidyList(SpecialLinkedList<Long> list)
    {
        SpecialLinkedList<Long> newList = SpecialLinkedList.makeLongsList();
        
        list.filter((l) ->
        {
            if(l < 0)
            {
                newList.add(-l);
                return false;
            }
            else if(l == 1)
            {
                return false;
            }
            else
            {
                return true;
            }
        });
        
        if(newList.size() % 2 == 1)
        {
            list.add(-1L);
        }
        
        newList.filter((l) -> l != 1L);
        
        if(newList.isEmpty())
        {
            newList.add(1L);
        }
        
        list.addAll(newList);
    }
    
    public Fraction reduce()
    {
        if(this.getNumerator() == 0)
        {
            this.numerator.clear();
            this.numerator.add(0L);
            this.denominator.clear();
            this.denominator.add(1L);
        }
        else
        {
            this.tidy();
            
            SpecialLinkedList<Long> shared = this.numerator.getAllSharedEntriesOf(this.denominator);
            
            SpecialLinkedList<Long> missing1 = shared.getAllMissingEntriesOf(this.numerator);
            SpecialLinkedList<Long> missing2 = shared.getAllMissingEntriesOf(this.denominator);
            
            if(missing1.isEmpty())
            {
                missing1.add(1L);
            }
            
            if(missing2.isEmpty())
            {
                missing2.add(1L);
            }
            
            this.numerator = missing1;
            this.denominator = missing2;
        }
        
        return this;
    }
    
    public Fraction extend(long n)
    {
        this.multiply(n);
        this.divide(n);
        
        return this;
    }
    
    public Fraction add(Fraction fraction)
    {
        this.equalizeDenominators(fraction);
        
        SpecialLinkedList<Long> shared = this.numerator.getAllSharedEntriesOf(fraction.numerator);
        
        SpecialLinkedList<Long> missing1 = shared.getAllMissingEntriesOf(this.numerator);
        SpecialLinkedList<Long> missing2 = shared.getAllMissingEntriesOf(fraction.numerator);
        
        this.numerator = shared;
        
        long summand1 = Fraction.accumulateMult(missing1);
        long summand2 = Fraction.accumulateMult(missing2);
        
        long factors = summand1 + summand2;
        
        this.addPrimes(this.numerator, factors);
        
        return this;
    }
    
    public Fraction subtract(Fraction fraction)
    {
        fraction = fraction.copy().multiply(-1);
        this.add(fraction);
        return this;
    }
    
    public Fraction multiply(Fraction fraction)
    {
        this.numerator.addAll(fraction.numerator);
        this.denominator.addAll(fraction.denominator);
        return this;
    }
    
    public Fraction multiply(long n)
    {
        this.addPrimes(this.numerator, n);
        return this;
    }
    
    public Fraction divide(Fraction fraction)
    {
        fraction = fraction.copy().flip();
        this.multiply(fraction);
        return this;
    }
    
    public Fraction divide(long n)
    {
        this.addPrimes(this.denominator, n);
        return this;
    }
    
    public long getNumerator()
    {
        return Fraction.accumulateMult(this.numerator);
    }
    
    public long getDenominator()
    {
        return Fraction.accumulateMult(this.denominator);
    }
    
    public Fraction copy()
    {
        Fraction f = new Fraction();
        f.numerator = this.numerator.copy();
        f.denominator = this.denominator.copy();
        return f;
    }
    
    //        @Override
    //        public String toString()
    //        {
    //            return this.numerator + "/" + this.denominator;
    //        }
    
    @Override
    public String toString()
    {
        return this.getNumerator() + "/" + this.getDenominator();
    }
    
    public boolean equals(Fraction fraction)
    {
        return this.getNumerator() == fraction.getNumerator() && this.getDenominator() == fraction.getDenominator();
    }
    
    private void addPrimes(SpecialLinkedList<Long> list, long n)
    {
        if(n < 0)
        {
            list.add(-1L);
            n = -n;
        }
        
        for(long i = 2; i < n; i++)
        {
            if(n % i == 0)
            {
                n /= i;
                list.add(i);
                i = 1;
            }
        }
        
        if(n != 1 || list.isEmpty())
        {
            list.add(n);
        }
    }
    
    private static long accumulateMult(SpecialLinkedList<Long> list)
    {
        return list.accumulate(() -> 1L, (l1, l2) -> l1 * l2);
    }
}
