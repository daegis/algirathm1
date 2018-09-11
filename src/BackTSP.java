import java.util.Arrays;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 9/11/2018 7:29 PM
 */
public class BackTSP {

    BackTSP() {

    }

    BackTSP(int n, int[][] weight) {
        this.N = n;
        this.weight = weight;
        this.x = new int[n];
        this.bestX = new int[n];
    }

    private int N;

    private int cl; // 当前路径的长度
    private int fl; // 当前只当的最大路径长度

    private int weight[][];
    private int x[];
    private int bestX[];

    public int[] getBestX() {
        return bestX;
    }

    public int getMinLen() {
        return fl;
    }

    /**
     * 判断第k个数是否不同与前k-1个数
     *
     * @param k
     * @return bool
     */
    private boolean nextValue(int k) {
        int i = 0;
        while (i < k) {
            if (x[k] == x[i]) {
                return false;
            }
            i += 1;
        }
        return true;
    }

    private void backUp(int k) {
        if (k == N - 1) {
            for (int j = 1; j <= N; j++) {
                x[k] = Math.floorMod(x[k] + 1, N);
                if (nextValue(k) && cl + weight[x[k - 1]][x[k]] + weight[x[k]][0] < fl) { // 如果最短路径,更新最优解
                    fl = cl + weight[x[k - 1]][x[k]] + weight[x[k]][0];
                    for (int i = 0; i < N; i++) {
                        bestX[i] = x[i];
                    }
                }
            }
        } else {
            for (int j = 1; j <= N; j++) {
                x[k] = Math.floorMod(x[k] + 1, N);
                if (nextValue(k) && cl + weight[x[k - 1]][x[k]] <= fl) {
                    // 此路可行
                    cl += weight[x[k - 1]][x[k]];
                    backUp(k + 1);
                    cl -= weight[x[k - 1]][x[k]];
                }
            }

        }
    }

    public void solve() {
        int k = 1; // 第0个顶点是固定的,从第一个顶点开始选择
        cl = 0;
        fl = Integer.MAX_VALUE;
        backUp(k);
    }

    public static void main(String[] args) {
        final BackTSP tsp = new BackTSP();
        tsp.solve();
        final int minLen = tsp.getMinLen();
        System.out.println(minLen);
        final int[] bestX = tsp.getBestX();
        System.out.println(Arrays.toString(bestX));
    }

}
