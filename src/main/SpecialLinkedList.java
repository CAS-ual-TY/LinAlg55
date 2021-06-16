import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * One way linked list, sorted
 * @author CAS_ual_TY
 *
 */
public class SpecialLinkedList<P>
{
    private final Comparator<P> comparator;
    private Entry<P> base;
    
    private int size;
    
    private SpecialLinkedList(Comparator<P> comparator, Entry<P> base)
    {
        this.comparator = comparator;
        this.base = base;
        
        this.size = 0;
        
        Entry<P> current = this.base;
        
        while(current != null)
        {
            this.size++;
            current = current.getNext();
        }
    }
    
    public SpecialLinkedList(Comparator<P> comparator)
    {
        this(comparator, null);
    }
    
    public void add(P value) // O(n)
    {
        if(this.base == null)
        {
            this.base = new Entry<>(value);
        }
        else
        {
            Entry<P> entry = new Entry<>(value);
            
            if(this.compareEntries(entry, this.base) <= 0)
            {
                // new entry smaller or equals to first entry
                // just add in front
                
                entry.setNext(this.base);
                this.base = entry;
            }
            else
            {
                // new entry must be bigger than first entry
                
                Entry<P> current = this.base;
                Entry<P> next = null;
                
                while(current != null)
                {
                    next = current.getNext();
                    
                    if(next == null)
                    {
                        // new entry must be bigger than current entry
                        
                        current.setNext(entry);
                        break;
                    }
                    else if(this.compareEntries(next, entry) >= 0)
                    {
                        // new entry is smaller or equals to next entry
                        
                        current.setNext(entry);
                        entry.setNext(next);
                        break;
                    }
                    else
                    {
                        // new entry is bigger than next entry
                        
                        current = next;
                    }
                }
                
                if(current == null)
                {
                    // we did not add new entry to the list during the while loop
                    
                    throw new RuntimeException("Programer might be idjot");
                }
            }
        }
        
        this.size++;
    }
    
    public void addAll(SpecialLinkedList<P> list) // O(n+m)
    {
        if(list.base == null)
        {
            return;
        }
        else if(this.base == null)
        {
            this.base = list.copy().base;
            this.size = list.size;
        }
        else
        {
            Entry<P> current = new Entry<>(null);
            
            Entry<P> e1 = this.base;
            Entry<P> e2 = list.base;
            
            this.base = current;
            this.size = 1;
            
            while(e1 != null && e2 != null)
            {
                if(this.compareEntries(e1, e2) <= 0)
                {
                    current.setNext(e1.copyValue());
                    current = current.getNext();
                    e1 = e1.getNext();
                }
                else
                {
                    current.setNext(e2.copyValue());
                    current = current.getNext();
                    e2 = e2.getNext();
                }
                
                this.size++;
            }
            
            if(e1 != null)
            {
                current.setNext(e1);
                
                while(e1 != null)
                {
                    e1 = e1.getNext();
                    this.size++;
                }
            }
            else
            {
                while(e2 != null)
                {
                    current.setNext(e2.copyValue());
                    current = current.getNext();
                    e2 = e2.getNext();
                    this.size++;
                }
            }
            
            this.base = this.base.getNext();
            this.size--;
        }
    }
    
    public SpecialLinkedList<P> getAllMissingEntriesOf(SpecialLinkedList<P> list) // O(n+m)
    {
        if(list.base == null)
        {
            return new SpecialLinkedList<>(this.comparator);
        }
        else if(this.base == null)
        {
            return list.copy();
        }
        
        Entry<P> current = new Entry<>(null); // temporary entry
        SpecialLinkedList<P> missing = new SpecialLinkedList<>(this.comparator, current);
        
        Entry<P> e1 = this.base;
        Entry<P> e2 = list.base;
        
        while(e1 != null && e2 != null)
        {
            if(this.compareEntries(e1, e2) == 0)
            {
                e1 = e1.getNext();
                e2 = e2.getNext();
            }
            else if(this.compareEntries(e1, e2) < 0)
            {
                e1 = e1.getNext();
            }
            else if(this.compareEntries(e1, e2) > 0)
            {
                current.setNext(e2.copyValue());
                current = current.getNext();
                e2 = e2.getNext();
                missing.size++;
            }
        }
        
        while(e2 != null)
        {
            current.setNext(e2.copyValue());
            current = current.getNext();
            e2 = e2.getNext();
            missing.size++;
        }
        
        missing.base = missing.base.getNext(); // eliminate temporary entry
        missing.size--;
        
        return missing;
    }
    
