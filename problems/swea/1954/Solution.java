import java.util.*;
import java.io.*;
// 제출본. 이 파일만 그대로 복사해서 낸다. main 도 assert 도 여기 넣지 않는다.
// 검증은 Main.java 에서 한다.
// class Solution {
//     public int solution(int[] arr) {
//         return 0;
//     }
// }

// SWEA / 백준류(표준입력)라면 위를 지우고 아래 형태로 바꾼다.
// 이때 Main.java 는 쓰지 않고 `java Solution.java < input.txt` 로 돌린다.
//
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb =new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        int[] dy = {-1, 0 , 1 , 0};
        int[] dx = {0 , 1 , 0, -1};
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int total = N*N;
            int cur = 0;
            int[][] maps = new int[N][N];
            int dir = 1;
            Deque<int[]> q = new ArrayDeque<>();
            q.add(new int[]{0,0});
            maps[0][0] = 1;
            cur++;
            while(cur < total){
                cur++;
                int[] curCoordinate = q.poll();
                int cy = curCoordinate[0];
                int cx = curCoordinate[1];
                int ny = cy + dy[dir];
                int nx = cx + dx[dir];
                if(ny < 0 || ny >= N || nx < 0 || nx >= N || maps[ny][nx] != 0){
                    dir = (dir + 1)%4;
                    ny = cy + dy[dir];
                    nx = cx + dx[dir];
                }
                if(maps[ny][nx] == 0){
                    maps[ny][nx] = cur;
                    q.add(new int[]{ny,nx});
                }
            }
            sb.append("#"+tc).append('\n');
            for(int i = 0 ; i < N; i++){
                for(int j = 0 ; j < N ; j++){
                    sb.append(maps[i][j]).append(j != N-1 ? ' ' : '\n');
                }
            }
            
        }
        System.out.print(sb);
    }
}
