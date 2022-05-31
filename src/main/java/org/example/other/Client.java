package org.example.other;


//--------------------------------------------------------------------------------------
//client端，用来接收creen图片和发送鼠标事件
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * 客户端
 * @author 哑元
 *
 */
public class Client {

    public static void main(String args[]) throws UnknownHostException, IOException{
        Socket s = new Socket("127.0.0.1",80);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ClientWindow cw = new ClientWindow(oos);
        byte[] imageBytes;
        while(true){
            imageBytes = new byte[dis.readInt()];   //先拿到传过来的数组长度
            dis.readFully(imageBytes);  //所有的数据存放到byte中
            cw.repainImage(imageBytes);
        }
    }
}

/**
 * 客户端窗体
 * @author 哑元
 *
 */
class ClientWindow extends JFrame{
    private ObjectOutputStream oos;
    private JLabel label;
    //重写背景图片方法
    public void repainImage(byte [] imageBytes){
        label.setIcon(new ImageIcon(imageBytes));
        this.repaint();
    }
    public ClientWindow(ObjectOutputStream oos){
        this.oos = oos;
        this.setTitle("远程控制程序");
        label = new JLabel();
        JPanel p = new JPanel();
        p.add(label);
        JScrollPane scroll = new JScrollPane(p);//给p面板添加滚动条
        this.add(scroll);
        this.setSize(1024,768);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                sendEvent(e);

            }

            @Override
            public void keyPressed(KeyEvent e) {
                sendEvent(e);

            }
        });

        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                sendEvent(e);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                sendEvent(e);

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                sendEvent(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }
    public void sendEvent(InputEvent event){
        try {
            oos.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}