// 제출본. 이 파일만 그대로 복사해서 낸다. main 도 assert 도 여기 넣지 않는다.
// 검증은 Main.java 에서 한다.
class Solution {
    public int solution(int[][] maps) {
        int answer = 0;
        int N = maps.length;
        int M = maps[0].length;
        int[] dy = {-1, 0, 1 ,0};
        int[] dx = {0, 1, 0 , -1};
        Deque<int[]> q = new ArrayDeque<>();
        int[][] visited = new int[N][M];
        if(maps[0][0] == 1){
            q.add(new int[]{0,0});
            visited[0][0] = 1;        
        }
    
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int cy = cur[0];
            int cx = cur[1];
            for(int i = 0 ; i < 4; i++){
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if(ny < 0 || ny >= N || nx < 0 || nx >= M || maps[ny][nx] == 0 || visited[ny][nx] > 0) continue;
                q.add(new int[]{ny,nx});
                visited[ny][nx] = visited[cy][cx] + 1;
            }
        }
        answer = visited[N-1][M-1];
        return  answer == 0 ? -1 : answer;
    }
}

// SWEA / 백준류(표준입력)라면 위를 지우고 아래 형태로 바꾼다.
// 이때 Main.java 는 쓰지 않고 `java Solution.java < input.txt` 로 돌린다.
//
// public class Solution {
//     public static void main(String[] args) throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         int T = Integer.parseInt(br.readLine());
//         for (int tc = 1; tc <= T; tc++) {
//             System.out.println("#" + tc + " " + ...);
//         }
//     }
// }
