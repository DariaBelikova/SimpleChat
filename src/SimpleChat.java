import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.CharBuffer;


public class SimpleChat extends JFrame implements ActionListener {
    final String TITLE_OF_PROGRAM = "Chatter: simple chatbot";
    final int START_LOCATION = 200;
    final int WINDOW_WIDTH = 350;
    final int WINDOW_HEIGHT = 450;
    final String LOG_FILE_NAME = "log";

    JTextArea dialogue; // area for dialog
    JCheckBox ai;       // enable/disable AI
    JTextField message; // field for entering messages

    public static void main(String[] args) {

        new SimpleChat();
    }

    SimpleChat() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);

        // area for dialog
        dialogue = new JTextArea();
        dialogue.setLineWrap(true);
        JScrollPane scrollBar = new JScrollPane(dialogue);

        String log = getLog();
        dialogue.setText(log);

        // panel for checkbox, message field and button
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        ai = new JCheckBox("AI");
        ai.doClick();
        message = new JTextField();
        message.addActionListener(this);
        JButton enter = new JButton("Enter");
        enter.addActionListener(this);
        add(BorderLayout.CENTER, scrollBar);

        // adding all elements to the window
        bp.add(ai);
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (message.getText().trim().length() > 0) {
            dialogue.append(message.getText() + "\n");

            String log = dialogue.getText();
            saveLog(log);
        }
        message.setText("");
        message.requestFocusInWindow();
    }

    private void saveLog(String log) {
        BufferedWriter out = null;
        try
        {
            FileWriter fstream = new FileWriter(LOG_FILE_NAME, false);
            out = new BufferedWriter(fstream);
            out.write(log);
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            if(out != null) {
                try {
                    out.close();
                }
                catch (IOException e)
                {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private String getLog() {
        String log = "";
        BufferedReader in = null;
        try
        {
            FileReader fstream = new FileReader(LOG_FILE_NAME);
            in = new BufferedReader(fstream);

            String tmp = in.readLine();

            while(tmp != null) {
                log += tmp + "\n";
                tmp = in.readLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        finally
        {
            if(in != null) {
                try {
                    in.close();
                }
                catch (IOException e)
                {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }

        return log;
    }
}
