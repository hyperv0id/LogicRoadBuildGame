package org.example.other;

//server主程
import java.awt.AWTException;
import java.awt.Event;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * @author 哑元
 *
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        System.out.println("服务器已经正常启动");
        Socket socket = server.accept();//等待接收请求,阻塞方法
        System.out.println("有客户端连接");
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        //将客户端与服务器端链接的输出流交个ImageThread处理
        ImageThread imageThread = new ImageThread(dos);
        new Thread(imageThread).start();
        new Thread(new EventThread(new ObjectInputStream(socket.getInputStream()))).start();
    }
}
/**
 * 用来处理接收过来的鼠标事件或者键盘事件
 */
class EventThread implements Runnable{
    private ObjectInputStream ois;
    private Robot robot;
    public EventThread(ObjectInputStream ois) {
        this.ois = ois;
    }
    @Override
    public void run() {
        try {
            robot = new Robot();
            while(true){
                InputEvent event = (InputEvent)ois.readObject();//得知由客户端传递过来的是一个object对象
                actionEvent(event);//处理事件
            }
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 事件处理，用来判断事件类型，并用robot类执行
     * @param event
     */
    public void actionEvent(InputEvent event){
        System.out.println(event);
        if(event instanceof KeyEvent){
            KeyEvent e = (KeyEvent)event;
            int type = e.getID();//拿到事件类型
            if(type==Event.KEY_PRESS){
                robot.keyPress(e.getKeyCode());
            }else if(type == Event.KEY_RELEASE){
                robot.keyRelease(e.getKeyCode());
            }
        }else if(event instanceof MouseEvent){
            MouseEvent e = (MouseEvent)event;
            int type = e.getID();
            if(type == MouseEvent.MOUSE_MOVED){
                robot.mouseMove(e.getX(),e.getY());
            }else if(type == Event.MOUSE_DOWN){
                robot.mousePress(getMouseKey(type));
            }else if(type == Event.MOUSE_UP){
                robot.mouseRelease(getMouseKey(type));
            }else if(type == Event.MOUSE_DRAG){
                robot.mouseMove(e.getX(), e.getY());//鼠标拖动
            }
        }
    }
    /**
     * 返回鼠标的真正事件，鼠标时间不能直接处理，需要进过转换
     * @return
     */
    public int getMouseKey(int button){
        if(button == MouseEvent.BUTTON1){//鼠标左键
            return InputEvent.BUTTON1_DOWN_MASK;
        }else if(button == MouseEvent.BUTTON2){//鼠标右键
            return InputEvent.BUTTON2_DOWN_MASK;
        }else if(button == MouseEvent.BUTTON3){//滚轮
            return InputEvent.BUTTON3_DOWN_MASK;
        }else{
            return 0;
        }
    }
}