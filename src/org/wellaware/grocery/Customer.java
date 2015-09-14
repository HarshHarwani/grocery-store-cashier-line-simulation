package org.wellaware.grocery;

/**
 * 
 * @author hharwani This is the customer class which will tell us what the type
 *         of customer it is, whether he is currently being served ot not ,what
 *         register he is at and what number of items it has
 *
 */
public class Customer implements Comparable<Customer> {

    private Integer itemCount; //used the wrapper in order to support compareTo function
    private Type type;
    private boolean inService;
    private int timeArrived;
    
    
    public Customer(Type type,int timeArrived,Integer itemCount) {
        this.type=type;
        this.timeArrived=timeArrived;
        this.itemCount=itemCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isInService() {
        return inService;
    }

    public void setInService(boolean inService) {
        this.inService = inService;
    }

    public int getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(int registerArrived) {
        this.timeArrived = registerArrived;
    }

    public Integer decreaseItems(){
        return --this.itemCount;
    }
    /**
     * 
     * @param @Customer
     * @return int
     *  comparing the customers based on their types, if that is same
     *  comparing them according to the number of items they have.
     */
    @Override
    public int compareTo(Customer o) {
        int val = 0;
            val = this.type.compareTo(o.type);
        if (val == 0) {
            val = this.itemCount.compareTo(o.itemCount);
        }
        return val;
    }

}

enum Type {
    A, B;
}
