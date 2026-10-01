// 로컬 검증용. 제출하지 않는다.
// 실행: java -ea Main.java        ← -ea 없으면 assert 가 통째로 무시된다
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();

        // 예제 케이스. 배열 반환이면 == 가 아니라 Arrays.equals 를 쓴다.
        assert s.solution(3,new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}) == 2 : "sample 1";
        assert s.solution(3, new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}}) == 1 : "sample 2";
        System.out.println("모든 예제 통과");
    }
}
