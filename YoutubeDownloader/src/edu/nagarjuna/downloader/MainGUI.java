package edu.nagarjuna.downloader;

//import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
//import java.awt.event.ActionListener;

public class MainGUI {

    private  JFrame jFrame;
    private JLabel textBoxLabel;

    private JTextField downloadUrlField;

    private JPanel jPanelOne;
    private JProgressBar downloadProgress;

    MainGUI(){
        loadGUIComponents();
    }
    public void loadGUIComponents(){
        jFrame = new JFrame("Youtube downloader");
        jFrame.setSize(500,600);

        textBoxLabel = new JLabel("PLease paste your youtube url below:");
        downloadUrlField = new JTextField();
        JButton downloadBtm = new JButton("Download");
        downloadBtm.addActionListener((ActionEvent e) ->{
            String userText = downloadUrlField.getText();
            startDownload(userText);
            JOptionPane.showMessageDialog(jFrame,"User input : "+userText);
        });
        downloadProgress = new JProgressBar();
        downloadProgress.setMaximum(100);
        downloadProgress.setMinimum(0);
        downloadProgress.setValue(50);
        jPanelOne = new JPanel();
        jPanelOne.add(textBoxLabel);
        jPanelOne.add(downloadUrlField);

        jPanelOne.setLayout(new GridLayout(1,1));

        jFrame.add(jPanelOne);
        jFrame.add(downloadBtm);
        jFrame.add(downloadProgress);

        jFrame.setLayout(new GridLayout(4,1));

        jFrame.setVisible(true);

    }

    public void startDownload(String downlaodUrl) {
        // String a = "abcd";
        // String b = "abcd"; b = "cde"
        // url : https://somewebsite.com/downlaods/idm.exe .substring(0,4)
        StringBuffer userHome = new StringBuffer(System.getProperty("user.home"));
        StringBuffer fileSeparator = new StringBuffer(System.getProperty("file.separator"));
        StringBuffer downloadPath = userHome.append(fileSeparator).append("Downloads").append(fileSeparator);
        downloadPath.append(downlaodUrl.substring(downlaodUrl.lastIndexOf("/")+1));

        URL url;
        try {
            url = new URL(downlaodUrl);
            getFileTotalSize(url);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(downloadPath.toString());
            byte[] b = new byte[1024];
            int count;

            while((count= bufferedInputStream.read(b,0,1024))!=-1){
                fileOutputStream.write(b,0,count);
            }
        }catch(MalformedURLException malformedURLException){
            System.out.println("The url provided : "+downlaodUrl+ " is invalid");
        }catch (IOException ioException){
            System.out.println("The resource is not found");
        }
    }

    private float getFileTotalSize(URL url,String expectedOutput) {
        //expectedoutput = mb, kb, gb
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            long length = httpURLConnection.getContentLengthLong();
            return getSize(length,expectedOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private float getSize(long length, String expectedOutput) {
        switch (expectedOutput){
            case "mb":
//                converttomb();
                break;
            case "gb":
                break;
            default:

        }
    }

    public static void main(String[] args) throws MalformedURLException {
       String downloadUrl =  "https://az764295.vo.msecnd.net/stable/e2816fe719a4026ffa1ee0189dc89bdfdbafb164/VSCodeUserSetup-x64-1.75.0.exe";
//        PrintAllProperties();
//                MainGUI mainGUI = new MainGUI();
        getFileTotalSize(new URL(downloadUrl));
//        startDownload(downloadUrl);
    }

    private static void PrintAllProperties() {
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("file.separator"));
        System.out.println(System.getenv("OS"));
        System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
        Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()){
            String property = enumeration.nextElement().toString();
            System.out.println(property +" "+System.getProperty(property));
        }
    }
}
