class Solution {
    public int[] solution(int brown, int yellow) {
        int total = brown + yellow;                                // 카펫 전체 칸 수 = 가로 * 세로
        for (int height = 3; height * height <= total; height++) { // 세로 최소 3 = 테두리 2줄 + 노란 1줄
            if (total % height != 0) continue;
            int width = total / height;                            // height <= width 가 자동 보장된다
            if ((width - 2) * (height - 2) == yellow) return new int[]{width, height};
        }
        throw new IllegalStateException("카펫 없음: brown=" + brown + ", yellow=" + yellow);
    }
}
