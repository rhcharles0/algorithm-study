// 로컬 검증용. 제출하지 않는다.  실행: java -ea Main.java
public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        assert s.solution(new int[]{1, 1, 1, 1, 1}, 3) == 5 : "sample 1";
        assert s.solution(new int[]{4, 1, 2, 1}, 4) == 2 : "sample 2";
        System.out.println("모든 예제 통과");
    }
}
