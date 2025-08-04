/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.motorph.view;

import com.motorph.util.EmployeeDataReader;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;


/**
 *
 * @author TheCatWhoDoesntMeow —Yamoyam, Jahaziel D.
 */

public class MainFrame extends JFrame {
    private JTextField searchField;
    private JButton viewBtn, addBtn, updateBtn, deleteBtn, computeBtn, refreshBtn, logoutBtn;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        //FRAME SETUP
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Increased size to accommodate table
        setLocationRelativeTo(null);

        //DESIGNNNNNN! I'M LIVING FOR THE AESTHETICS SIR! HAHAHAHA
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                try {
                    BufferedImage bgImage = ImageIO.read(new File("src/MainFrameBg.png"));
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    
                    // Fallback gradient background
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    Color color1 = new Color(52, 152, 219);
                    Color color2 = new Color(41, 128, 185);
                    GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        //SEARCH BAR
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setOpaque(false);
        
        //HEADER NAME ABOVE SEARCH BAR - UWU`
        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setOpaque(false);
        
        //STILL A SEARCH BAR COMPONENT: LABEL 
        JLabel searchLabel = new JLabel("Search by Employee ID:");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        //SEARCH BUTTON
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(46, 204, 113));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //EMPLOYEE TABLE
        String[] columnNames = {"Employee #", "Last Name", "First Name", "SSS #", "PhilHealth #", "TIN", "Pag-IBIG #"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setRowHeight(25);
        
        // TABLE TRANSPARENCY
        employeeTable.setOpaque(false);
        employeeTable.setForeground(Color.WHITE);
        employeeTable.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent dark background
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 12));
        employeeTable.getTableHeader().setBackground(new Color(50, 50, 50));
        employeeTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //BUTTON PANEL
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        //STYLE SANG BUTTONS
        viewBtn = createStyledButton("View Employee Details", new Color(52, 152, 219));
        addBtn = createStyledButton("Add New Employee", new Color(155, 89, 182));
        updateBtn = createStyledButton("Update Employee Record", new Color(241, 196, 15));
        deleteBtn = createStyledButton("Delete Employee Record", new Color(231, 76, 60));
        computeBtn = createStyledButton("Compute Salary", new Color(46, 204, 113));
        refreshBtn = createStyledButton("Refresh", new Color(52, 73, 94));
        logoutBtn = createStyledButton("LogOut", new Color(149, 165, 166));
        
        //ADDING OF BUTTONS SA MAY PANEL
        buttonPanel.add(viewBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(computeBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(logoutBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //ACTIONS/METHODS
        searchBtn.addActionListener(this::searchEmployee);
        logoutBtn.addActionListener(this::logout);
        refreshBtn.addActionListener(e -> refreshEmployeeData());
        
        //LOAD FROM SQL FILE IN THE DATA FOLDER
        refreshEmployeeData();
    }
    

        

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private void searchEmployee(ActionEvent e) {
        String empId = searchField.getText().trim();
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //SEARCH IMPLEMENTATION FOR EMPLOYEE ID SEARCHING
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (empId.equals(tableModel.getValueAt(i, 0))) {
                employeeTable.setRowSelectionInterval(i, i);
                employeeTable.scrollRectToVisible(employeeTable.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Employee not found", "Not Found", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to logout?", 
            "Logout", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    private void refreshEmployeeData() {
         tableModel.setRowCount(0); // Clear existing data
        
        try {
            List<String[]> employeeData = EmployeeDataReader.readEmployeeData("data/employeeRecords.sql");
            for (String[] data : employeeData) {
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading employee data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
