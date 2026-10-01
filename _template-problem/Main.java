// 로컬 검증용. 제출하지 않는다.
// 실행: java -ea Main.java        ← -ea 없으면 assert 가 통째로 무시된다
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();

        // 예제 케이스. 배열 반환이면 == 가 아니라 Arrays.equals 를 쓴다.
        assert s.solution(new int[]{1, 2, 3}) == 0 : "sample 1";

        System.out.println("모든 예제 통과");
    }
}
