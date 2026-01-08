import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Main extends JFrame {
    public Main() {
        setTitle("DATA STRUCTURE");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 18));
        setLayout(new BorderLayout());

        JLabel header = new JLabel("DATA STRUCTURE", SwingConstants.CENTER);
        header.setFont(new Font("Monospaced", Font.BOLD, 32));
        header.setForeground(new Color(0, 255, 150));
        header.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 2, 25, 25));
        grid.setBackground(new Color(18, 18, 18));
        grid.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));

        grid.add(createDashboardBtn("1. Array Operations", new Color(0, 150, 255), e -> new ArrayVisualizer().setVisible(true)));
        grid.add(createDashboardBtn("2. Linked List Operations", new Color(150, 0, 255), e -> new LinkedListVisualizer().setVisible(true)));
        grid.add(createDashboardBtn("3. Stack / Queue Operations", new Color(255, 150, 0), e -> new StackQueueVisualizer().setVisible(true)));
        grid.add(createDashboardBtn("4. Exit", new Color(255, 50, 50), e -> System.exit(0)));

        add(grid, BorderLayout.CENTER);
    }

    private JButton createDashboardBtn(String text, Color glow, ActionListener action) {
        JButton b = new JButton(text);
        b.setFont(new Font("Monospaced", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(30, 30, 30));
        b.setBorder(BorderFactory.createLineBorder(glow, 2));
        b.setFocusPainted(false);
        b.addActionListener(action);
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}


class ArrayVisualizer extends JFrame {
    private ArrayList<String> data = new ArrayList<>();
    private JPanel visualArea;

    public ArrayVisualizer() {
        setTitle("Array Pro Visualizer");
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Setup Initial Data
        try {
            int size = Integer.parseInt(JOptionPane.showInputDialog("Enter Array Size:"));
            String input = JOptionPane.showInputDialog("Enter " + size + " elements (comma separated):");
            if (input != null) {
                String[] items = input.split(",");
                for (int i = 0; i < Math.min(size, items.length); i++) data.add(items[i].trim());
            }
        } catch (Exception e) { dispose(); return; }

        JPanel sidebar = createSidebar("--- ARRAY MENU ---", new Color(0, 150, 255));
        sidebar.add(sideBtn("1. Traverse", e -> JOptionPane.showMessageDialog(this, "Array: " + data)));
        sidebar.add(sideBtn("2. Insert", e -> {
            String val = JOptionPane.showInputDialog("Enter Value:");
            int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter Position (1-" + (data.size()+1) + "):"));
            if (pos > 0 && pos <= data.size() + 1) data.add(pos - 1, val);
            visualArea.repaint();
        }));
        sidebar.add(sideBtn("3. Delete", e -> {
            int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter Position (1-" + data.size() + "):"));
            if (pos > 0 && pos <= data.size()) data.remove(pos - 1);
            visualArea.repaint();
        }));
        sidebar.add(sideBtn("4. Search", e -> {
            String key = JOptionPane.showInputDialog("Search Element:");
            int idx = data.indexOf(key);
            JOptionPane.showMessageDialog(this, idx != -1 ? "Found at Position " + (idx+1) : "Not Found!");
        }));
        sidebar.add(sideBtn("5. Back", e -> dispose()));

        visualArea = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(new Color(20, 20, 20));
                int x = 50;
                for (int i = 0; i < data.size(); i++) {
                    g.setColor(new Color(0, 150, 255));
                    g.fillRoundRect(x, 200, 70, 60, 10, 10);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, 200, 70, 60);
                    g.drawString(data.get(i), x + 25, 235);
                    g.setColor(Color.GRAY);
                    g.drawString("[" + i + "]", x + 25, 280);
                    x += 85;
                }
            }
        };

        add(sidebar, BorderLayout.WEST);
        add(visualArea, BorderLayout.CENTER);
    }

    private JPanel createSidebar(String title, Color c) {
        JPanel p = new JPanel(new GridLayout(12, 1, 5, 5));
        p.setPreferredSize(new Dimension(220, 0));
        p.setBackground(new Color(35, 35, 35));
        JLabel l = new JLabel(title, SwingConstants.CENTER);
        l.setForeground(c);
        p.add(l);
        return p;
    }

    private JButton sideBtn(String t, ActionListener a) {
        JButton b = new JButton(t);
        b.setBackground(new Color(50, 50, 50));
        b.setForeground(Color.WHITE);
        b.addActionListener(a);
        return b;
    }
}


class LinkedListVisualizer extends JFrame {
    private java.util.List<String> list = new ArrayList<>();
    private String type = "SINGLY";
    private JPanel visualArea;

