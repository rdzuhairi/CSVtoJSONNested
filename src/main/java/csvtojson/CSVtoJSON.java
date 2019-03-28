package csvtojson;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class CSVtoJSON {

    public static void main(String[] args)throws Exception{
        File input = new File("input.csv");
        File output = new File("output.json");

        BufferedReader bf = null;
        String line = "";
        String csvSplit = ",";
        boolean firstLine = true;
        ArrayList<String> list = new ArrayList<String>();
        JSONArray result = new JSONArray();

        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        try {
            bf = new BufferedReader(new FileReader(input));
            while (!(line = bf.readLine()).equals(null)){
                if (firstLine){
                    firstLine = false;
                    String[] headerCSV = line.split(csvSplit);

                    for (String header : headerCSV) {
                        list.add(header);
                        System.out.println(list);
                    }
                } else {
                    String[] valuesCSV = line.split(csvSplit);
                    JSONObject valuesObject = new JSONObject();

                    valuesObject.put(list.get(10), valuesCSV[10]);
                    valuesObject.put(list.get(11), valuesCSV[11]);
                    valuesObject.put("crawledDate", "ISODate(\"2018-11-27T05:07:59.110Z\")");

                    JSONObject nestedObject = new JSONObject();
                    for (int i = 0; i < (valuesCSV.length - 2); i++){
                        nestedObject.put(list.get(i), valuesCSV[i]);
                    }

                    valuesObject.put("data", nestedObject);
                    result.put(valuesObject);
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
//
//        // Read data from CSV file
//        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Write JSON formated data to output.json file
//        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);
//
//        // Write JSON formated data to stdout
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
    }
}
