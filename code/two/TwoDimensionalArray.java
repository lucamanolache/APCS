package two;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TwoDimensionalArray {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("TwoDimensionalArray").build()
                .defaultHelp(true)
                .description("Read a two dimensional array from a file");
        parser.addArgument("task")
                .dest("task")
                .help("What task should this program complete")
                .choices("read", "write", "randomize", "sum")
                .setDefault("read")
                .nargs(1);
        parser.addArgument("file").nargs(1)
                .help("File to read the 2d array from")
                .required(true);
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String filePath = Path.of(ns.getString("file").replaceAll("\\[|\\]", ""))
                .toAbsolutePath().toString();
        String task = ns.getString("task");

        switch (task) {
            case "[read]": read(filePath); break;
            case "[write]": write(filePath); break;
            case "[randomize]": randomize(filePath); break;
            case "[sum]": sum(filePath); break;
        }
    }

    private static void read(String file) {
        FileMatrix<Double> array = null;
        try {
            array = new FileMatrix<>(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getCols(); j++) {
                System.out.printf("%s ", array.get(i, j));
            }
            System.out.print("\n");
        }
    }

    private static void write(String file) {
        Scanner in = new Scanner(System.in);

        System.out.print("How many rows: ");
        int rows = in.nextInt();
        System.out.print("How many cols: ");
        int cols = in.nextInt();
        System.out.print("\n");

        FileMatrix<Double> array = new FileMatrix<>(rows, cols, file);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                array.set(row, col, in.nextDouble());
            }
        }
    }

    private static void randomize(String file) {
        Scanner in = new Scanner(System.in);

        System.out.print("How many rows: ");
        int rows = in.nextInt();
        System.out.print("How many cols: ");
        int cols = in.nextInt();
        System.out.print("\n");

        System.out.print("What is the min number: ");
        int min = in.nextInt();
        System.out.print("What is the max number: ");
        int max = in.nextInt();
        System.out.print("\n");

        if (min > max) {
            // swapping the two numbers with O(1) space
            max = min * max;
            min = max;
            max = max / min;
        }

        FileMatrix<Double> array = new FileMatrix<>(rows, cols, file);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                array.set(row, col, min + Math.random() * (max - min));
            }
        }
    }

    private static void sum(String file) {
        FileMatrix<Double> array = null;
        try {
            array = new FileMatrix<>(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        double sum = 0;
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getCols(); j++) {
                sum += array.get(i, j);
            }
        }

        System.out.println(sum);
    }
}
