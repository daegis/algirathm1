import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 9/11/2018 7:54 PM
 */
public class Window extends JFrame {
    public static void main(String args[]) {
        GraphicsDemo myGraphicsFrame = new GraphicsDemo();
    }
}

class ShapesPanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.black);
        int C = 12;
        int[][] spots = new int[C][2];
        // 随机生成十个点
        for (int i = 0; i < C; i++) {
            g.setColor(Color.green);
            Random xr = new Random();
            final int x = xr.nextInt(1800) + 100;
            final int y = xr.nextInt(800) + 100;
            g.fillRect(x, y, 10, 10);
            Font f = new Font(null, Font.PLAIN, 40);
            g.setFont(f);
            g.setColor(Color.red);
            g.drawString(String.valueOf(i), x, y);
            spots[i][0] = x;
            spots[i][1] = y;
        }
        // 打印点的信息
        for (int[] spot : spots) {
            System.out.println(Arrays.toString(spot));
        }
        // 计算点距离矩阵
        int[][] distances = new int[C][C];
        for (int i = 0; i < C; i++) {
            for (int j = 0; j < C; j++) {
                int ax = spots[i][0];
                int ay = spots[i][1];
                int bx = spots[j][0];
                int by = spots[j][1];
                final int distanceXdistance = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
                final Double pow = Math.pow(distanceXdistance, 0.5);
                final int distance = pow.intValue();
                distances[i][j] = distance;
            }
        }
        for (int[] distance : distances) {
            System.out.println(Arrays.toString(distance));
        }

        // 进行路线规划
        final BackTSP tsp = new BackTSP(distances.length, distances);
        tsp.solve();
        final int minLen = tsp.getMinLen();
        System.out.println(minLen);
        final int[] bestX = tsp.getBestX();
        System.out.println(Arrays.toString(bestX));

        // 开始画线
        g.setColor(Color.cyan);
        int current = bestX[0];
        for (int i = 1; i < distances.length; i++) {
            int next = bestX[i];
            // 画一条线
            final int ax = spots[current][0];
            final int ay = spots[current][1];
            final int bx = spots[next][0];
            final int by = spots[next][1];
            g.drawLine(ax, ay, bx, by);
            current = next;
        }

        final int ax = spots[0][0];
        final int ay = spots[0][1];
        final int bx = spots[current][0];
        final int by = spots[current][1];
        g.drawLine(ax, ay, bx, by);
    }
}


class GraphicsDemo extends JFrame {
    public GraphicsDemo() {
        this.getContentPane().add(new ShapesPanel());
        setSize(1920, 1000);
        setVisible(true);
    }
}

