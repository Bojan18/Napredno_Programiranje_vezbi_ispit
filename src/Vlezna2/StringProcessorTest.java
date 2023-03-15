package Vlezna2;
import java.io.*;
import java.util.*;

class StringProcessor{
    //najdi u koj red od string ima najveke char 'a'
    public String finalStr;
    public HashMap<String, Integer> stringMap;

    public StringProcessor() {
        stringMap = new HashMap<>();
    }

    public void getListWithCharA(List<String> list, char a){
        int charCounter = 0;
        for(String s : list){
            char [] parts = s.toCharArray();
            for (int i = 0; i < parts.length; i++) {
                if(parts[i] == a){
                    charCounter++;
                }
            }
            stringMap.put(s, charCounter);
            charCounter = 0;
        }

        Map.Entry<String, Integer> maxValue = null;

        for(Map.Entry<String, Integer> val : stringMap.entrySet())
        {
            if (maxValue == null || val.getValue().compareTo(maxValue.getValue()) > 0)
            {
                maxValue = val;
            }
        }

        finalStr = maxValue.getKey();
    }

    public void readRows(InputStream in, char a){
        Scanner scanner = new Scanner(in);
        List<String> list = new ArrayList<>();

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            list.add(line);
        }

        getListWithCharA(list, a);
    }

    public void printMaxFromRows(PrintStream out){
        PrintWriter pw = new PrintWriter(out);
        pw.println(finalStr);
        pw.flush();
    }

}

public class StringProcessorTest {

    public static void main(String[] args) throws IOException {

        StringProcessor stringProcessor = new StringProcessor();

        stringProcessor.readRows(System.in, 'a');

        stringProcessor.printMaxFromRows(System.out);

    }
}