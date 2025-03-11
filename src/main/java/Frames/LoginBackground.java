package Frames;

import javax.swing.*;
import java.awt.*;

public class LoginBackground extends JPanel 
{
    private Image backgroundImage;

    public LoginBackground(Image backgroundImage) 
    {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        if (backgroundImage != null) 
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
