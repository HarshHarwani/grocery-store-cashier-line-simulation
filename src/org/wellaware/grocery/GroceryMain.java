package org.wellaware.grocery;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 * @author hharwani
 * Main grocery class which simulates grocery store / cashier line simulation.
 */
public class GroceryMain {

    private static Queue<Customer> customerQueue = new LinkedList<Customer>();
    RegisterCollection registerCollection = null;
    
    public GroceryMain(RegisterCollection registerCollection) {
        this.registerCollection = registerCollection;
    }
    
    public RegisterCollection getRegisterCollection() {
        return registerCollection;
    }
    //This is the main Customer Queue so no point in keeping it public, this will only be accessed
    //by methods inside the same package, in future if there is a need to extend, it can be changed to public
    protected static Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }
    /**
     * Main Method
     * @param args
     */
    public static void main(String args[]) {
        GroceryMain groceryMain = GroceryHelper.initiaize(args);
        RegisterCollection registerCollection = groceryMain.getRegisterCollection();
        int totalTime=calculateTime(registerCollection, groceryMain);
        System.out.println("Finished at: t=" + totalTime + " minutes");
    }    
    
    /**
     * This is central logic of the simulation, the simulation is executed till there are customers
     * or any register is serving any customer, for every iteration we assign customers to the nmber of available registers
     * and increment the time. 
     * @param {@link RegisterCollection}
     * @param {@link GroceryMain}
     * @return int
     */
    public static int calculateTime(RegisterCollection registerCollection,GroceryMain groceryMain){
        int time = 1;
        while (!customerQueue.isEmpty() || registerCollection.isRegisterinService()) {
            List<Customer> customerListArrivedAtSameTime = new ArrayList<Customer>();
            GroceryHelper.collectSameTimeCustomers(customerListArrivedAtSameTime, time);
            //Customers arrived at same time are sorted first according to their item count,
            //if that is same then according to their type.
            Collections.sort(customerListArrivedAtSameTime);
            registerCollection.serviceCustomer(customerListArrivedAtSameTime);
            int index = 0;
            while (index < registerCollection.getRegisters().size()) {
                //getting the customerList of the register and 
                //passing it to the serve methods where the customer get served
                Queue<Customer> customer = registerCollection.getRegisters().get(index).getCustomerList();
                if (index == registerCollection.getRegisters().size() - 1) {
                    GroceryHelper.traineeServe(customer);
                } else {
                    GroceryHelper.expertServe(customer);
                }
                index++;
            }
            time++;
        }
        return time;
    }
}
