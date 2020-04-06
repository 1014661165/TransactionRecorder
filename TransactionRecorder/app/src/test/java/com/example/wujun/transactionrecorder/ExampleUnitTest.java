package com.example.wujun.transactionrecorder;

import com.example.wujun.transactionrecorder.bean.Item;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void  testRemove(){
        List<Item> items = new ArrayList<>();
        items.add(new Item(0, "", 0f));
        items.add(new Item(0, "", 0f));
        items.add(new Item(0, "", 0f));

        Item item = new Item(0, "", 0f);
        items.remove(item);
        System.out.println(items.size());
    }
}