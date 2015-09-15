package org.wellaware.grocery;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author hharwani 
 * Every Register has a queue representing the queue of
 * customers and a index denoting its index There are two comparators
 * which compare the registers by index and queue size, which will be helpful in getting the
 * register with smallest queue size and smallest register by index.
 */
public class Register implements Comparable<Register> {

    private Queue<Customer> customerList = null;
    private Integer index;

    public Register(int index) {
        this.index = index;
        customerList = new LinkedList<Customer>();
    }

    public Queue<Customer> getCustomerList() {
        return customerList;
    }

    public Integer getIndex() {
        return index;
    }
    /**
     * For sorting the Register Objects by their size, this will be useful for assigning
     * the correct register to Type A customers.
     */
    public static Comparator<Register> sizeComparator = new Comparator<Register>() {
        @Override
        public int compare(Register o1, Register o2) {
            Integer size = o1.customerList.size();
            Integer size1 = o2.customerList.size();
            return size.compareTo(size1);
        }
    };

    @Override
    /**
     * For sorting the Register Objects by their index, will be useful for assigning
     * correct register to both the type of customers depending on the situation. 
     */
    public int compareTo(Register o) {
        return this.index.compareTo(o.index);
    }
}
