package LinkedList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
Create a custom built linked list class to implement a stack and create a push, pop, and peek function for the class.
The Node class for the linked list should have a string variable and a next pointer.
Write a function outside of the linked list and node classes to convert postfix expressions to prefix expressions using a stack.
 */
/**
 * Created by diempham on 5/4/17.
 */

public class Main {

    public static void main(String[] args) {
	    Stack stack = new Stack();
	    String s = "A B * C D * +";
	    postFixToPreFix(s, stack);
	    stack.print();
    }

    //convert postFix to preFix
    public static void postFixToPreFix(String string, Stack stack){
        String[] arrayOfString = string.split(" ");
        for(int i = 0; i < arrayOfString.length; i++){
            if(!isOperator(arrayOfString[i])){
                Node newNode = new Node(arrayOfString[i]);
                stack.push(newNode);
            } else {
                Node node1 = stack.pop();
                Node node2 = stack.pop();
                String newString = arrayOfString[i].concat(node2.string).concat(node1.string);
                stack.push(new Node(newString));
            }
        }
    }

    //check whether a string is an operator
    public static boolean isOperator(String string){
        String pattern = "[-+*/]";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(string);
        return matcher.matches();
    }
}
