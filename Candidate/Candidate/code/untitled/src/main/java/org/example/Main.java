package org.example;


import org.example.models.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: Read the Employee List.csv file and store the details of each employee // in a treemap
            TreeMap<String, Employee> employeeMap = new TreeMap<String, Employee>();

            FileReader employeeFile = new FileReader("C:\\Users\\eshan\\Downloads\\Candidate\\Candidate\\input\\Employee List.csv");
            BufferedReader employeeReader = new BufferedReader(employeeFile);

            String line = employeeReader.readLine(); // read header
            line = employeeReader.readLine();
            while (line != null) {
                String[] employeeDetails = line.split(",");
                Employee employee;
                if(employeeDetails.length == 6){
                    employee = new Employee(employeeDetails[0], employeeDetails[1], employeeDetails[2],
                            employeeDetails[3], employeeDetails[4], employeeDetails[5]);
                    employeeMap.put(employeeDetails[0], employee);
                }
                else{
                    employee = new Employee(employeeDetails[0], employeeDetails[1], employeeDetails[2],
                            employeeDetails[3], employeeDetails[4], "");
                    employeeMap.put(employeeDetails[0], employee);
                }
                line = employeeReader.readLine();
//                System.out.println(employeeDetails[0]+  employeeDetails[1]);
            }
            employeeReader.close();

            // Step 2: Read the Daily Rate.csv file and store the daily wage assigned for each employee in a hashmap
            HashMap<String, Double> dailyRateMap = new HashMap<String, Double>();
            BufferedReader dailyRateReader = new BufferedReader(new FileReader("C:\\Users\\eshan\\Downloads\\Candidate\\Candidate\\input\\Daily Rate.csv"));
            line = dailyRateReader.readLine(); // read header
            line = dailyRateReader.readLine();
            while (line  != null) {
                String[] dailyRateDetails = line.split(",");
                dailyRateMap.put(dailyRateDetails[0], Double.parseDouble(dailyRateDetails[1]));
                line = dailyRateReader.readLine();
//                System.out.println(dailyRateDetails[0]+" "+ dailyRateDetails[1]);
            }
            dailyRateReader.close();

            // Step 3: Read the Attendance 01-2023.csv file and store the attendance details for each employee for the month of January in a hashmap
            HashMap<String, Integer> attendance01Map = new HashMap<String, Integer>();
            BufferedReader attendance01Reader = new BufferedReader(new FileReader("C:\\Users\\eshan\\Downloads\\Candidate\\Candidate\\input\\Attendance 01-2023.csv"));

            line = attendance01Reader.readLine(); // read header
            line = attendance01Reader.readLine();
            while (line != null) {
                String[] attendanceDetails = line.split(",");

                int count = 0;
                for(int i = 1; i < attendanceDetails.length; i++){
                    if(attendanceDetails[i].equals("yes")){
                        count++;
                    }
//                    System.out.println(attendanceDetails[i].equals("yes"));
                }
//                System.out.println(count);
                attendance01Map.put(attendanceDetails[0], count);
                line = attendance01Reader.readLine();
            }
            attendance01Reader.close();
//
            // Step 4: Read the Attendance 02-2023.csv file and store the attendance details for each employee for the month of February in a hashmap
            HashMap<String, Integer> attendance02Map = new HashMap<String, Integer>();
            BufferedReader attendance02Reader = new BufferedReader(new FileReader("C:\\Users\\eshan\\Downloads\\Candidate\\Candidate\\input\\Attendance 02-2023.csv"));

            line = attendance02Reader.readLine(); // read header
            line = attendance02Reader.readLine();
            while (line != null) {
                String[] attendanceDetails = line.split(",");

                int count = 0;
                for(int i = 1; i < attendanceDetails.length; i++){
                    if(attendanceDetails[i].equals("yes")){
                        count++;
                    }
                }
//                System.out.println(count);
                attendance02Map.put(attendanceDetails[0], count);
                line = attendance02Reader.readLine();
            }
            attendance02Reader.close();


            // Step 5: Calculate the salary for each active employee for the month of January
            HashMap<String, Double> salary01Map = new HashMap<String, Double>();

            for (Map.Entry<String, Integer> x : attendance01Map.entrySet()) {
                String employeeId = x.getKey();
                int workingDays = x.getValue();

                Employee employee = employeeMap.get(employeeId);
                if (employee.getStatus().equals("Active")) {
                    double dailyRate = dailyRateMap.get(employeeId);
                    double salary = dailyRate * workingDays;
                    salary01Map.put(employeeId, salary);
                }
            }

            // Step 6: Calculate the salary for each active employee for the month of February
            HashMap<String, Double> salary02Map = new HashMap<String, Double>();

            for (Map.Entry<String, Integer> entry : attendance02Map.entrySet()) {
                String employeeId = entry.getKey();
                int workingDays = entry.getValue();
                Employee employee = employeeMap.get(employeeId);
                if (employee.getStatus().equals("Active")) {
                    double dailyRate = dailyRateMap.get(employeeId);
                    double salary = dailyRate * workingDays;
                    salary02Map.put(employeeId, salary);
                }
            }
//
            // Step 7: Write the output to a CSV file with name Salary.csv with 4 columns:
            // EmployeID, EmployeeName, Salary for 01-2023 and Salary for 02-2023. This file
            // should have one row per employee for the months of Jan and Feb 2023, sorted based
            // on Employee ID
            FileWriter file = new FileWriter("C:\\Users\\eshan\\Downloads\\Candidate\\Candidate\\output\\Salary.csv");
            BufferedWriter salaryWriter = new BufferedWriter(file);

            salaryWriter.write("EmployeeID,EmployeeName,Salary for 01-2023,Salary for 02-2023\n");
            for(Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
                String employeeId = entry.getKey();
                Employee employee = entry.getValue();

                if (employee.getStatus().equals("Active")) {
                    String employeeName = employee.getName();
                    double salary01 = salary01Map.getOrDefault(employeeId, 0.0);
                    double salary02 = salary02Map.getOrDefault(employeeId, 0.0);
                    salaryWriter.write(employeeId + "," + employeeName + "," + salary01 + "," + salary02 + "\n");
//                    System.out.println(employeeId + "," + employeeName + "," + salary01 + "," + salary02 + "\n");
                }
            }
            salaryWriter.close();

            System.out.println("Salary calculation completed successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}