package org.example.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;            // 文件名称

    private long fileLength;             // 文件长度
    private byte[] fileContent;          // 文件内容

    public Message(){

    }
    public Message(String filePath) throws IOException{
        File file = new File(filePath);
        this.fileLength=file.length();
        this.fileName=file.getName();

        FileInputStream FIS = new FileInputStream(filePath);
        byte[] bytes = new byte[(int)fileLength];
        FIS.read(bytes,0,(int)fileLength);
        this.fileContent=bytes;

    }



    public String getFileName()
    { return fileName;}

    public void setFileName(String fileName)

    { this.fileName = fileName;}
    public long getFileLength()
    { return fileLength;
    }

    public void setFileLength(long fileLength)
    {this.fileLength = fileLength;}



    public byte[] getFileContent()
    {return fileContent;}
    public void setFileContent(byte[] fileContent)
    {this.fileContent = fileContent;}


}
