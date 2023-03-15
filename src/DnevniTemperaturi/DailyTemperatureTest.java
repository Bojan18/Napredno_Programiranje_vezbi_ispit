package DnevniTemperaturi;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

class Temp {
    public int day;
    public List<Double> temps;
    public char scale;

    public Temp(int day, List<Double> temps, char scale) {
        this.day = day;
        this.temps = temps;
        this.scale = scale;
    }

    public int getTempsSize() {
        return temps.size();
    }

    public int getDay() {
        return this.day;
    }

    public double getMinTemp() {
        return temps.stream().mapToDouble(x -> x).min().orElse(0);
    }

    public double getMaxTemp() {
        return temps.stream().mapToDouble(x -> x).max().orElse(0);
    }

    public double getAvgTemp() {
        return temps.stream().mapToDouble(x -> x).average().orElse(0);
    }

    @Override
    public String toString() {
        return String.format("%d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c", getDay(), getTempsSize(), getMinTemp(), scale, getMaxTemp(),scale , getAvgTemp(), scale);
    }

    public void convertToF() {
        //(T∗9/5)+32
        for (int i = 0; i < temps.size(); i++) {
            temps.set(i, ((temps.get(i)*9) / 5) + 32);
        }
        this.scale = 'F';
    }

    public void convertToC() {
        //t-32 * 5 / 9
        for (int i = 0; i < temps.size(); i++) {
            temps.set(i, ((temps.get(i)-32) * 5) / 9);
        }
        this.scale = 'C';
    }
}

class DailyTemperatures {

    public Set<Temp> temperatures;

    public DailyTemperatures() {
        temperatures = new TreeSet<>(Comparator.comparing(Temp::getDay));
    }

    public void readTemperatures(InputStream inputStream) {
//        317 24C 29C 28C 29C
//        10  26C 28C 30C 22C
//        221 13F 11F 12F 18F
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            int day = Integer.parseInt(parts[0]);
            char scale = parts[1].charAt(parts[1].length() - 1);

            List<Double> newTemps = new ArrayList<>();

            for (int i = 1; i < parts.length; i++) {
                newTemps.add(Double.parseDouble(parts[i].substring(0, parts[i].length() - 1)));
            }

            temperatures.add(new Temp(day, newTemps, scale));
        }

    }

    void writeDailyStats(OutputStream outputStream, char scale) {
        PrintWriter pw = new PrintWriter(outputStream);

        for (Temp t : temperatures){
            if (t.scale != scale){
                if(scale == 'C') {
                    t.convertToC();
                }
                if(scale == 'F'){
                    t.convertToF();
                }
            }
            pw.println(t);
        }
        pw.flush();
    }

}