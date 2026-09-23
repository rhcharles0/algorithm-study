import java.io.*;
import java.util.*;

public class Solution {

    static Set<Integer> primeSet = new HashSet<>();
    static int end = 0;
    static public boolean isPrime(int num){
        if(num == 2) return true;
        if(num <= 1 || num %2 ==0) return false;
        for(int i = 2 ; i *i <= num ; i++){
            if(num % i == 0) return false;
        }
        return true;
    }
    static public void recur(String str, int idx, int res, int visited){
        if(!(idx == -1 || res == 0) && isPrime(res)){
            primeSet.add(res);    
        }
        for(int next = 0; next < end ; next++){
            if((visited & (1 << next) ) > 0) continue;
            recur(str, next, res, visited | 1 << next);
            recur(str, next, res*10 + str.charAt(next) -'0', visited | 1 << next);
        }
    }
    // 프로그래머스류: 이 메서드만 제출

    static int solve(String numbers) {
        primeSet.clear();
        int answer = 0;
        end = numbers.length();
        recur(numbers,-1,0, 0 );
        answer = primeSet.size();
        return answer;
    }

    public static void main(String[] args) throws IOException {
        // 백준/SWEA류 표준입력 파싱은 여기서
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 예제 케이스 검증
        assert solve("17") == 3 : "sample 1";
        // System.out.println(solve("17"));

        assert solve("011") == 2 : "sample 2";
        // System.out.println(solve("011"));
    }
}
