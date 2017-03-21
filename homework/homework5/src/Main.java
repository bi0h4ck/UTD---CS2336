import StackAndQueue.Node;
import StackAndQueue.Queue;
import StackAndQueue.Stack;

import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException {

	File file = new File("file.txt");
	File file2 = new File("file2.txt");
	popCharFromStack(file);
	dequeueCharToUpper(file);
	compareTwoFile(file, file2);

    }

    public static File popCharFromStack(File fileName) throws IOException {
        File newFile = new File("reverse.txt");
        //Scanner scanner = new Scanner(newFile);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        Stack stack = new Stack();
        //read file into a stack of char
        while((line = reader.readLine()) != null){
            line = line.trim();
            for(int i = 0; i < line.length(); i++){
                stack.push(new Node(line.charAt(i)));
            }
            stack.push(new Node('\n'));

        }
        stack.pop();
        //pop the characters from the stack and save them in a new file in a reverse order
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        while(stack.top != null){
            writer.write(stack.pop().getC());
        }
        writer.flush();
        writer.close();
        return newFile;
    }

    public static File dequeueCharToUpper(File fileName) throws IOException {
        File newFile = new File("upper.txt");

        Queue queue = readFileToQueue(fileName);
        //dequeue the characters from the queue and convert them to uppercase and write in a new file
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        while (queue.head != null){
            writer.write(Character.toUpperCase((queue.dequeue(queue.getHead()).getC())));
        }
        writer.flush();
        writer.close();
        return newFile;
    }

    public static void compareTwoFile(File file1, File file2) throws IOException {
        boolean identical = true;
        Queue queue1 = readFileToQueue(file1);
        Queue queue2 = readFileToQueue(file2);
        if(queue1.getSize() != queue2.getSize()){
            identical = false;
        } else{
            while(queue1.head != null && queue2.head != null && (identical)){
                if(queue1.dequeue(queue1.getHead()).getC() != queue2.dequeue(queue2.getHead()).getC())
                    identical = false;
            }
        }
        if(identical)
            System.out.println("The files are identical");
        else
            System.out.println("The files are not identical");

    }

    public static Queue readFileToQueue(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        Queue queue = new Queue();
        //read file into a queue of char
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            for (int i = 0; i < line.length(); i++) {
                queue.enqueue(new Node(line.charAt(i)));
            }
            queue.enqueue(new Node('\n'));
        }
        return queue;
    }
}































