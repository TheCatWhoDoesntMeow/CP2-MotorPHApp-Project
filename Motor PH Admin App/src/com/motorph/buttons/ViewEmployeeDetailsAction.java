/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.motorph.buttons;

import com.motorph.util.EmployeeDataReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author TheCatWhoDoesntMeow —Yamoyam, Jahaziel D.
 */
public class ViewEmployeeDetailsAction extends AbstractAction{
    private JTable employeeTable;
    private Component parent;
    
    public ViewEmployeeDetailsAction(JTable employeeTable, Component parent) {
        super("View Employee Details");
        this.employeeTable = employeeTable;
        this.parent = parent;
    }
    
    public void actionPerformed(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent,"Please Select an Employee First!", "No Selected Employee!", JOptionPane.WARNING_MESSAGE);
            
            return;
        }
        String empId = (String) employeeTable.getValueAt(selectedRow, 0);
        
        try {
            List<String[]> allEmployees = EmployeeDataReader.readEmployeeData("data/employeeRecords.sql");
            
            for (String[] employee : allEmployees) {
                if (employee[0].equals(empId)) {
                    showEmployeeDetails(employee);
                    return;
                }
            }
            JOptionPane.showMessageDialog(parent, 
                "Employee details not found", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
                
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, 
                "Error loading employee data: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void showEmployeeDetails(String[] employee) {
        JFrame detailsFrame = new JFrame("Employee Details");
        detailsFrame.setSize(1000, 700);
        detailsFrame.setLocationRelativeTo(parent);

        //MAIN PANEL
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
            
            URL location = getClass().getResource("/ViewEmployeeBg.png");
            if (location == null) {
                System.err.println("Image not found!");
                g.setColor(Color.LIGHT_GRAY); // Fallback color
                g.fillRect(0, 0, getWidth(), getHeight());
                return; // Exit if image is not found
            }
            ImageIcon icon = new ImageIcon("src/ViewEmployeeBg.png");
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        };
        backgroundPanel.setOpaque(false); 
        
        // Content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false); 
        
        // Header with employee name and ID
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false); //HEADER TRANSPARENCY
        JLabel nameLabel = new JLabel(employee[2] + " " + employee[1] + " (ID: " + employee[0] + ")");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK); 
        headerPanel.add(nameLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Details panel with all information
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setOpaque(false); //DETAILS PANEL TRANSPARENCY
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 15);
        
        // Personal Information Section
        gbc.gridy++;
        addSectionHeader(detailsPanel, gbc, "Personal Information");
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Employee Number:", employee[0]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Last Name:", employee[1]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "First Name:", employee[2]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Birthday:", employee[3]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Address:", employee[4]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Phone Number:", employee[5]);
        
        // Government IDs Section
        gbc.gridy++;
        addSectionHeader(detailsPanel, gbc, "Government IDs");
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "SSS #:", employee[6]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Philhealth #:", employee[7]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "TIN #:", employee[8]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Pag-ibig #:", employee[9]);
        
         // Employment Details Section
        gbc.gridy++;
        addSectionHeader(detailsPanel, gbc, "Employment Details");
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Status:", employee[10]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Position:", employee[11]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Immediate Supervisor:", employee[12]);
        
        // Compensation Section
        gbc.gridy++;
        addSectionHeader(detailsPanel, gbc, "Compensation");
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Basic Salary:", "₱" + employee[13]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Rice Subsidy:", "₱" + employee[14]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Phone Allowance:", "₱" + employee[15]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Clothing Allowance:", "₱" + employee[16]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Gross Semi-Monthly Rate:", "₱" + employee[17]);
        
        gbc.gridy++;
        addDetailLabel(detailsPanel, gbc, "Hourly Rate:", "₱" + employee[18]);
        
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        detailsFrame.add(backgroundPanel);
        detailsFrame.setVisible(true);
    }
    
    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, String text) {
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, gbc);
        gbc.gridwidth = 1;
    }
    
    private void addDetailLabel(JPanel panel, GridBagConstraints gbc, String labelText, String valueText) {
        gbc.gridx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(value, gbc);
    }
}