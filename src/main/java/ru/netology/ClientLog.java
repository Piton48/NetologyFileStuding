package ru.netology;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> data = new ArrayList();

    public void log(int productNum, int amount) {
        data.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV(File file) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            for (String[] record : data) {
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




