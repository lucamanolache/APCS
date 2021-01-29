package two;

import java.io.IOException;
import java.nio.file.Path;

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

        FileMatrix<Double> array = new FileMatrix<>(3, 3, filePath.toAbsolutePath().toString());
//        array.set(0, 0, 1.0);
//        array.set(0, 1, 2.0);
//        array.set(0, 2, 3.0);
        System.out.println(array.get(0, 2));
    }
}
