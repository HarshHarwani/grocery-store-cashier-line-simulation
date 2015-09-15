package org.wellaware.grocery.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wellaware.grocery.GroceryHelper;
import org.wellaware.grocery.GroceryMain;
import org.wellaware.grocery.RegisterCollection;

public class TimeTest {

    @Test
    public void test() {
        String[] path = {
                "input.txt","input1.txt","input2.txt","input3.txt"};
        int[] actualTime={11,7,6,11};
        for (int i = 0; i < path.length; i++) {
            String[] input={path[i]};
            GroceryMain groceryMain = GroceryHelper.initiaize(input);
            RegisterCollection registerCollection = groceryMain
                    .getRegisterCollection();
            int time = GroceryMain.calculateTime(registerCollection,
                    groceryMain);
            testHelper(time,actualTime[i]);
        }
    }

    public void testHelper(int expectedTime,int actualTime) {
        assertEquals(expectedTime, actualTime);
    }

}
