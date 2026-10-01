// 로컬 검증용. 제출하지 않는다.  실행: java -ea Main.java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        assert Arrays.equals(s.solution(10, 2), new int[]{4, 3}) : "sample 1";
        assert Arrays.equals(s.solution(8, 1), new int[]{3, 3}) : "sample 2";
        assert Arrays.equals(s.solution(24, 24), new int[]{8, 6}) : "sample 3";
        // 경계: 정사각형(height == width), 가장 납작한 카펫(height == 3)
        assert Arrays.equals(s.solution(5000, 1560001), new int[]{1251, 1251}) : "정사각형";
        assert Arrays.equals(s.solution(5000, 2497), new int[]{2499, 3}) : "height 최소";
        System.out.println("모든 예제 통과");
    }
}
