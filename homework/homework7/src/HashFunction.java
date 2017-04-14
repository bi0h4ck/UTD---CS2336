//Diem Pham dtp160130

import java.util.Arrays;

public class HashFunction {
    String[] theArray;
    int arraySize;

    public static final double LOAD_FACTOR = 0.5;

    public static void main(String[] args) {
        HashFunction theFunc = new HashFunction();
        int threshold = (int)(LOAD_FACTOR * theFunc.arraySize);
        String[] elementsToAdd = {"100", "111", "759", "888", "991", "700", "677", "567", "399", "591"};
        theFunc.hashFunction(elementsToAdd, theFunc.theArray, threshold);
    }

    HashFunction(int size){
        arraySize = size;
        theArray = new String[size];
        Arrays.fill(theArray, "-1");
    }

    HashFunction(){
        arraySize = 9;
        theArray = new String[arraySize];
        Arrays.fill(theArray, "-1");
    }

    public void hashFunction(String[] arrayOfKeys, String[] hashTable, int threshold){
        for(int n = threshold - threshold; n < arrayOfKeys.length; n++){
            String key = arrayOfKeys[n];
            //key mod hashTable size = index of the key in the hashTable
            int index = Integer.parseInt(key) % hashTable.length;

            int count = 1;
            //while the inserted place is not available, using quadratic probing to get the available spot
            while(hashTable[index] != "-1"){
                index = quadraticProbing(key, count, hashTable.length);
                System.out.println("Collision try " + index + " for value " + key);
                //if the next index is out of range, rollover
                if(index > hashTable.length) {
                    index %= hashTable.length;
                    System.out.println(index + " > " + hashTable.length + " Roll over " + index);
                }
                count++;
            }
            hashTable[index] = key;
            threshold--;
            System.out.println("Modulus Index = " + index + " for value " + key + " threshold : " + threshold);
            //when hashmap reaches threshold -> rehash
            if(threshold == 0){
                rehash(arrayOfKeys, hashTable);
                return;
            }
        }
    }

    //(key * count^2)/hashTable.size
    public int quadraticProbing(String key, int count, int hashTableSize){
        //System.out.println("quadratic probing: " + (Integer.parseInt(key) + Math.pow(count, 2)) % hashTableSize + " at count = " + count);
        return (int)(Integer.parseInt(key) + Math.pow(count, 2)) % hashTableSize;
    }

    //double size of the array and rehash the hashTable
    public void rehash(String[] arrayOfKeys, String[] hashTable){
        int doubleInitialHashTableSize = hashTable.length * 2;
        HashFunction theFunc = new HashFunction(doubleInitialHashTableSize);
        int threshold = (int)(doubleInitialHashTableSize * LOAD_FACTOR);
        System.out.println("\nThreshold was reached, new hashTable with size " + doubleInitialHashTableSize);
        hashFunction(arrayOfKeys, theFunc.theArray, threshold);
    }
}
