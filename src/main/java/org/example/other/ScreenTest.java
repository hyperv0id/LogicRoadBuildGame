package org.example.other;

//屏幕抓取
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * 抓取本地桌面图片
 * @author 哑元
 *
 */
public class ScreenTest {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        JFrame jframe = new JFrame();
        jframe.setSize(1200, 700);
        JLabel label = new JLabel();
        jframe.add(label);
        //四个参数x y width height
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        //构建一个死循环动态截取
        while(true){
            BufferedImage image = robot.createScreenCapture(new Rectangle(0,0,1366,768));//截取屏幕
            label.setIcon(new ImageIcon(image));
            Thread.sleep(50);
        }
    }

}