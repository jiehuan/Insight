import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedWriter zipw = new BufferedWriter(new FileWriter(args[1]));

        String regex = "^[0-9]{5}$"; //data format to validate zipcode

        //create hashtable, key is either ID+zip code or ID+date;
        //value is an object containing two heaps created by "MedianFinder" class
        Hashtable<String, MedianFinder> table1 = new Hashtable<String, MedianFinder>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) //read input file
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] array = line.split("\\|");
                float amt = Float.parseFloat(array[14]); //covert value to float
                //check: 1. ID is not empty 2. amount is greater than zero 3. OTHER_ID is empty
                if (array[0].length()> 0 && amt > 0 && array[15].length() == 0)
				{
                    int median1;
                    int cnt1;
                    int sum1;
                    if (array[10].length() >=5) // zip code is at least 5 digit long.
                    {
                        String zip = array[10].substring(0,5);//only keep first 5 digits
                        if (zip.matches(regex))
                        {
                            MedianFinder tmp1 = new MedianFinder(); //create object for storing and processing data
                            String key1 = array[0] + "|"+ zip; //create kep for ID+zip code

                            if (table1.containsKey(key1))
                            {
                                tmp1 = table1.get(key1);
                                tmp1.addNum(amt);
                                cnt1 = tmp1.count();
                            }
                            else {
                                tmp1.addNum(amt);
                                cnt1 =1;
                            }

                            table1.put(key1, tmp1);
                            median1 = tmp1.findMedian();
                            sum1 = tmp1.sum();

                            //write ID+zip result to file
                            zipw.write(key1);
                            zipw.write("|");
                            zipw.write(Integer.toString(median1));
                            zipw.write("|");
                            zipw.write(Integer.toString(cnt1));
                            zipw.write("|");
                            zipw.write(Integer.toString(sum1));
                            zipw.newLine();
                        }
                    }
                }
            }
        }catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
        zipw.close();
    }
}

