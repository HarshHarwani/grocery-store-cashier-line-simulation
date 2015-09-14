package org.wellaware.grocery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }

    public static void main(String args[]) {
        GroceryMain groceryMain = initiaize(args);
        Grocery grocery = groceryMain.getGrocery();
        int time = 1;
        while (!customerQueue.isEmpty()
                || grocery.isCustomerWaitingInRegister()) {
            List<Customer> customerListArrivedAtSameTime = new ArrayList<Customer>();
            processCustomersArrivingAtSameTime(
                    customerListArrivedAtSameTime, time);
            Collections.sort(customerListArrivedAtSameTime);
            grocery.assignCustomer(customerListArrivedAtSameTime);
            int index = 0;
            while (index < grocery.getRegisters().size()) {
                Queue<Customer> customer = grocery.getRegisters()
                        .get(index).getCustomerList();
                if (index == grocery.getRegisters().size() - 1) {
                    serveWithTrainee(customer);
                } else {
                    serveWithExpert(customer);
                }
                index++;
            }
            time++;
        }
        System.out.println("Finished at: t=" + time + " minutes");

    }

    private static void processCustomersArrivingAtSameTime(
            List<Customer> customerListArrivedAtSameTime, int time) {
        Customer customer = customerQueue.peek();
        while (customer != null
                && customer.getTimeArrived() == time) {
            customerListArrivedAtSameTime.add(customerQueue.poll());
            customer = customerQueue.peek();
        }

    }

    private static void serveWithExpert(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null && cust.decreaseItems() == 0) {
            customer.poll();
        }
    }

    private static void serveWithTrainee(Queue<Customer> customer) {
        Customer cust = customer.peek();
        if (cust != null) {
            if (!cust.isInService()) {
                cust.setInService(true);
            } else {
                if (cust.decreaseItems() == 0) {
                    customer.poll();
                } else {
                    cust.setInService(false);
                }
            }
        }

    }

    private static GroceryMain initiaize(String[] args) {
        GroceryMain groceryMain = null;
        Grocery grocery = null;
        String line = "";
        int firstLine = 0;
        BufferedReader buReader = null;
        try {
            buReader = new BufferedReader(
                    new FileReader(new File(args[0])));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while ((line = buReader.readLine()) != null) {
                if (firstLine == 0) {
                    int noofRegisters = Integer.parseInt(line);
                    grocery = new Grocery(noofRegisters);
                } else {
                    Customer customer = readCustomers(line);
                    customerQueue.offer(customer);
                }
                firstLine++;
            }
            groceryMain = new GroceryMain(grocery);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return groceryMain;
    }

    private static Customer readCustomers(String line) {
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
