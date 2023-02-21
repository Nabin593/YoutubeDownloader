package downloader;

//tried by me

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TryMainGUI {

    private JFrame jFrame;

    private JLabel textBoxLabel;

    private JTextField DownloadUrlField;

    private JPanel jPanelOne;

    TryMainGUI() { loadGUIComponents(); }

    public void loadGUIComponents(){
        jFrame = new JFrame("Youtube video Downloader");
        jFrame.setSize(200,600);

        textBoxLabel = new JLabel("Please paste the youtube video link/url here:");
        DownloadUrlField = new JTextField();
        JButton DownloadBtn = new JButton("Download Button");
        DownloadBtn.addActionListener((ActionEvent e) ->{
            String UserText = DownloadUrlField.getText();
            JOptionPane.showMessageDialog(jFrame,"User Input : "+UserText );
        });

        jPanelOne = new JPanel();
        jPanelOne.add(textBoxLabel);
        jPanelOne.add(DownloadUrlField);
        jPanelOne.setLayout(new GridLayout(1,1));

        jFrame.add(jPanelOne);
        jFrame.add(DownloadBtn);

        jFrame.setLayout(new GridLayout(3,1));

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        TryMainGUI tryMainGUI = new TryMainGUI();
    }
}
