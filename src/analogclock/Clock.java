package analogclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Clock extends JPanel implements Runnable {
    Thread thread = null;
    SimpleDateFormat formatter = new SimpleDateFormat("s", Locale.getDefault());
    Date currentDate;
    int xcenter = 960, ycenter = 500, lastxs = 0, lastys = 0, lastxm = 0;
    int lastym = 0, lastxh = 0, lastyh = 0;

    private void drawStructure(Graphics g) {
        g.setFont(new Font("Calibri", Font.ITALIC, 60));
        g.setColor(Color.BLACK);
        g.fillOval(xcenter-300 , ycenter-300 , 600, 600);
        g.setColor(Color.WHITE);        
        g.drawString("6", xcenter-10, ycenter +260);
        g.drawString("3", xcenter +250, ycenter+10);
        g.drawString("9", xcenter -275, ycenter+10);
        g.drawString("12", xcenter-30, ycenter- 240);
        g.setFont(new Font("Calibri", Font.ITALIC, 30));
        g.drawString("Project Submitted by:", 1360, 790);
        g.drawString("Rabin Ghimire   (073/BCT/28)", 1400 , 820);
        g.drawString("Yatru Harsha Hiski   (073/BCT/48)", 1400 , 850);
    }

    public void paint(Graphics g) {
        int xhour, yhour, xminute, yminute, xsecond, ysecond, second, minute, hour;
        drawStructure(g);
        currentDate = new Date();
        formatter.applyPattern("s");
        second = Integer.parseInt(formatter.format(currentDate));
        formatter.applyPattern("m");
        minute = Integer.parseInt(formatter.format(currentDate));
        formatter.applyPattern("h");
        hour = Integer.parseInt(formatter.format(currentDate));
        xsecond = (int) (Math.cos(second * 3.14f / 30 - 3.14f / 2) * 240 + xcenter);
        ysecond = (int) (Math.sin(second * 3.14f / 30 - 3.14f / 2) * 240 + ycenter);
        xminute = (int) (Math.cos(minute * 3.14f / 30 - 3.14f / 2) * 200 + xcenter);
        yminute = (int) (Math.sin(minute * 3.14f / 30 - 3.14f / 2) * 200 + ycenter);
        xhour = (int) (Math.cos((hour * 30 + minute / 2) * 3.14f / 180 - 3.14f / 2) * 160 + xcenter);
        yhour = (int) (Math.sin((hour * 30 + minute / 2) * 3.14f / 180 - 3.14f / 2) * 160 + ycenter);
// Erase if necessary, and redraw  
        g.setColor(Color.yellow);
        if (xsecond != lastxs || ysecond != lastys) {
            g.drawLine(xcenter, ycenter, lastxs, lastys);
        }
        if (xminute != lastxm || yminute != lastym) {
            g.drawLine(xcenter, ycenter - 5, lastxm, lastym);
            g.drawLine(xcenter - 5, ycenter, lastxm, lastym);
        }
        if (xhour != lastxh || yhour != lastyh) {
            g.drawLine(xcenter, ycenter - 5, lastxh, lastyh);
            g.drawLine(xcenter - 5, ycenter, lastxh, lastyh);
        }
        g.setColor(Color.YELLOW);
        g.drawLine(xcenter, ycenter, xsecond, ysecond);
        g.setColor(Color.red);
        g.drawLine(xcenter, ycenter - 5, xminute, yminute);
        g.drawLine(xcenter - 5, ycenter, xminute, yminute);
        g.setColor(Color.green);
        g.drawLine(xcenter, ycenter - 5, xhour, yhour);
        g.drawLine(xcenter - 5, ycenter, xhour, yhour);
        lastxs = xsecond;
        lastys = ysecond;
        lastxm = xminute;
        lastym = yminute;
        lastxh = xhour;
        lastyh = yhour;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {

        thread = null;
    }

    public void run() {
        while (thread != null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            repaint();
        }
        thread = null;
    }

    public void update(Graphics g) {
        paint(g);
    }

    public static void main(String args[]) {
        JFrame window = new JFrame();
        Color c = new Color(118, 73, 190);
        window.setBackground(c);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 400, 400);
        Clock clock = new Clock();
        window.getContentPane().add(clock);
        window.setVisible(true);
        clock.start();
    }

}