    public SpecialLinkedList<P> getAllSharedEntriesOf(SpecialLinkedList<P> list) // O(n+m)
    {
        if(this.base == null || list.base == null)
        {
            return new SpecialLinkedList<>(this.comparator);
        }
        
        Entry<P> current = new Entry<>(null); // temporary entry
        SpecialLinkedList<P> shared = new SpecialLinkedList<>(this.comparator, current);
        
        Entry<P> e1 = this.base;
        Entry<P> e2 = list.base;
        
        while(e1 != null && e2 != null)
        {
            if(this.compareEntries(e1, e2) == 0)
            {
                current.setNext(e1.copyValue());
                current = current.getNext();
                e1 = e1.getNext();
                e2 = e2.getNext();
                shared.size++;
            }
            else if(this.compareEntries(e1, e2) < 0)
            {
                e1 = e1.getNext();
            }
            else if(this.compareEntries(e1, e2) > 0)
            {
                e2 = e2.getNext();
            }
        }
        
        shared.base = shared.base.getNext(); // eliminate temporary entry
        shared.size--;
        
        return shared;
    }
    
    public <Q> Q accumulate(Supplier<Q> initial, BiFunction<Q, P, Q> function)
    {
        Q value = initial.get();
        
        Entry<P> entry = this.base;
        
        while(entry != null)
        {
            value = function.apply(value, entry.getValue());
            entry = entry.getNext();
        }
        
        return value;
    }
    
    public void forEachEntry(Consumer<P> consumer)
    {
        Entry<P> entry = this.base;
        
        while(entry != null)
        {
            consumer.accept(entry.getValue());
            entry = entry.getNext();
        }
    }
    
    public void filter(Function<P, Boolean> keepEntry)
    {
        Entry<P> current = new Entry<>(null);
        current.setNext(this.base);
        this.base = current;
        
        Entry<P> next = null;
        
        while(current != null)
        {
            next = current.getNext();
            
            if(next == null)
            {
                break;
            }
            else if(!keepEntry.apply(next.getValue()))
            {
                current.setNext(next.getNext());
                this.size--;
            }
            
            current = current.getNext();
        }
        
        this.base = this.base.getNext();
    }
    
    public int size()
    {
        return this.size;
    }
    
    public boolean isEmpty()
    {
        return this.size == 0;
    }
    
    public SpecialLinkedList<P> copy()
    {
        if(this.base == null)
        {
            return new SpecialLinkedList<>(this.comparator);
        }
        else
        {
            SpecialLinkedList<P> list = new SpecialLinkedList<>(this.comparator);
            list.size = this.size;
            
            Entry<P> copies = this.base.copyValue();
            list.base = copies;
            
            Entry<P> entry = this.base.getNext();
            
            while(entry != null)
            {
                copies.setNext(entry.copyValue());
                copies = copies.getNext();
                entry = entry.getNext();
            }
            
            return list;
        }
    }
    
    public SpecialLinkedList<P> clear()
    {
        this.base = null;
        this.size = 0;
        return this;
    }
    
    private int compareEntries(Entry<P> e1, Entry<P> e2)
    {
        return this.comparator.compare(e1.getValue(), e2.getValue());
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        
        s.append('{');
        
        if(this.base != null)
        {
            s.append(this.base.getValue());
            
            Entry<P> entry = this.base.getNext();
            
            while(entry != null)
            {
                s.append(", ").append(entry.getValue());
                entry = entry.getNext();
            }
        }
        
        return s.append('}').toString();
    }
    
    public static SpecialLinkedList<Long> makeLongsList()
    {
        return new SpecialLinkedList<>(Long::compare);
    }
    
    public static class Entry<P>
    {
        private Entry<P> next;
        private final P value;
        
        public Entry(P value)
        {
            this.next = null;
            this.value = value;
        }
        
        public Entry<P> setNext(Entry<P> next)
        {
            this.next = next;
            return this;
        }
        
        public Entry<P> getNext()
        {
            return this.next;
        }
        
        public P getValue()
        {
            return this.value;
        }
        
        public Entry<P> copyValue() // does not copy pointer
        {
            return new Entry<>(this.value);
        }
    }
}
