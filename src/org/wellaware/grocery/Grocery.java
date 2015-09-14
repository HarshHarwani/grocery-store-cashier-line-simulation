package org.wellaware.grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author hharwani Grocery class will have a list of Registers which will put
 *         it in a registerList. The registerList will hold Register Objects.
 */
public class Grocery {

    private List<Register> registerList = new ArrayList<Register>();

    public Grocery(int registers) {
        for (int i = 0; i < registers; i++) {
            registerList.add(new Register(i));
        }
    }

    public List<Register> getRegisters() {
        return registerList;
    }

    public Register getShortestRegisterBySize() {
        List<Register> sortedList = new ArrayList<Register>();
        for (Register register : registerList) {
            sortedList.add(register);
        }
        Collections.sort(sortedList, Register.sizeComparator);
        return sortedList.get(0);
    }

    public Register getSmallestRegisterByIndex() {
        List<Register> sortedListByIndex = new ArrayList<Register>();
        for (Register register : registerList) {
            sortedListByIndex.add(register);
        }
        Collections.sort(sortedListByIndex);
        return sortedListByIndex.get(0);
    }

    /**
     * need to think about this method
     * 
     * @return
     */
    public Register getRegisterWithLeastItemsAtEnd() {
        // how to get the
        List<Register> emptyList = new ArrayList<Register>();
        List<Register> listWithItems = new ArrayList<Register>();
        for (Register register : registerList) {
            if (register.getCustomerList().size() == 0) {
                emptyList.add(register);
            } else {
                listWithItems.add(register);
            }
        }
        if (emptyList.size() > 0) {
            Collections.sort(emptyList);
            return emptyList.get(0);
        } else {
            return listWithItems.get(0);
        }

    }

    public void assignCustomer(List<Customer> customerList) {
        for (Customer customer : customerList) {
            if (customer.getType().equals(Type.A)) {
                Register shortestRegister = getShortestRegisterBySize();
                shortestRegister.getCustomerList().offer(customer);
            } else {
                Register registerwithleastItems = getRegisterWithLeastItemsAtEnd();
                registerwithleastItems.getCustomerList()
                        .offer(customer);
            }
        }
    }

    public boolean isCustomerWaitingInRegister() {
        for (Register register : registerList) {
            if (register.getCustomerList().size() != 0)
                return true;
        }
        return false;
    }

}