    public LinkedListVisualizer() {
        setTitle("Linked List Pro Visualizer");
        setSize(1100, 600);
        setLayout(new BorderLayout());

        // Setup Initial Data
        String[] types = {"Singly", "Doubly", "Circular"};
        type = (String) JOptionPane.showInputDialog(null, "Select Type:", "List Type",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);

        try {
            int n = Integer.parseInt(JOptionPane.showInputDialog("Number of Nodes:"));
            String input = JOptionPane.showInputDialog("Enter " + n + " elements (comma separated):");
            if (input != null) {
                for (String s : input.split(",")) list.add(s.trim());
            }
        } catch (Exception e) { dispose(); return; }

        JPanel sidebar = new JPanel(new GridLayout(12, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(35, 35, 35));
        JLabel l = new JLabel("--- LL MENU (" + type + ") ---", SwingConstants.CENTER);
        l.setForeground(new Color(150, 0, 255));
        sidebar.add(l);

        sidebar.add(sideBtn("1. Traverse", e -> JOptionPane.showMessageDialog(this, "List: " + list)));
        sidebar.add(sideBtn("2. Insert End", e -> { list.add(JOptionPane.showInputDialog("Val:")); visualArea.repaint(); }));
        sidebar.add(sideBtn("3. Delete ", e -> { if(!list.isEmpty()){list.remove(0); visualArea.repaint();} }));
        sidebar.add(sideBtn("4. Back", e -> dispose()));

        visualArea = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                this.setBackground(new Color(20, 20, 20));
                int x = 50, y = 200;

                for (int i = 0; i < list.size(); i++) {
                    g2.setColor(new Color(150, 0, 255));
                    g2.drawRoundRect(x, y, 70, 45, 10, 10);
                    g2.setColor(Color.WHITE);
                    g2.drawString(list.get(i), x + 20, y + 27);

                    // Forward Arrow
                    if (i < list.size() - 1) {
                        g2.drawLine(x + 70, y + 22, x + 110, y + 22);
                        g2.fillPolygon(new int[]{x+115, x+105, x+105}, new int[]{y+22, y+17, y+27}, 3);
                    }

                    // Doubly (Backward Arrow)
                    if (type.equals("Doubly") && i > 0) {
                        g2.setColor(Color.GRAY);
                        g2.drawLine(x, y + 35, x - 40, y + 35);
                        g2.fillPolygon(new int[]{x-45, x-35, x-35}, new int[]{y+35, y+30, y+40}, 3);
                    }

                    // Circular (Return Arrow)
                    if (type.equals("Circular") && i == list.size() - 1 && list.size() > 1) {
                        g2.setColor(Color.RED);
                        g2.drawArc(50 + 35, y + 45, x - 50, 60, 180, 180);
                    }
                    x += 120;
                }
            }
        };

        add(sidebar, BorderLayout.WEST);
        add(visualArea, BorderLayout.CENTER);
    }

    private JButton sideBtn(String t, ActionListener a) {
        JButton b = new JButton(t);
        b.setBackground(new Color(50, 50, 50));
        b.setForeground(Color.WHITE);
        b.addActionListener(a);
        return b;
    }
}

class StackQueueVisualizer extends JFrame {
    private java.util.Stack<String> stack = new java.util.Stack<>();
    private java.util.List<String> queue = new ArrayList<>();
    private int stackLimit, queueLimit;
    private JPanel visualArea;
    private String statusMessage = "Waiting for operation...";

