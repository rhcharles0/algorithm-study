import java.util.*;

public class Solution {

    // 프로그래머스류: 이 메서드만 제출
    static int[] solve(int brown, int yellow) {
        int total = brown + yellow;                                // 카펫 전체 칸 수 = 가로 * 세로
        for (int height = 3; height * height <= total; height++) { // 세로 최소 3 = 테두리 2줄 + 노란 1줄
            if (total % height != 0) continue;
            int width = total / height;                            // height <= width 가 자동 보장된다
            if ((width - 2) * (height - 2) == yellow) return new int[]{width, height};
        }
        throw new IllegalStateException("카펫 없음: brown=" + brown + ", yellow=" + yellow);
    }

    public static void main(String[] args) {
        // 예제 케이스 검증 — 실행 시 -ea 필수 (java -ea Solution.java)
        assert Arrays.equals(solve(10, 2), new int[]{4, 3}) : "sample 1";
        assert Arrays.equals(solve(8, 1), new int[]{3, 3}) : "sample 2";
        assert Arrays.equals(solve(24, 24), new int[]{8, 6}) : "sample 3";
        // 경계: 정사각형(height == width), 가장 납작한 카펫(height == 3)
        assert Arrays.equals(solve(5000, 1560001), new int[]{1251, 1251}) : "정사각형";
        assert Arrays.equals(solve(5000, 2497), new int[]{2499, 3}) : "height 최소";
    }
}
