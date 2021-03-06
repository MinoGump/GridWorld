import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;


public class Calculator {
    public static void main(String[] args) {
        Calc c = new Calc();
        c.init();
    }
    
}

class Calc implements ActionListener {
    public static final int NONE = 0;
    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int MULTIPLY = 3;
    public static final int DIVIDE = 4; 
    public static final int FRAME_HEIGHT = 150;
    public static final int FRAME_WIDTH = 240;
    public static final int FRAME_LOCATION_HEIGHT = 320;
    public static final int FRAME_LOCATION_WIDTH = 240;
    public static final int BUTTON_SIZE = 40;

    private int op = NONE;
    private Button b1, b2, b3, b4, b5;
    private Label l1, l2, l3;
    private TextField t1, t2;
    
    public void init() {
        JFrame c = new JFrame("A Calculator");
        c.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        c.setResizable(false);
        c.setLocation(FRAME_LOCATION_WIDTH, FRAME_LOCATION_HEIGHT);
        b1 = new Button("+");
        b2 = new Button("-");
        b3 = new Button("*");
        b4 = new Button("/");
        b5 = new Button("OK");
        l1 = new Label("");
        l2 = new Label("=");
        l3 = new Label("");
        t1 = new TextField(4);
        t2 = new TextField(4);
        t1.setSize(BUTTON_SIZE, BUTTON_SIZE);
        t2.setSize(BUTTON_SIZE, BUTTON_SIZE);
        t1.setEditable(true);
        t2.setEditable(true);
        FlowLayout layout = new FlowLayout();
        c.setLayout(layout);
        c.add(t1);
        c.add(l1);
        c.add(t2);
        c.add(l2);
        c.add(l3);
        c.add(b1);
        c.add(b2);
        c.add(b3);
        c.add(b4);
        c.add(b5);
        l1.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        l2.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        l3.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b1.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b2.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b3.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b4.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b5.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        c.setVisible(true);
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                op = PLUS;
                l1.setText("+");
            }
            if (e.getSource() == b2) {
                op = MINUS;
                l1.setText("-");                
            }
            if (e.getSource() == b3) {
                op = MULTIPLY;
                l1.setText("*");
            }
            if (e.getSource() == b4) {
                op = DIVIDE;
                l1.setText("/");
            }
            if (e.getSource() == b5) {
                int num1 = Integer.parseInt(t1.getText());
                int num2 = Integer.parseInt(t2.getText());
                if (op == PLUS) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1+num2));
                    op = NONE;
                } else if (op == MINUS) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1-num2));
                    op = NONE;
                } else if (op == MULTIPLY) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1*num2));
                    op = NONE;
                } else if (op == DIVIDE) {
                    l3.setText("");
                    if (num2 == 0) {
                        l3.setText("Error");
                    } else {
                        l3.setText(Integer.toString(num1/num2));
                    }
                    op = NONE;
                } else if (op == NONE) {
                    l1.setText("");
                    l3.setText("");
                }
            }
        } catch (Exception e1) {
            l3.setText("");
        }
    }
}