    public StackQueueVisualizer() {
        setTitle("Stack/Queue Pro Visualizer");
        setSize(1100, 750);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 15));

        try {
            stackLimit = Integer.parseInt(JOptionPane.showInputDialog("Enter Stack Maximum Size:"));
            queueLimit = Integer.parseInt(JOptionPane.showInputDialog(JOptionPane.showInputDialog("Enter Queue Maximum Size:")));
        } catch (Exception e) { stackLimit = 5; queueLimit = 5; }

        JPanel sidebar = new JPanel(new GridLayout(15, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBackground(new Color(35, 35, 35));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidebar.add(createHeaderLabel("--- STACK MENU ---", Color.ORANGE));
        sidebar.add(sideBtn("1. Push Character", e -> pushStack()));
        sidebar.add(sideBtn("2. Pop", e -> popStack()));
        sidebar.add(sideBtn("3. Peek", e -> peekStack()));
        sidebar.add(sideBtn("5. Infix -> Postfix", e -> convertInfix(true)));
        sidebar.add(sideBtn("6. Infix -> Prefix", e -> convertInfix(false)));

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(createHeaderLabel("--- QUEUE MENU ---", new Color(0, 200, 255)));
        sidebar.add(sideBtn("1. Enqueue", e -> enqueue()));
        sidebar.add(sideBtn("2. Dequeue", e -> dequeue()));
        sidebar.add(sideBtn("3. Back to Main", e -> dispose()));

        visualArea = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                this.setBackground(new Color(20, 20, 20));

                g2.setColor(Color.DARK_GRAY);
                g2.setStroke(new BasicStroke(4));
                g2.drawRect(150, 150, 120, 400);

                int yPos = 510;
                for (int i = 0; i < stack.size(); i++) {
                    g2.setColor(new Color(255, 165, 0));
                    g2.fillRoundRect(155, yPos, 110, 35, 10, 10);
                    g2.setColor(Color.BLACK);
                    g2.drawString(stack.get(i), 200, yPos + 23);
                    if (i == stack.size() - 1) {
                        g2.setColor(Color.GREEN);
                        g2.drawString("<- TOP (Index " + i + ")", 280, yPos + 23);
                    }
                    yPos -= 40;
                }

                g2.setColor(Color.WHITE);
                g2.drawString("STATUS: " + statusMessage, 150, 100);

                int qX = 500;
                for (int i = 0; i < queueLimit; i++) {
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRect(qX, 150, 70, 70);
                    if (i < queue.size()) {
                        g2.setColor(new Color(0, 200, 255));
                        g2.fillRect(qX+5, 155, 60, 60);
                        g2.setColor(Color.WHITE);
                        g2.drawString(queue.get(i), qX + 25, 195);
                    }
                    qX += 75;
                }
            }
        };

        add(sidebar, BorderLayout.WEST);
        add(visualArea, BorderLayout.CENTER);
    }

    private void pushStack() {
        if (stack.size() < stackLimit) {
            String val = JOptionPane.showInputDialog("Enter Character:");
            if (val != null) { stack.push(val); statusMessage = "Pushed: " + val; visualArea.repaint(); }
        }
    }

    private void popStack() {
        if (!stack.isEmpty()) { statusMessage = "Popped: " + stack.pop(); visualArea.repaint(); }
    }

    private void peekStack() {
        if (!stack.isEmpty()) JOptionPane.showMessageDialog(this, "Top: " + stack.peek());
    }

    private void enqueue() {
        if (queue.size() < queueLimit) {
            String val = JOptionPane.showInputDialog("Enter Item:");
            if (val != null) { queue.add(val); visualArea.repaint(); }
        }
    }

    private void dequeue() {
        if (!queue.isEmpty()) { queue.remove(0); visualArea.repaint(); }
    }

    private void convertInfix(boolean post) {
        String infix = JOptionPane.showInputDialog("Enter Infix:");
        if (infix != null) {
            String res = post ? infixToPostfix(infix) : infixToPrefix(infix);
            JOptionPane.showMessageDialog(this, "Result: " + res);
        }
    }

    private int prec(char ch) { return switch (ch) { case '+', '-' -> 1; case '*', '/' -> 2; case '^' -> 3; default -> -1; }; }

    private String infixToPostfix(String exp) {
        StringBuilder res = new StringBuilder(); Stack<Character> s = new Stack<>();
        for (char c : exp.toCharArray()) {
            if (Character.isLetterOrDigit(c)) res.append(c);
            else if (c == '(') s.push(c);
            else if (c == ')') { while (!s.isEmpty() && s.peek() != '(') res.append(s.pop()); s.pop(); }
            else { while (!s.isEmpty() && prec(c) <= prec(s.peek())) res.append(s.pop()); s.push(c); }
        }
        while (!s.isEmpty()) res.append(s.pop()); return res.toString();
    }

    private String infixToPrefix(String exp) {
        StringBuilder rev = new StringBuilder(exp).reverse();
        for (int i = 0; i < rev.length(); i++) {
            if (rev.charAt(i) == '(') rev.setCharAt(i, ')');
            else if (rev.charAt(i) == ')') rev.setCharAt(i, '(');
        }
        return new StringBuilder(infixToPostfix(rev.toString())).reverse().toString();
    }

    private JLabel createHeaderLabel(String t, Color c) {
        JLabel l = new JLabel(t, SwingConstants.CENTER);
        l.setForeground(c);
        l.setFont(new Font("Monospaced", Font.BOLD, 16));
        return l;
    }

    private JButton sideBtn(String t, ActionListener a) {
        JButton b = new JButton(t);
        b.setBackground(new Color(50, 50, 50));
        b.setForeground(Color.WHITE);
        b.addActionListener(a);
        return b;
    }
}