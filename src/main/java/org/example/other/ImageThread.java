package org.example.other;



//屏幕截取器和发送器，这里需要拿到socket的out流

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.imageio.*;
/**
 * 用来将图片数据发送
 * @author 哑元
 *
 */
public class ImageThread implements Runnable{
    DataOutputStream dos = null;    //数据输出流
    public ImageThread(DataOutputStream dos){
        this.dos = dos;
    }
    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            //截取整个屏幕
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            /*
            int width = (int)dimension.getWidth();
            int height = (int)dimension.getWidth();
            Rectangle rec = new Rectangle(0,0,width,height);
            */
            Rectangle rec = new Rectangle(dimension);
            BufferedImage image;
            byte imageBytes[];
            while(true){
                image = robot.createScreenCapture(rec);
                imageBytes = getImageBytes(image);
                dos.writeInt(imageBytes.length);
                dos.write(imageBytes);
                dos.flush();
                Thread.sleep(50);   //线程睡眠
            }

        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally{
            try {
                if(dos!= null)  dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     *  压缩图片
     * @param 需要压缩的图片
     * @return 压缩后的byte数组
     * @throws IOException
     * @throws ImageFormatException
     */
    public byte[] getImageBytes(BufferedImage image) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return baos.toByteArray();
    }

}
