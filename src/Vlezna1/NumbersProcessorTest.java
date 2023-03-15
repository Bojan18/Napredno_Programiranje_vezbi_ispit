package Vlezna1;

import java.io.*;
import java.util.*;

class NumberProcessor {

    List<Integer> numbers;
    HashMap<Integer, Integer> nums;

    public NumberProcessor(){
        numbers = new ArrayList<>();
        nums = new HashMap<>();
    }

    public void getBIggestNumberWithCoef(List<Integer> list1){
        int biggestNumber = list1.get(0);
        int biggestCounter = 0;
        boolean done = true;
        int selectedElement = list1.get(0);
        int selectedCounter = 0;

        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i) > biggestNumber){
                biggestNumber = list1.get(i);
            }
        }

        for (int i = 0; i < list1.size(); i++) {
            if(biggestNumber == list1.get(i)){
                biggestCounter++;
            }
        }

        for (int i = 0; i < list1.size(); i++) {
            selectedElement = list1.get(i);
            for (Integer integer : list1) {
                if (selectedElement == integer) {
                    selectedCounter++;
                }
            }
            nums.put(selectedElement, selectedCounter);
            selectedCounter = 0;
        }

        System.out.println(nums);

        for (Integer n : nums.values()){
            if (n > biggestCounter) {
                done = false;
                break;
            }
        }

        if(done)
            numbers.add(biggestNumber);

    }

    public void readRows(InputStream in) {
        Scanner scanner = new Scanner(in);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String [] parts = line.split("\\s+");

            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                list.add(Integer.parseInt(parts[i]));
            }
            getBIggestNumberWithCoef(list);

        }

    }

    public void printMaxFromRows(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        for (Integer x : numbers){
            pw.println(x);
        }
        pw.flush();
    }
}

public class NumbersProcessorTest {

    public static void main(String[] args) throws IOException {

        NumberProcessor numberProcessor = new NumberProcessor();

        numberProcessor.readRows(System.in);

        numberProcessor.printMaxFromRows(System.out);

    }
}