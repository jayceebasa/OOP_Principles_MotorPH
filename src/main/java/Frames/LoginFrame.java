package Frames;

import com.opencsv.exceptions.CsvValidationException;
import org.mapua.Login;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class LoginFrame extends JFrame 
{
    private JPanel jPanel1;
    private JLabel jTitle;
    private JLabel jUsername;
    private JTextField tf_username;
    private JLabel jPassword;
    private JPasswordField tf_password;
    private JButton jLogin_btn;
    
    public LoginFrame() 
    {
        initComponents();
    }
    private void initComponents() 
    {
        jPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Banner
        ImageIcon icon = new ImageIcon("resources/MotorPH.png"); //ex: "C:/User/PCName/Documents/NetBeansProjects/MotorPH Portal v1.1/MotorPH.png"
        ImageIcon resizedIcon = resizeImageIcon(icon, 450, 200);
        JLabel jImage = new JLabel(icon);
        jImage = new JLabel(resizedIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(jImage, gbc);
        
        
        //Title
        jTitle = new JLabel("MotorPH Portal", JLabel.CENTER);
        jTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(jTitle, gbc);
   

        //Username Label
        jUsername = new JLabel(" Username", JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(jUsername, gbc);

        
        //Username TextField
        tf_username = new JTextField(0);
        tf_username.setBorder(new EtchedBorder(Color.white, null));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        jPanel1.add(tf_username, gbc);

        
        //Password Label
        jPassword = new JLabel(" Password", JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPassword, gbc);

        //Password TextField
        tf_password = new JPasswordField(15);
        tf_password.setBorder(new EtchedBorder(Color.white, null));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1;
        jPanel1.add(tf_password, gbc);
        

        //Login Button
        jLogin_btn = new JButton("Login");
        jLogin_btn.setPreferredSize(new Dimension(80, 30));
        jLogin_btn.setBorder(new BevelBorder(BevelBorder.RAISED));
        jLogin_btn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                loginbtnMouseClicked();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        jPanel1.add(jLogin_btn, gbc);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(jPanel1);
        getRootPane().setDefaultButton(jLogin_btn);
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        //Spacer
        JPanel emptyPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        jPanel1.add(emptyPanel, gbc);
    }
    //Button Functions
    private void loginbtnMouseClicked() 
    {
        Login login = new Login();
        login.setUsername(tf_username.getText());
        login.setPassword(tf_password.getPassword());

        try{
            if((!login.getUsername().isEmpty()) && (!login.getPassword().isEmpty()))
            {
                if(login.isAuthenticated()) 
                {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose();
                    new MainFrame().setVisible(true);
                }else
                {
                    JOptionPane.showMessageDialog(this, "Username or Password is incorrect. Try again.");
                }
            }else 
            {
                JOptionPane.showMessageDialog(this, "Username and password is a required field!");
            }
        }catch (IOException | CsvValidationException ex)
        {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Exit(java.awt.event.MouseEvent evt) 
    {                      
        exit(0);
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) 
    {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}