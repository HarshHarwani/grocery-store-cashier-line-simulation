package org.wellaware.grocery;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author hharwani
 * Every Register has a queue representing the queue of customers and a index denoting its index
 * There are two comparators which compare the registers by index and size.
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
    public void setCustomerList(Queue<Customer> customerList) {
        this.customerList = customerList;
    }
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }
    public static Comparator<Register> getSizeComparator() {
        return sizeComparator;
    }
    public static void setSizeComparator(
            Comparator<Register> sizeComparator) {
        Register.sizeComparator = sizeComparator;
    }

    // to get the smallest register in terms of size, i.e the number of
    // customers in each register
    public static Comparator<Register> sizeComparator = new Comparator<Register>() {

        @Override
        public int compare(Register o1, Register o2) {
            Integer size = o1.customerList.size();
            Integer size1 = o2.customerList.size();
            return size.compareTo(size1);
        }
    };
    // to get the smallest register by index
    @Override
    public int compareTo(Register o) {
        return this.index.compareTo(o.index);
    }
}
