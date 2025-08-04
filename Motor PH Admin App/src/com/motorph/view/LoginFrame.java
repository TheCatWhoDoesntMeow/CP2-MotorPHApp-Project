/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.motorph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author TheCatWhoDoesntMeow —Yamoyam, Jahaziel D.
 * Comments are made for studying and reviewing 
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private Image backgroundImage;


   public LoginFrame() {
        setTitle("MotorPH HR - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(new File("src/LoginBg.png")); // For test only
        } catch (IOException e) {
            e.printStackTrace();
        }

        //PANEL & BACKGROUND
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false); // Let background show through

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        inputPanel.add(usernameField, gbc);


        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        inputPanel.add(passwordField, gbc);


        // Show Password
        gbc.gridx = 1;
        gbc.gridy = 2;
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.addActionListener(e -> togglePasswordVisibility());
        inputPanel.add(showPasswordCheckbox, gbc);


        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");
        inputPanel.add(loginButton, gbc);
        loginButton.addActionListener(new LoginAction());

        backgroundPanel.add(inputPanel);
        add(backgroundPanel);
    }

    private void togglePasswordVisibility() {
        if (showPasswordCheckbox.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('•');
        }
    }
    
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Input validations
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Username is required!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Password is required!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Authentication check
            if (authenticate(username, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close login frame
                
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Incorrect Password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean authenticate(String username, String password) {
            try (BufferedReader br = new BufferedReader(new FileReader("data/loginCredentials.sql"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] creds = line.split(",");
                    if (creds.length >= 2 && creds[0].equals(username) && creds[1].equals(password)) {
                        return true;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Error reading user database!",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}