package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        try {
            List<Employee> employees = new ArrayList<>();

            // 1. Создаем DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Парсим XML файл в Document
            Document doc = builder.parse(new File(fileName));

            // 3. Получаем корневой элемент
            Node root = doc.getDocumentElement();

            // 4. Получаем список всех узлов "employee"
            NodeList nodeList = root.getChildNodes();

            // 5. Обрабатываем каждый узел
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                // Пропускаем текстовые узлы (переносы строк и т.д.)
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // 6. Извлекаем данные из XML
                    long id = Long.parseLong(getTagValue(element, "id"));
                    String firstName = getTagValue(element, "firstName");
                    String lastName = getTagValue(element, "lastName");
                    String country = getTagValue(element, "country");
                    int age = Integer.parseInt(getTagValue(element, "age"));

                    // 7. Создаем объект Employee и добавляем в список
                    employees.add(new Employee(id, firstName, lastName, country, age));
                }
            }
            return employees;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Не удалось проанализировать XML-файл: " + fileName, e);
        }
    }

    // Вспомогательный метод для получения значения тега
    private static String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        return node.getTextContent();
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