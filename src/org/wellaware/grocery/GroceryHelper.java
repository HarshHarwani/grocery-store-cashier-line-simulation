package org.wellaware.grocery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class GroceryHelper {
    
    static Queue<Customer> customerQueue=GroceryMain.getCustomerQueue();
 
/**
 * This method picks customer from the main customer queue and puts it in a separate list,which
 * will then be sorted acording to number of items of customers and their type so that we can assign the customer
 * with least items first. 
 * @param customerListArrivedAtSameTime
 * @param time
 */
    public static void collectSameTimeCustomers(
            List<Customer> customerListArrivedAtSameTime, int time) {
        Customer customer = customerQueue.peek();
        while (customer != null && customer.getTimeArrived() == time) {
            customerListArrivedAtSameTime.add(customerQueue.poll());
            customer = customerQueue.peek();
        }

    }
    /**
     *just keep serving the customer till it items are not empty as soon as they are empty,
     * remove that customer from the queue.
     * @param customer
     */
    public static void expertServe(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null && cust.servedItems() == 0) {
            customer.poll();
        }
    }
    /**
     * 
     * @param customer
     */
    public static void traineeServe(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null) {
            if (!cust.isInService()) {
                cust.setInService(true);
            } else {
                if (cust.servedItems() == 0) {
                    customer.poll();
                } else {
                    cust.setInService(false); //to simulate double time as trainee takes double the time. 
                }
            }
        }
    }
    /**
     * 
     * @param args
     * @return {@link GroceryMain}
     * this method will read from the line, the number of registers instantiate the grocery object
     * then will also read the customer records instantiate the customer objects, put them in a customerQueue
     * will return the GroceryMain object, which will in turn have the Grocery Object.
     */
    public static GroceryMain initiaize(String[] args) {
        GroceryMain groceryMain = null;
        Grocery grocery = null;
        String line = "";
        int firstLine = 0;
        BufferedReader buReader = null;
        try {
            buReader = new BufferedReader(
                    new FileReader(new File(args[0])));
        } catch (FileNotFoundException e) {
            System.out.println("File not found-->" +e.getMessage());
          //abnormal termination
            System.exit(-1);
        }
        try {
            while ((line = buReader.readLine()) != null) {
                if (firstLine == 0) {
                    try {
                        int noofRegisters = Integer.parseInt(line);
                        grocery = new Grocery(noofRegisters);
                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Error in parsing number of registers->"
                                        + e.getMessage());
                    }
                } else {
                    Customer customer = buildCustomerObjects(line);
                    customerQueue.offer(customer);
                }
                firstLine++;
            }
            groceryMain = new GroceryMain(grocery);
        } catch (IOException e) {
            System.out.println("Exception in reading the file" +e.getMessage());
          //abnormal termination
            System.exit(-1);
        }
        return groceryMain;
    }
    /**
     * This method reads the line from the file and constructs and returns a customer object.
     * @param line
     * @return {@link Customer}
     */
    public static Customer buildCustomerObjects(String line) {
        String[] items = line.split(" ");
        if(items.length!=3){
            System.out.println("Error in Input-->"+line);
            //abnormal termination
            System.exit(-1);
        }
        if (items[0].equals(Type.A.toString())) {
            return new Customer(Type.A, Integer.parseInt(items[1]),
                    Integer.parseInt(items[2]));
        } else if(items[0].equals(Type.B.toString())) {
            return new Customer(Type.B, Integer.parseInt(items[1]),
                    Integer.parseInt(items[2]));
        }else{
            System.out.println("Customer Type is Invalid");
          //abnormal termination
            System.exit(-1);
            return null;
        }
    }
}
