package org.wellaware.grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * @author hharwani
 * RegisterCollection class will have a list of Registers which will be put
 * it in a registerList. The registerList will hold Register Objects.
 */
public class RegisterCollection {

    private List<Register> registerList = new ArrayList<Register>();
    
    /**
     * inititalize the RegisterCollection with the number of registers.
     * @param registers
     */
    public RegisterCollection(int registers) {
        for (int i = 0; i < registers; i++) {
            registerList.add(new Register(i));
        }
    }
    /**
     * List of all the registers in the grocery store.
     * @return {@link List<Register>}
     */
    public List<Register> getRegisters() {
        return registerList;
    }
    
    /**
     * to help type A customer choose a register
     * @return {@link Register}
     */
    public Register getShortRegisterBySize() {
        List<Register> sortedList = new ArrayList<Register>();
        for (Register register : registerList) {
            sortedList.add(register);
        }
        Collections.sort(sortedList, Register.sizeComparator);
        return sortedList.get(0);
    }
    
    /**
     * to help type A customer choose a register
     * @return {@link Register}
     */
    public Register getShortRegisterByIndex() {
        List<Register> sortedListByIndex = new ArrayList<Register>();
        for (Register register : registerList) {
            sortedListByIndex.add(register);
        }
        Collections.sort(sortedListByIndex);
        return sortedListByIndex.get(0);
    }

    /**
     * 
     * @return {@link Register}
     * this is specifically for type B customer which looks at the last customer of a register or
     * an empty register
     * complexity is O(n^2) need an efficient way to find last element.
     */
    public Register getRegisterLeastItemsEnd() {
        Map<Customer,Register> custRegMap=new HashMap<Customer,Register>();
        List<Register> emptyList = new ArrayList<Register>();
        List<Customer> listWithItems = new ArrayList<Customer>();
        for (Register register : registerList) {
            if (register.getCustomerList().size() == 0) {
                emptyList.add(register);
            } else {
                Customer lastCustomer=getLastElement(register.getCustomerList());
                custRegMap.put(lastCustomer, register);
                listWithItems.add(lastCustomer);
            }
        }
        if (emptyList.size() > 0) {
            Collections.sort(emptyList);
            return emptyList.get(0);
        } else {
            Collections.sort(listWithItems);
            return custRegMap.get(listWithItems.get(0));
        }
    }
    /**
     * helper method to get last element of the Customer Queue.
     * @param {@link Queue<Customer>}
     * @return {@link Customer}
     */
    private Customer getLastElement(Queue<Customer> customerList) {
        Customer lastCustomer=null;
        Iterator<Customer> iterator=customerList.iterator();
        while (iterator.hasNext()) {
            lastCustomer=iterator.next();
        }
        return lastCustomer;
    }
    /**
     * depending upon the type of customer and their constrains this method serves the right customer with right
     * kind of register.
     * @param customerList
     */
    public void serviceCustomer(List<Customer> customerList) {
        for (Customer customer : customerList) {
            if (customer.getType().equals(Type.A)) {
                Register shortestRegister = getShortRegisterBySize();
                shortestRegister.getCustomerList().offer(customer);
            } else {
                Register registerwithleastItems = getRegisterLeastItemsEnd();
                registerwithleastItems.getCustomerList().offer(customer);
            }
        }
    }
    /**
     * to check if any of the registers in the store is serving customers.
     * returns true if there are customers at any of the registers
     * false otherwise
     * @return boolean
     */
    public boolean isRegisterinService() {
        for (Register register : registerList) {
            if (register.getCustomerList().size() != 0)
                return true;
        }
        return false;
    }

}
