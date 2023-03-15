package Vlezna1Try2;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

class NumberProcessor{
    List<Integer> numbers;
    TreeMap<Integer, Integer> nums;

    public NumberProcessor() {
        numbers = new ArrayList<>();
        nums = new TreeMap<>();
    }

    private void getBiggestNumberAndCoef(List<Integer> list) {
        int num = 0;
        int numCounter = 0;
        boolean addNum = true;

        for (int i = 0; i < list.size(); i++) {
            num = list.get(i);
            for (int j = 0; j < list.size(); j++) {
                if(num == list.get(j))
                    numCounter++;
            }
            nums.put(num, numCounter);
            numCounter = 0;
        }

        int biggestNum = nums.lastKey();
        int bigVal = nums.get(biggestNum);

        for (int x : nums.values()){
            if (x > bigVal) {
                addNum = false;
                break;
            }
        }

        if(addNum)
            numbers.add(biggestNum);

//        System.out.println("Biggest num is " + biggestNum + " Value: " + bigVal);
//        System.out.println(nums);
    }

    public void readRows(InputStream in) {
        Scanner scanner = new Scanner(in);
        List<Integer> list = new ArrayList<>();

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String [] parts = line.split("\\s+");

            for(String i : parts){
                list.add(Integer.parseInt(i));
            }
            getBiggestNumberAndCoef(list);
            list = new ArrayList<>();
            nums = new TreeMap<>();
        }
    }

    public void printMaxFromRows(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        for(Integer i : numbers){
            pw.println(i);
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