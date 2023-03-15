package MernaStanica;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class Measurment implements Comparable<Measurment>{
    //22.1 18.9 1.3 24.6
    //температура: 13 степени
    //влажност: 98%
    //ветар: 11.2 km/h
    //видливост: 14 km
    //време: 28.12.2013 14:37:55 (dd.MM.yyyy HH:mm:ss).
    public float temp;
    public float humidity;
    public float wind;
    public float vision;
    public Date date;

    public Measurment(float temp, float humidity, float wind, float vision, Date date) {
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.vision = vision;
        this.date = date;
    }

    @Override
    public String toString() {
        //Tue Dec 17 23:35:15 GMT 2013
        DateFormat df = new SimpleDateFormat("Eee MMM dd : HH:mm:ss zzz yyy");
        //41.8 9.4 km/h 40.8% 20.7 km Tue Dec 17 23:35:15 GMT 2013
        //temp  hum      wind  vis     date
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temp, humidity, wind, vision, df.format(date));
    }

    @Override
    public int compareTo(Measurment o) {
        long seconds = date.getTime() / 1000;
        long seconds2 = o.date.getTime() / 1000;

        return date.compareTo(o.date);
    }
}

class WeatherStation{

    public TreeSet<Measurment> measurments;
    public int days;

    public WeatherStation(int days) {
        this.days = days;
        measurments = new TreeSet<>();
    }

    public void addMeasurment(float temp, float wind, float hum, float vis, Date date) {
        measurments.add(new Measurment(temp, wind, hum, vis, date));
    }

    public int total(){
        return measurments.size();
    }

    public void status(Date from, Date to){
        //ги печати сите мерења во периодот од from до to подредени
        //според датумот во растечки редослед и на крај ја печати просечната
        //температура во овој период. Ако не постојат мерења во овој период се
        //фрла исклучок од тип RuntimeException (вграден во Јава).

    }

}
