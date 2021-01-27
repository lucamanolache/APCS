package two;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TwoDimensionalArray {

    public static void main(String[] args) throws IOException {
        ArgumentParser parser = ArgumentParsers.newFor("TwoDimensionalArray").build()
                .defaultHelp(true)
                .description("Read a two dimensional array from a file");
        parser.addArgument("file").nargs(1)
                .help("File to read the 2d array from");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        Path filePath = Path.of(ns.getString("file").replaceAll("\\[|\\]", ""));
        if (filePath.toString().equals("")) {
            throw new IllegalArgumentException("Needs a valid file name, got \"\"");
        }
        BufferedReader in = new BufferedReader(new FileReader(filePath.toAbsolutePath().toString()));
        StringTokenizer str = new StringTokenizer(in.readLine(), ",");

        if (str.countTokens() != 2) {
            throw new RuntimeException("File needs to have the size on the first line");
        }
        int rows = Integer.parseInt(str.nextToken()), cols = Integer.parseInt(str.nextToken());
        double[][] arr = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            str = new StringTokenizer(in.readLine(), ",");
            for (int j = 0; j < cols; j++) {
                arr[i][j] = Double.parseDouble(str.nextToken());
            }
        }

        double sum = 0;
        for (double[] d : arr) {
            for (double dd : d) {
                sum += dd;
            }
        }
        System.out.println(sum);
    }
}
