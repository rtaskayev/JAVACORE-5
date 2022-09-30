import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String fileName = "data.xml";
        String outputJsonFileName = "data2.json";

        List<Employee> list = parseXML(fileName);
        System.out.println("Parsed objects from XML - " + list);

        String json = listToJson(list); // Converting list of objects to Json data
        System.out.println("Json data - " + json);

        writeString(json, outputJsonFileName); // Write json data to file

    }

    //Method to parse xml into list of objects
    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();

        System.out.println("Root element: " + root.getNodeName());

        NodeList nodeList = doc.getElementsByTagName("employee");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("Current Element :" + node.getNodeName());

                // get text
                long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String firstname = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastname = element.getElementsByTagName("lastName").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());

                System.out.println("id : " + id);
                System.out.println("First Name : " + firstname);
                System.out.println("Last Name : " + lastname);
                System.out.println("Country : " + country);
                System.out.println("Age : " + age);

                Employee emp = new Employee(id, firstname, lastname, country, age);
                list.add(emp);
            }
        }
        return list;
    }


    // Method to transfer list of objects to json
    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(list);
    }

    // Method to write json data into file
    public static void writeString(String stringToWrite, String fileName) {
        try (FileWriter file = new
                FileWriter(fileName)) {
            file.write(stringToWrite);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
