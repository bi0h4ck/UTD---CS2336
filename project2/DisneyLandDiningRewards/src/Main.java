import Customer.*;


import java.io.IOException;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.pow;

/**
 * Created by diempham on 2/18/17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ProcessOrder processOrder = new ProcessOrder();
        processOrder.run();
    }
}

class ProcessOrder {
    // Result class has newArrayOfPreferredLines and newArrayOfCustomerLines
    protected class Result {
        protected String[] newArrayOfPreferredLines;
        protected String[] newArrayOfCustomerLines;

        protected Result(String[] newArrayOfCustomerLines, String[] newArrayOfPreferredLines) {
            this.newArrayOfCustomerLines = newArrayOfCustomerLines;
            this.newArrayOfPreferredLines = newArrayOfPreferredLines;
        }
    }

    public void run() throws IOException {
        //Wrap file name into File
        File transactionFileName = new File("orders.dat");
        File preferredFileName = new File("preferred.dat");
        File customerFileName = new File("customer.dat");

        //Read files into array of lines of string
        String[] arrayOfCustomerLines = readFileIntoArrayOfLine(customerFileName);
        String[] arrayOfTransactionLines = readFileIntoArrayOfLine(transactionFileName);
        String[] arrayOfPreferredLines;

        //put each line into an array of object of Customer/Preferred
        Customer[] arrayOfCustomers = readCustomerFile(arrayOfCustomerLines);
        PreferredCustomer[] arrayOfPreferred;

        Result result = new Result(arrayOfCustomerLines, arrayOfCustomerLines);

        //Check whether the preferred exists
        boolean preferredFileExists = preferredFileName.exists();

        //If the preferred file exists
        if (preferredFileExists) {
            arrayOfPreferredLines = readFileIntoArrayOfLine(preferredFileName);
            arrayOfPreferred = readPreferredFile(arrayOfPreferredLines);
            for (int count = 0; count < arrayOfTransactionLines.length; count++) {
                //get customer ID of the transaction
                int ID = ID(arrayOfTransactionLines[count]);

                // calculate how much the customer spent
                double amountSpent = amountSpent(arrayOfTransactionLines[count]);

                //get the index of the ID in customer or preferred
                int isCustomer = isCustomer(arrayOfCustomers, ID);
                int isPreferred = isPreferred(arrayOfPreferred, ID);

                //If the ID belongs to preferred, call the processPreferred function
                if (isCustomer < 0 && isPreferred >= 0) {
                    processPreferred(arrayOfPreferred, isPreferred, amountSpent, preferredFileName);
                    // If the ID belongs to customer, call the processCustomer function
                } else if (isCustomer >= 0 && isPreferred < 0) {
                    result = processCustomer(arrayOfCustomers, arrayOfCustomerLines, arrayOfPreferredLines,
                            isCustomer, amountSpent);
                }
            }
            writeToFile(result.newArrayOfCustomerLines, customerFileName);
            writeToFile(result.newArrayOfPreferredLines, preferredFileName);
        }
        //If the preferred file does not exist or exist but is empty
        else if ((!preferredFileExists) || preferredFileName.length() <= 0) {
            arrayOfPreferredLines = new String[1];
            //If the preferred file does not exist, create one
            if (!preferredFileExists) {
                preferredFileName.createNewFile();
            }

            for (int count = 0; count < arrayOfTransactionLines.length; count++) {
                //get customer ID of the transaction
                int ID = ID(arrayOfTransactionLines[count]);

                // calculate how much the customer spent
                double amountSpent = amountSpent(arrayOfTransactionLines[count]);
                int isCustomer = isCustomer(arrayOfCustomers, ID);
                //Process customer
                result = processCustomer(arrayOfCustomers, arrayOfCustomerLines, arrayOfPreferredLines,
                        isCustomer, amountSpent);
            }
            //If the preferred file is empty, delete it
            if (preferredFileName.length() <= 0) {
                preferredFileName.delete();
            }
            writeToFile(result.newArrayOfCustomerLines, customerFileName);
        }
    }

    // Write the updated array of customer into a temporary file,
    // then delete the old file, and use the old name to rename the new
    public void writeToFile(String[] array, File oldFile) throws IOException {
        String tmp = "tmp.txt";
        BufferedWriter out = new BufferedWriter((new FileWriter(tmp)));
        for (int count = 0; count < array.length; count++) {
            out.write(array[count]);
            out.write("\n");
        }
        out.flush();
        out.close();

        oldFile.delete();

        File newFile = new File(tmp);
        newFile.renameTo(oldFile);

    }

    //This function is called when the customer ID in transaction belongs to the regular customers
    public Result processCustomer(Customer[] arrayOfCustomer, String[] arrayOfCustomerLines,
                                  String[] arrayOfPreferredLines, int isCustomer, double amountSpent) {

        //Add the current amount spent into the previous amount spent
        arrayOfCustomer[isCustomer].updateAmountSpent(amountSpent);

        //If the customer gets promoted
        if (arrayOfCustomer[isCustomer].isPromoted()) {

            //move the customer from regular to preferred
            arrayOfPreferredLines = moveToPreferred(arrayOfCustomer[isCustomer], arrayOfPreferredLines);
            //PreferredCustomer[] newArrayOfPreferred = readPreferredFile(newArrayOfPreferredLines);

            //remove that customer in the regular customer array
            arrayOfCustomerLines = removePromotedCustomer(arrayOfCustomerLines, isCustomer);
            //Customer[] newArrayOfCustomer = readCustomerFile(newArrayOfCustomerLines);

            return new Result(arrayOfCustomerLines, arrayOfPreferredLines);
        } else {
            for (int i = 0; i < arrayOfCustomerLines.length; i++) {
                arrayOfCustomerLines[i] = arrayOfCustomer[i].toString();
            }
            return new Result(arrayOfCustomerLines, arrayOfPreferredLines);
        }

    }

    //This function is called when the customer ID in the transaction file belongs to preferred customers
    public void processPreferred(PreferredCustomer[] arrayOfPreferred, int isPreferred,
                                 double amountSpent, File fileName) throws IOException {
        //get the discount percentage
        double discount = arrayOfPreferred[isPreferred].getDiscountPercentage();
        //get the amount spent after discount
        double amountAfterDiscount = amountSpent * discount;
        //update the amountSpent
        arrayOfPreferred[isPreferred].updateAmountSpent(amountAfterDiscount);
        //update the discount percentage
        arrayOfPreferred[isPreferred].updateDiscountPercentage();
        String[] newArrayOfPreferredLines = new String[arrayOfPreferred.length];
        //loop through the array of preferred Customer, and convert each object into string
        for (int count = 0; count < arrayOfPreferred.length; count++) {
            newArrayOfPreferredLines[count] = arrayOfPreferred[count].toString();
        }
        //write the array of updated preferred customers into a file
        writeToFile(newArrayOfPreferredLines, fileName);
    }

    //Remove the promoted customer out of the regular customer array
    public String[] removePromotedCustomer(String[] arrayOfCustomerLines, int isCustomer) {
        //the new array size decreases by 1
        int newArraySize = arrayOfCustomerLines.length - 1;
        String[] newArrayOfCustomerLines = new String[newArraySize];
        System.out.println("length: " + (arrayOfCustomerLines.length - 1));
        System.out.println("is Customer: " + isCustomer);

        //If the removed customer locates at the end of the file
        if (isCustomer == arrayOfCustomerLines.length - 1)
            //copy the old array into a new array except the last customer
            System.arraycopy(arrayOfCustomerLines, 0, newArrayOfCustomerLines, 0, isCustomer);
            //If the removed customer locates at the beginning of the file
        else if (isCustomer == 0)
            //start to copy from the second element in the old array into a new array
            System.arraycopy(arrayOfCustomerLines, 1, newArrayOfCustomerLines, 0, newArraySize);
        //If the removed customer locates in the middle of the file
        if (isCustomer != 0 && isCustomer < newArraySize)
            //copy from the beginning of the old array into the new array except the promoted customer
            System.arraycopy(arrayOfCustomerLines, 0, newArrayOfCustomerLines, 0, isCustomer);
        System.arraycopy(arrayOfCustomerLines, isCustomer + 1, newArrayOfCustomerLines, isCustomer,
                newArraySize);

        return newArrayOfCustomerLines;
    }

    //Move to promoted customer to preferred array
    public String[] moveToPreferred(Customer customer, String[] arrayOfPreferred) {

        PreferredCustomer newPreferred = new PreferredCustomer(customer.getID(), customer.getFirstName(),
                customer.getLastName(), customer.getAmountSpent(), 0.0);
        // get the discount percentage based on the total amountSpent
        newPreferred.updateDiscountPercentage();
        // increase the new array size by 1
        int newArraySize = arrayOfPreferred.length + 1;

        String[] newArrayOfPreferred = new String[newArraySize];
        //copy all the preferred customers into a new array
        System.arraycopy(arrayOfPreferred, 0, newArrayOfPreferred, 0, newArraySize - 1);
        //copy the new promoted customer at the end of the new array
        newArrayOfPreferred[newArraySize - 1] = newPreferred.toString();

        return newArrayOfPreferred;
    }

    //check whether the customer in the transaction belongs to regular customer
    public int isCustomer(Customer[] arrayOfCustomer, int ID) {
        int isCustomer = -1;
        int c = 0;
        while (isCustomer == -1 && c < arrayOfCustomer.length) {
            if (ID == arrayOfCustomer[c].getID())
                //get the index where the ID in transaction matches the ID in regular customer file
                isCustomer = c;
                // if the customer is not the the regular customers file, return -1
            else isCustomer = -1;
            c++;
        }
        return isCustomer;
    }

    //check whether the customer in the transaction belongs to preferred customers
    public int isPreferred(PreferredCustomer[] arrayOfCustomer, int ID) {
        int isCustomer = -1;
        int c = 0;
        while (isCustomer == -1 && c < arrayOfCustomer.length) {
            if (ID == arrayOfCustomer[c].getID())
                //get the index where the ID in transaction matches the ID in preferred customer file
                isCustomer = c;
                // if the customer is not the the preferred customers file, return -1
            else isCustomer = -1;
            c++;
        }
        return isCustomer;
    }

    //read a file into an array of String
    public String[] readFileIntoArrayOfLine(File filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    //process each line into a customer object
    public Customer[] readCustomerFile(String[] arrayOfCustomerLines) {
        Customer[] arrayOfCustomers = new Customer[arrayOfCustomerLines.length];
        for (int count = 0; count < arrayOfCustomerLines.length; count++) {
            String[] strArray = arrayOfCustomerLines[count].split(" ");
            int ID = Integer.parseInt(strArray[0]);
            String firstName = strArray[1];
            String lastName = strArray[2];
            double amountSpent = (Double.parseDouble(strArray[3]));

            arrayOfCustomers[count] = new Customer(ID, firstName, lastName, amountSpent);
        }
        return arrayOfCustomers;
    }

    //process each line into a preferred object
    public static PreferredCustomer[] readPreferredFile(String[] arrayOfPreferredCustomerLines) {
        PreferredCustomer[] arrayOfPreferred = new PreferredCustomer[arrayOfPreferredCustomerLines.length];
        for (int count = 0; count < arrayOfPreferredCustomerLines.length; count++) {
            String[] strArray = arrayOfPreferredCustomerLines[count].split(" ");
            int ID = Integer.parseInt(strArray[0]);
            String firstName = strArray[1];
            String lastName = strArray[2];
            double amountSpent = Double.parseDouble(strArray[3]);
            double discountPercentage = (Double.parseDouble(strArray[4].split("%")[0]) / 100);

            arrayOfPreferred[count] = new PreferredCustomer(ID, firstName, lastName, amountSpent, discountPercentage);
        }
        return arrayOfPreferred;
    }

    //get the amountSpent for each transaction
    public static double amountSpent(String line) {
        String[] strArray = line.split(" ");
        double radius = Double.parseDouble(strArray[1]);
        double height = Double.parseDouble(strArray[2]);
        double ounces = Double.parseDouble(strArray[3]);
        double ouncePrice = Double.parseDouble(strArray[4]);
        double squareInchPrice = Double.parseDouble(strArray[5]);
        int quantity = Integer.parseInt(strArray[6]);
        //calculate the amountSpent
        double amountSpent = priceCalculation(radius, height, ounces, ouncePrice, squareInchPrice, quantity);

        return amountSpent;
    }

    //get the customer ID for each transaction
    public static int ID(String line) {
        String[] strArray = line.split(" ");
        int ID = Integer.parseInt(strArray[0]);
        return ID;
    }

    //calculate the drink and/or design of each transaction
    public static double priceCalculation(double radius, double height, double ounces, double ouncePrice,
                                          double squareInchPrice, int quantity) {
        NumberFormat formatter = new DecimalFormat("##.##");
        double containerSize = 2 * Math.PI * pow(radius, 2) + 2 * Math.PI * radius * height;
        double drinkPrice = ounces * ouncePrice;
        double designPrice = containerSize * squareInchPrice;
        double total = Double.parseDouble(formatter.format((drinkPrice + designPrice) * quantity));
        return total;
    }
}