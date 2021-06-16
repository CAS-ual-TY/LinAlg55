import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class ListTests
{
    @Test
    void testSize()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        
        assertEquals(list1.size(), 0);
        
        list1.add(0L);
        
        assertEquals(list1.size(), 1);
        
        for(int i = 0; i < 10; ++i)
        {
            list1.add((long)(i - 5));
        }
        
        assertEquals(list1.size(), 11);
        
        SpecialLinkedList<Long> list2 = list1.copy();
        
        assertEquals(list1.size(), list2.size());
    }
    
    @Test
    void testOrder()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        
        list1.add(3L);
        
        for(int i = 0; i < 3; ++i)
        {
            list1.add((long)i);
        }
        
        for(int i = 0; i < 10; ++i)
        {
            list1.add((long)(5 - i));
        }
        
        ArrayList<Long> list = new ArrayList<>(list1.size());
        list1.forEachEntry(list::add);
        list.sort(Long::compare);
        
        list1.forEachEntry((l) ->
        {
            assertTrue(l == list.remove(0));
        });
    }
    
    @Test
    void testCopy()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        
        list1.add(3L);
        
        for(int i = 0; i < 3; ++i)
        {
            list1.add((long)i);
        }
        
        for(int i = 0; i < 10; ++i)
        {
            list1.add((long)(5 - i));
        }
        
        SpecialLinkedList<Long> list2 = list1.copy();
        
        LinkedList<Long> list = new LinkedList<>();
        
        list1.forEachEntry(list::addLast);
        
        list2.forEachEntry((l) ->
        {
            assertEquals(list.removeFirst(), l);
        });
    }
    
    @Test
    void testMissing()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        
        list1.add(3L);
        
        for(int i = 0; i < 3; ++i)
        {
            list1.add((long)i);
        }
        
        for(int i = 0; i < 10; ++i)
        {
            list1.add((long)(5 - i));
        }
        
        assertEquals(list1.size(), 14);
        
        SpecialLinkedList<Long> list2 = list1.copy();
        
        SpecialLinkedList<Long> missing = list1.getAllMissingEntriesOf(list2);
        
        assertEquals(missing.size(), 0);
        
        missing = list2.getAllMissingEntriesOf(list1);
        
        assertEquals(missing.size(), 0);
    }
    
    @Test
    void testShared()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        
        list1.add(3L);
        
        for(int i = 0; i < 3; ++i)
        {
            list1.add((long)i);
        }
        
        for(int i = 0; i < 10; ++i)
        {
            list1.add((long)(5 - i));
        }
        
        assertEquals(list1.size(), 14);
        
        SpecialLinkedList<Long> list2 = list1.copy();
        
        SpecialLinkedList<Long> shared = list1.getAllSharedEntriesOf(list2);
        
        assertEquals(shared.size(), list1.size());
        
        SpecialLinkedList<Long> missing = list1.getAllMissingEntriesOf(shared);
        
        assertTrue(missing.isEmpty());
        
        ArrayList<Long> list = new ArrayList<>(list1.size());
        shared.forEachEntry(list::add);
        
        list1.forEachEntry((l) ->
        {
            assertTrue(l == list.remove(0));
        });
        
        list2.add(1L);
        
        shared = list1.getAllSharedEntriesOf(list2);
        
        assertEquals(shared.size(), list2.size() - 1);
        
        missing.forEachEntry((l) ->
        {
            assertTrue(l == 1L);
        });
    }
    
    @Test
    void testAddAll()
    {
        SpecialLinkedList<Long> list1 = SpecialLinkedList.makeLongsList();
        SpecialLinkedList<Long> list2 = SpecialLinkedList.makeLongsList();
        
        for(int i = 0; i < 5; ++i)
        {
            list1.add((long)i);
            list2.add((long)(i + 5));
        }
        
        list1.addAll(list2);
        
        assertEquals(list1.size(), 10);
        
        //        System.out.println(list1);
        // technically more tests needed
    }
}
