package edu.nagarjuna.downloader;

//import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Properties;
//import java.awt.event.ActionListener;

public class MainGUI {

    private  JFrame jFrame;
    private JLabel textBoxLabel;

    private JTextField downloadUrlField;

    private JPanel jPanelOne;
    private JProgressBar downloadProgress;

    private JTable jTable;
    private JScrollPane jScrollPane;
    private final String[] COLUMNS = {"name","download date","download url"};

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
            saveDataToDatabase();
            startDownload(userText);
            JOptionPane.showMessageDialog(jFrame,"User input : "+userText);
        });
        downloadProgress = new JProgressBar();
        downloadProgress.setMaximum(100);
        downloadProgress.setMinimum(0);

        //inititating table
        DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{},COLUMNS);
        jTable = new JTable(defaultTableModel);
        jScrollPane = new JScrollPane(jTable);

        jPanelOne = new JPanel();
        jPanelOne.add(textBoxLabel);
        jPanelOne.add(downloadUrlField);

        jPanelOne.setLayout(new GridLayout(1,1));

        jFrame.add(jPanelOne);
        jFrame.add(downloadBtm);
        jFrame.add(downloadProgress);
        //add table to the frame
        jFrame.add(jScrollPane);

        jFrame.setLayout(new GridLayout(4,1));

        jFrame.setVisible(true);

    }

    private void saveDataToDatabase() {
        String[] rowsToBeInserted = {"1","2","3"};
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
        defaultTableModel.addRow(rowsToBeInserted);
    }

    public void startDownload(String downlaodUrl) {
        // String a = "abcd";
        // String b = "abcd"; b = "cde"
        // url : https://somewebsite.com/downlaods/idm.exe .substring(0,4)

        StringBuffer userHome = new StringBuffer(System.getProperty("user.home"));
        StringBuffer fileSeparator = new StringBuffer(System.getProperty("file.separator"));
        StringBuffer downloadPath = userHome.append(fileSeparator).append("Downloads").append(fileSeparator);
        downloadPath.append(downlaodUrl.substring(downlaodUrl.lastIndexOf("/")+1));
        // someurl.com, downloads, 7:18, donwloading
        // ohterurl.com, downloads, 8:20, failed, success
//        DatabaseUtil.saveToDatabase(downlaodUrl,downloadPath, LocalDateTime.now());
        URL url;
        try {
            url = new URL(downlaodUrl);
            float fileTotalSize = getFileTotalSize(url);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(downloadPath.toString());
            byte[] b = new byte[1024];
            int count;
            int totalCount=0;
            while((count= bufferedInputStream.read(b,0,1024))!=-1){
                totalCount+= count;
                updateProgressBar(totalCount,fileTotalSize);
                fileOutputStream.write(b,0,count);
            }
        }catch(MalformedURLException malformedURLException){
            System.out.println("The url provided : "+downlaodUrl+ " is invalid");
        }catch (IOException ioException){
            System.out.println("The resource is not found");
        }
    }

    private void updateProgressBar(int totalCount, float fileTotalSize) {
        int percentageDownloaded = (int) ((totalCount/fileTotalSize) * 100);
        // total 32kb > 32 mb
        System.out.println("percentage downloaded "+(totalCount/fileTotalSize)*100);
        SwingUtilities.invokeLater(()-> {
                downloadProgress.setValue(percentageDownloaded);
        });
    }

    private float getFileTotalSize(URL url) {
        //expectedoutput = mb, kb, gb
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            long length = httpURLConnection.getContentLengthLong();
            return length;
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
        return 0f;
    }

    public static void main(String[] args) throws MalformedURLException {
       String downloadUrl =  "https://az764295.vo.msecnd.net/stable/e2816fe719a4026ffa1ee0189dc89bdfdbafb164/VSCodeUserSetup-x64-1.75.0.exe";
//        PrintAllProperties();
                MainGUI mainGUI = new MainGUI();
//        getFileTotalSize(new URL(downloadUrl));
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
