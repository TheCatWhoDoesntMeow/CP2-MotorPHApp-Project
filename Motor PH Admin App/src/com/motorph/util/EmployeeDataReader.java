package com.motorph.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TheCatWhoDoesntMeow —Yamoyam, Jahaziel D.
 */

public class EmployeeDataReader {
    private static final int HEADER_LINE = 8; 
    public static List<String[]> readEmployeeData(String filePath) throws IOException {
        
        List<String[]> employees = new ArrayList<>();
        File file = new File(filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine = 0;

            while ((line = br.readLine()) != null) {
                currentLine++;

                //FOR SKIPPING THE HEADER LINE
                if (currentLine == HEADER_LINE + 1) {
                    continue; 
                }
                
                String[] data = parseCsvLine(line); 
                if (data.length >= 10) { 
                    String[] employeeData = {
                        data[0].trim(),  // Employee Number
                        data[1].trim(),  // Last Name
                        data[2].trim(),  // First Name
                        data[3].trim(),  // Birthday
                        data[4].trim(),  // Address
                        data[5].trim(),  // Phone Number
                        data[6].trim(),  // SSS #
                        data[7].trim(),  // PhilHealth #
                        data[8].trim(),  // TIN #
                        data[9].trim(),  // Pag-IBIG #
                        data[10].trim(), // Status
                        data[11].trim(), // Position
                        data[12].trim(), // Immediate Supervisor
                        data[13].trim(), // Basic Salary
                        data[14].trim(), // Rice Subsidy
                        data[15].trim(), // Phone Allowance
                        data[16].trim(), // Clothing Allowance
                        data[17].trim(), // Gross Semi-Monthly Rate
                        data[18].trim()   // Hourly Rate
                    };
                    employees.add(employeeData);
                }
            }
        }
        return employees;
    }
    
    private static String[] parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; 
            } else if (c == ',' && !inQuotes) {
                values.add(sb.toString().trim()); 
                sb.setLength(0);
            } else {
                sb.append(c); 
            }
        }
        values.add(sb.toString().trim()); 
        return values.toArray(new String[0]);
    }
}