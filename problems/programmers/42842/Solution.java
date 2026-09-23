import java.io.*;
import java.util.*;

public class Solution {

    static public int[] makeYellowTile(int yellow, int brown){
        if(yellow == 0) return null;
        for(int x = 1; x <= yellow; x++){
            int y = yellow/x;
            if(y > x ||x*y != yellow) continue;
            if(((y+2)*(x+2) == (brown + yellow)) ){
                return new int[]{x+2, y+2};
            }
        }
        return null;
    }
    // 프로그래머스류: 이 메서드만 제출
    static int[] solve(int brown, int yellow) {
        return makeYellowTile(yellow, brown);
    }

    public static void main(String[] args) throws IOException {
        // 백준/SWEA류 표준입력 파싱은 여기서
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 예제 케이스 검증 — 실행 시 -ea 필수 (java -ea Solution.java)
        assert solve(10, 2) == new int[]{4,3} : "sample 1";
        assert solve(8,1) == new int[]{3,3} : "sample 2";
        assert solve(24, 24) == new int[]{8,6} : "sample 3";
    }
}
