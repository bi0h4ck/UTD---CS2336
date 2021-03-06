package Customer;

/**
 * Created by Diem Pham on 2/11/17.
 * dtp160130
 */
public class Customer {
    public String firstName;
    public String lastName;
    public int ID;
    public double amountSpent;

    public Customer(int ID, String firstName, String lastName, double amountSpent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.amountSpent = amountSpent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public double updateAmountSpent(double spent){
        this.amountSpent += spent;
        return this.amountSpent;
    }

    public boolean isPromoted(){
        boolean isPromoted;
        if (amountSpent >= 150.00)
            isPromoted = true;
        else
            isPromoted = false;
        return  isPromoted;
    }
    @Override
    public String toString(){
        String string = ID + " " + firstName + " " + lastName + " " + amountSpent;
        return string;
    }
}
