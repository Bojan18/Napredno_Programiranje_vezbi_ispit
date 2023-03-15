package F1Trka;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Driver implements Comparable<F1Driver> {
    //Driver_name lap1 lap2 lap3
    public String driverName;
    public String [] laps;

    F1Driver(String driverName, String lap1, String lap2, String lap3){
        this.driverName = driverName;
        laps = new String[3];
        laps[0] = lap1;
        laps[1] = lap2;
        laps[2] = lap3;
    }

    public String bestLap(){
        String best = laps[0];
        if(best.compareTo(laps[1]) > 0){
            best = laps[1];
        }
        if(best.compareTo(laps[2]) > 0){
            best = laps[2];
        }

        return best;
    }

    @Override
    public String toString() {
        //Alonso      1:53:563
        return String.format("%-10s%10s", driverName, bestLap());
    }

    @Override
    public int compareTo(F1Driver o) {
        return bestLap().compareTo(o.bestLap());
    }
}

class F1Race {

    List<F1Driver> drivers;

    public F1Race(){
        drivers = new ArrayList<>();
    }

    void readResults(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            F1Driver driver = new F1Driver(parts[0], parts[1], parts[2], parts[3]);
            drivers.add(driver);
        }
    }

    void printSorted(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(new PrintWriter(outputStream));
        Comparator<F1Driver> comparator = Comparator.comparing(F1Driver::bestLap);
        drivers.sort(comparator);
        for (int i = 0; i < drivers.size(); i++) {
            printWriter.println(String.format("%d. %s", i+1, drivers.get(i)));
        }
        printWriter.flush();
    }

}