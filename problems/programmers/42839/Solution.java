import java.util.*;

class Solution {
    private Set<Integer> primeSet = new HashSet<>();
    private int end = 0;

    public boolean isPrime(int num){
        if(num == 2) return true;
        if(num <= 1 || num %2 ==0) return false;
        for(int i = 2 ; i *i <= num ; i++){
            if(num % i == 0) return false;
        }
        return true;
    }

    public void recur(String str, int idx, int res, int visited){
        if(!(idx == -1 || res == 0) && isPrime(res)){
            primeSet.add(res);
        }
        for(int next = 0; next < end ; next++){
            if((visited & (1 << next) ) > 0) continue;
            recur(str, next, res, visited | 1 << next);
            recur(str, next, res*10 + str.charAt(next) -'0', visited | 1 << next);
        }
    }

    public int solution(String numbers) {
        primeSet.clear();
        int answer = 0;
        end = numbers.length();
        recur(numbers,-1,0, 0 );
        answer = primeSet.size();
        return answer;
    }
}
