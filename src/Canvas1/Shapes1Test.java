package Canvas1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}

class Shape {
    //364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
    public String id;
    public List<Integer> sizes;

    public Shape(String id) {
        this.id = id;
    }

    public Shape(String id, List<Integer> sizes) {
        this.id = id;
        this.sizes = sizes;
    }

    public int squares_count() {
        return sizes.size();
    }

    public int total_squares_perimeter() {
        int res = 0;
        for (int i = 0; i < sizes.size(); i++) {
            res += sizes.get(i);
        }

        res *= 4;
        return res;
    }

    @Override
    public String toString() {
        //364fbe94 14 1556
        return String.format("%s %d %d", id, squares_count(), total_squares_perimeter());
    }

}

class ShapesApplication {

    public List<Shape> shapes;

    public ShapesApplication() {
        shapes = new ArrayList<>();
    }

    int readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            String id = parts[0];
            List<Integer> sizes = new ArrayList<>();

            for (int i = 1; i < parts.length; i++) {
                sizes.add(Integer.parseInt(parts[i]));
            }

            shapes.add(new Shape(id, sizes));
        }

        int res = 0;
        for (int i = 0; i < shapes.size(); i++) {
            res += shapes.get(i).squares_count();
        }

        scanner.close();
//        return shapes.stream().mapToInt((Shape s) -> s.squares_count()).sum();
        return res;
    }

    void printLargestCanvasTo(OutputStream outputStream) {
        Shape maxShape = shapes.get(0);
        for(int i=1;i<shapes.size();i++)
        {
            if(shapes.get(i).total_squares_perimeter() > maxShape.total_squares_perimeter())
                maxShape = shapes.get(i);
        }
        PrintWriter printWriter=new PrintWriter(outputStream);
        printWriter.print(maxShape);
        printWriter.flush();
    }

}