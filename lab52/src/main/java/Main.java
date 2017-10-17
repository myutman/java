import org.omg.CORBA.MARSHAL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by myutman on 10/17/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String inputFilename = "src/main/resources/input.txt";
        String outputFilename = "src/main/resources/output.txt";

        Scanner scanner = new Scanner(new File(inputFilename));
        ArrayList<Maybe<Integer> > arrayList = new ArrayList<Maybe<Integer>>();
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            try {
                Integer num = Integer.parseInt(s);
                arrayList.add(Maybe.<Integer>just(num));
            } catch (NumberFormatException e){
                arrayList.add(Maybe.<Integer>nothing());
            }
        }

        PrintWriter printWriter = new PrintWriter(new File(outputFilename));
        for (Maybe<Integer> maybe: arrayList){
            Maybe<Integer> maybe1 = maybe.map(x -> x*x);
            try {
                printWriter.println(maybe1.get());
            } catch (NotPresentException e) {
                printWriter.println("null");
            }
        }
        printWriter.close();
    }
}
