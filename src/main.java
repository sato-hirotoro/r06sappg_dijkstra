public class main {

    final int N = 5;            // ノードの個数.
    final int INF = 999;        // 十分大きい定数.
    final int[][] edge = {      // [m,n]には,ノードmからノードnへの距離を格納.隣接していない場合はINFを格納.自身は0.
        {0, 10, 16, INF, INF},
        {10, 0, 4, 3, INF},
        {16, 4, 0, 2, 3},
        {INF, 3, 2, 0, 6},
        {INF, INF, 3, 6, 0}
    };
    final int GOAL = 4;         // 終点ノード番号.
    int[] dist = {              // 始点ノード距離.（現時点の最短距離を保持）
        INF, INF, INF, INF, INF
    };
    int[] done = {              // 初期値は0.最短距離が確定したら1.（一度更新起点ノードに選ばれたら最短距離が確定する）
        0, 0, 0, 0, 0
    };
    int curNode = 0;            // 更新起点ノードのノード番号.
    int minDist = 0;            // 更新起点ノードを求める際に使用する一時変数.
    int k;                      // 要素番号

    // 最短経路の出力用
    int[] viaNode = {           // 最短のノード番号を格納
        0, 0, 0, 0, 0
    };
    int j = 0;                  // 要素番号

    public static void main(String[] args) {
        new main().distance();
    }

    private void distance() {
        dist[0] = 0;
        while (true) {
            /*
            手順①
            更新起点ノードを探す.
             */
            minDist = INF;
            // 最短経路が確定しておらず,始点ノード距離が最短のノードが更新起点ノード.
            for (k = 0; k < N; k++) {
                if (done[k] == 0 && dist[k] < minDist) {
                    minDist = dist[k];
                    curNode = k;
                }
            }
            // 選ばれた更新起点ノードの最短距離を確定させる.
            done[curNode] = 1;

            /*
            手順②
            更新起点ノードが終点ノードに到達した場合に出力処理を開始.
             */
            if (curNode == GOAL) {
                j = GOAL;
                // GOALから逆順に出力.
                System.out.print("最短経路：" + GOAL);
                while (j > 0) {
                    System.out.print(" ← " + viaNode[j]);
                    j = viaNode[j];
                }
                System.out.println("\n最短距離：" + dist[curNode]);
                break;
            }

            /*
            手順③
            更新起点ノードに隣接する各ノードへの距離を測定し,現時点の最短距離より短い距離を見つけたら更新する.
             */
            for (k = 0; k < N; k++) {
                // 更新起点ノードまでの始点ノード距離 + 更新起点ノードから見た隣接するノードまでの距離 < 今のところの最短距離.
                // 例：ノード2が更新起点ノードで「(1 -> 2):10 + (2 -> 3):4 <（1 -> 3）:17」なら,ノード3までの最短経路を14に更新する.
                if (dist[curNode] + edge[curNode][k] < dist[k] && done[k] == 0) {
                    // 最短距離を見つけた場合,そのノードへの最短経路経由地としてcurNodeを記録する.
                    // 例：「viaNode[3] = 2」ノード3へ行くにはノード2を経由するのが最短経路である.
                    viaNode[k] = curNode;
                    // 最短距離を更新する.（未確定のもののみ）
                    dist[k] = dist[curNode] + edge[curNode][k];
                }
            }
        }
    }
}