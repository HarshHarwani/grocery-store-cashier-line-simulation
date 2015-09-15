package org.wellaware.grocery;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GroceryMain {

    private static Queue<Customer> customerQueue = new LinkedList<Customer>();
    Grocery grocery = null;

    public GroceryMain(Grocery grocery) {
        this.grocery = grocery;
    }
    
    public Grocery getGrocery() {
        return grocery;
    }
    public static Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }
    /**
     * This is central logic of the simulation, the simulation is executed till there are customers
     * or any register is serving any customer, for every iteration we assign customers to the nmber of available registers
     * and increment the time. 
     * @param args
     */
    public static void main(String args[]) {
        GroceryMain groceryMain = GroceryHelper.initiaize(args);
        Grocery grocery = groceryMain.getGrocery();
        int time = 1;
        while (!customerQueue.isEmpty() || grocery.isRegisterinService()) {
            List<Customer> customerListArrivedAtSameTime = new ArrayList<Customer>();
            GroceryHelper.collectSameTimeCustomers(customerListArrivedAtSameTime, time);
            //Customers arrived at same time are sorted first according to their item count,
            //if that is same then according to their type.
            Collections.sort(customerListArrivedAtSameTime);
            grocery.serviceCustomer(customerListArrivedAtSameTime);
            int index = 0;
            while (index < grocery.getRegisters().size()) {
                Queue<Customer> customer = grocery.getRegisters().get(index).getCustomerList();
                if (index == grocery.getRegisters().size() - 1) {
                    GroceryHelper.traineeServe(customer);
                } else {
                    GroceryHelper.expertServe(customer);
                }
                index++;
            }
            time++;
        }
        System.out.println("Finished at: t=" + time + " minutes");

    }    
   


}
