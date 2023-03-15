package Discounts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

class StoreItem{
    public int discount;
    public int price;

    public StoreItem(int discount, int price) {
        this.discount = discount;
        this.price = price;
    }

    public double getDisc(StoreItem si){
        return (double) si.discount / si.price;
    }

    @Override
    public String toString() {
        return discount + " " + price;
    }
}

class Store{
    public String name;
    public List<StoreItem> storeItems;

    public Store(String name, List<StoreItem> storeItems) {
        this.name = name;
        this.storeItems = storeItems;
    }

    public String getName() {
        return name;
    }

    public int getTotalPrice(){
        int sum = 0;
        for(StoreItem si : storeItems){
            sum += si.price;
        }

        return sum;
    }
    public int getTotalDiscount(){
        int sum = 0;
        for(StoreItem si : storeItems){
            sum += si.discount;
        }

        return sum;
    }

    public long totalDiscount(){
        return getTotalPrice() - getTotalDiscount();
    }

    public String getDiscountItem(){
        StringBuilder sb = new StringBuilder();
        double disc = 0;
        for(StoreItem si : storeItems){
            disc = si.getDisc(si);
            disc = 1 - disc;
            disc *= 100;
            sb.append(String.format("%.0f", disc)).append("% ").append(si.discount).append("/").append(si.price).append('\n');
        }
        return sb.toString();
    }

    public double avgDiscount(){
        int sum = 0;
        int counter = 0;
        for(StoreItem si : storeItems) {
            double disc = si.getDisc(si);
            disc = 1 - disc;
            disc *= 100;
            counter++;
            sum += disc;
        }
        return (double) sum/counter;
    }

    @Override
    public String toString() {
        //Levis - name
        //Average discount: 35.8% - avgDiscount
        //Total discount: 21137 - totalDiscount, discount - price = totalDiscount
        //48% 2579/4985 - discount / price = 0.517, 1-0.517=0.483, 0.483*100 = 48#
        //47% 9988/19165
        //36% 7121/11287
        //35% 1501/2316
        //32% 6385/9497
        //17% 6853/8314 - [процент во две места]% [цена на попуст]/[цена]
        //Total discount = 34,427(discs) - 46,564(price) =

        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s", name, avgDiscount(), totalDiscount(),getDiscountItem());
    }
}

class Discounts{

    public List<Store> stores;

    public Discounts() {
        stores = new ArrayList<Store>();
    }

    public int readStores(InputStream in) {
        // line = Levis 6385:9497  9988:19165  7121:11287  1501:2316  2579:4985  6853:8314
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String [] parts = line.split("\\s+");
            //parts[0] = Levis
            //parts[1] = 6385:9497
            //parts[2] = 9988:19165
            //parts[3] = 7121:11287 ...
            String name = parts[0]; // - Levis
            List<StoreItem> storeItems = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                String [] parts2 = part.split(":"); //discount = parts2[0], price = parts2[1]
                StoreItem storeItem = new StoreItem(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
                storeItems.add(storeItem);

            }
            stores.add(new Store(name, storeItems));
        }
        return stores.size();
    }

    public List<Store> byAverageDiscount(){
        Comparator<Store> comparator = Comparator.comparing(Store::avgDiscount).thenComparing(Store::getName);

        return stores.stream().limit(3).sorted(comparator).collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount(){
        Comparator<Store> comparator = Comparator.comparing(Store::totalDiscount).thenComparing(Store::getName);
        //Апсолутен попуст е разликата од цената и цената на попуст.
        //cena - popust
        return stores.stream().limit(3).sorted(comparator).collect(Collectors.toList());
    }

}