// Program to implement a Java method which reads a text file and counts how often words occur.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReaderWordCount {
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Integer> wordCount=new HashMap<>();

        Scanner readFile= new Scanner(new File("/Users/Naincy/Documents/gpl-3.0.txt"));

        while(readFile.hasNext()){
            String word= readFile.next().toLowerCase()
                    .replaceAll("[^\\s\\-a-zA-Z0-9]", "");

            if(wordCount.containsKey(word)){
                int count=wordCount.get(word)+1;
                wordCount.put(word,count);
            }
            else{
                wordCount.put(word,1);
            }
        }

        readFile.close();

       // wordCount.forEach((K,V) -> System.out.println("'"+ K + "'"+  ":" + V + ","));

        mapDisplay(wordCount);
    }

    public static void mapDisplay(Map<String, Integer> counter)
    {
        System.out.print("{");
        for (Map.Entry<String, Integer> entry : counter.entrySet())
            System.out.print("'" + entry.getKey() + "'" +
                    ":" + entry.getValue()+",");
        System.out.print("\b}");
    }
}
