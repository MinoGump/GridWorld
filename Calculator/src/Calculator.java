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
    int operation = 0;
    Button b1, b2, b3, b4, b5;
    Label l1, l2, l3;
    TextField t1, t2;
    
    public void init() {
        JFrame c = new JFrame("A Calculator");
        c.setSize(240, 150);
        c.setResizable(false);
        c.setLocation(240, 320);
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
        t1.setSize(40,40);
        t2.setSize(40,40);
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
        l1.setPreferredSize(new Dimension(40, 40));
        l2.setPreferredSize(new Dimension(40, 40));
        l3.setPreferredSize(new Dimension(40, 40));
        b1.setPreferredSize(new Dimension(40, 40));
        b2.setPreferredSize(new Dimension(40, 40));
        b3.setPreferredSize(new Dimension(40, 40));
        b4.setPreferredSize(new Dimension(40, 40));
        b5.setPreferredSize(new Dimension(40, 40));
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
                operation = 1;
                l1.setText("+");
            }
            if (e.getSource() == b2) {
                operation = 2;
                l1.setText("-");                
            }
            if (e.getSource() == b3) {
                operation = 3;
                l1.setText("*");
            }
            if (e.getSource() == b4) {
                operation = 4;
                l1.setText("/");
            }
            if (e.getSource() == b5) {
                int num1 = Integer.parseInt(t1.getText());
                int num2 = Integer.parseInt(t2.getText());
                if (operation == 1) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1+num2));
                    operation = 0;
                } else if (operation == 2) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1-num2));
                    operation = 0;
                } else if (operation == 3) {
                    l3.setText("");
                    l3.setText(Integer.toString(num1*num2));
                    operation = 0;
                } else if (operation == 4) {
                    l3.setText("");
                    if (num2 == 0) {
                        l3.setText("Error");
                    } else {
                        l3.setText(Integer.toString(num1/num2));
                    }
                    operation = 0;
                } else if (operation == 0) {
                    l1.setText("");
                    l3.setText("");
                }
            }
        } catch (Exception e1) {
            l3.setText("");
        }
    }
}
