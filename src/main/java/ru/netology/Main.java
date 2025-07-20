package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // CSV to JSON
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String csvFileName = "data.csv";
        List<Employee> csvList = parseCSV(columnMapping, csvFileName);
        String jsonFromCsv = listToJson(csvList);
        writeToFile(jsonFromCsv, "data.json");

        // XML to JSON
        String xmlFileName = "data.xml";
        List<Employee> xmlList = parseXML(xmlFileName);
        String jsonFromXml = listToJson(xmlList);
        writeToFile(jsonFromXml, "data2.json");

        String jsonFile = "data2.json";

        try {
            // Чтение JSON из файла
            String json = readString(jsonFile);

            // Преобразование JSON в список сотрудников
            List<Employee> list = jsonToList(json);

            // Вывод списка сотрудников в консоль
            for (Employee employee : list) {
                System.out.println(employee);
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // CSV
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            return csv.parse();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + fileName, e);
        }
    }

    private static String listToJson(List<Employee> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(list);
    }

    private static void writeToFile(String content, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + fileName, e);
        }
    }

    // XML
    private static List<Employee> parseXML(String fileName) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder xmlContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                xmlContent.append(line.trim());
            }

            String xml = xmlContent.toString();
            int pos = 0;

            while ((pos = xml.indexOf("<employee>", pos)) != -1) {
                int endPos = xml.indexOf("</employee>", pos);
                if (endPos == -1) break;

                String employeeXml = xml.substring(pos + "<employee>".length(), endPos);
                pos = endPos + "</employee>".length();

                long id = Long.parseLong(getTagValue(employeeXml, "id"));
                String firstName = getTagValue(employeeXml, "firstName");
                String lastName = getTagValue(employeeXml, "lastName");
                String country = getTagValue(employeeXml, "country");
                int age = Integer.parseInt(getTagValue(employeeXml, "age"));

                employees.add(new Employee(id, firstName, lastName, country, age));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file: " + fileName, e);
        }

        return employees;
    }

    private static String getTagValue(String xml, String tag) {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        int start = xml.indexOf(openTag);
        if (start == -1) return "";

        start += openTag.length();
        int end = xml.indexOf(closeTag, start);

        if (end == -1) return "";

        return xml.substring(start, end).trim();
    }

    // JSON
    private static String readString(String fileName) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return json.toString();
    }

    private static List<Employee> jsonToList(String json) {
        Gson gson = new GsonBuilder().create();
        Type employeeListType = new TypeToken<ArrayList<Employee>>(){}.getType();
        return gson.fromJson(json, employeeListType);
    }
}