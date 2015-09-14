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

    public static void collectSameTimeCustomers(
            List<Customer> customerListArrivedAtSameTime, int time) {
        Customer customer = customerQueue.peek();
        while (customer != null && customer.getTimeArrived() == time) {
            customerListArrivedAtSameTime.add(customerQueue.poll());
            customer = customerQueue.peek();
        }

    }

    public static void expertServe(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null && cust.servedItems() == 0) {
            customer.poll();
        }
    }

    public static void traineeServe(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null) {
            if (!cust.isInService()) {
                cust.setInService(true);
            } else {
                if (cust.servedItems() == 0) {
                    customer.poll();
                } else {
                    cust.setInService(false); //In order to simulate twice the time
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
            System.out.println("File not found" +e.getMessage());
        }
        try {
            while ((line = buReader.readLine()) != null) {
                if (firstLine == 0) {
                    int noofRegisters = Integer.parseInt(line);
                    grocery = new Grocery(noofRegisters);
                } else {
                    Customer customer = buildCustomerObjects(line);
                    customerQueue.offer(customer);
                }
                firstLine++;
            }
            groceryMain = new GroceryMain(grocery);
        } catch (IOException e) {
            System.out.println("Exception in reading the file" +e.getMessage());
        }
        return groceryMain;
    }
    
    public static Customer buildCustomerObjects(String line) {
        String[] items = line.split(" ");
        if (items[0].equals(Type.A.toString())) {
            return new Customer(Type.A, Integer.parseInt(items[1]),
                    Integer.parseInt(items[2]));
        } else {
            return new Customer(Type.B, Integer.parseInt(items[1]),
                    Integer.parseInt(items[2]));
        }
    }
}
