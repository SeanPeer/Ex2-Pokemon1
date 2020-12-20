package GUI;

import gameClient.Ex2;
import okhttp3.internal.ws.RealWebSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class Menu extends JFrame implements ActionListener {
    String[] arg = {"123"};
    private static int lvl_select;
    private static long id_select;
    private static boolean status;

    //    String[] lvl = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    JButton start;
    JButton our_id;
    JButton exit;
    JTextField id_field;
    JTextField level;

//    JFrame frame;

    JPanel panel;
    ImageIcon picachu;

//    JButton enter_buttom;

    JLabel welcomeLabel;
    JLabel lvlLabel;
    JLabel idLabel;
    JLabel enter_id;


    public Menu(String s) {
        super(s);
        initialize();

        setStatus(false);

        /**
         * Setting the GUI window properties
         */
        this.setIconImage(picachu.getImage());
        this.setSize(350, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        panel.setLayout(null);

        /**
         * Introduction label
         */
        welcomeLabel.setBounds(10, 5, 300, 25);
        panel.add(welcomeLabel);

        /**
         * enter ID
         */
        enter_id.setBounds(10,50,80,25);
        panel.add(enter_id);
        id_field.addActionListener(e -> System.out.println(id_select));
        id_field.setBounds(100,50,80,25);


        panel.add(id_field);


        /**
         * Select level
         */
        lvlLabel.setBounds(10, 100, 80, 25);
        panel.add(lvlLabel);
        level.addActionListener(this);
        level.setBounds(100, 100, 80, 25);
        panel.add(level);

        /**
         * After you selecting the level
         * press start to launch the game
         */
        start.setBounds(190, 100, 60, 25);
        start.addActionListener(e -> {
//            Ex2.main(arg);

            setID(Long.parseLong(id_field.getText()));
            setLevel(Integer.parseInt(level.getText()));

            setStatus(true);

            setVisible(false);


        });
        panel.add(start);


        /**
         * Name presentation
         */
        idLabel.setBounds(10, 150, 120, 25);
        panel.add(idLabel);
        our_id.addActionListener(e -> showMessageDialog(null, "Sean And Amos"));
        our_id.setBounds(120, 150, 100, 25);
        panel.add(our_id);

        /**
         * Exit Button
         */
        exit.addActionListener(e -> System.exit(0));
        exit.setBounds(100, 250, 100, 25);
        panel.add(exit);

        add(panel, BorderLayout.CENTER);
        setVisible(true);


    }

    public int getLevel() { return lvl_select;}
    public void setLevel(int l) {lvl_select = l;}

    public long getID() { return id_select;}
    public void setID(long l) {id_select = l;}

    public boolean getStatus() {return status;}
    public void setStatus(boolean b) {status = b;}

    @Override
    public void actionPerformed(ActionEvent e1) {
        this.lvl_select = Integer.parseInt(level.getText());
        System.out.println(lvl_select);
    }

    public void initialize() {
        panel = new JPanel();
        enter_id = new JLabel("Enter user ID");
        id_field = new JTextField();

//        enter_buttom = new JButton("Enter");
//        frame = new JFrame("Pokemon Game");

        picachu = new ImageIcon("Pokemon-pic.jpg");
        welcomeLabel = new JLabel("Welcome to our Pokemon Algorithm Game !");
        lvlLabel = new JLabel("Select level ");
        level = new JTextField(3);
        start = new JButton("Play");
        idLabel = new JLabel("Developers Name");
        our_id = new JButton("Click Here");
        exit = new JButton("Exit");
    }


}
