class Solution {
    int[] parent;
    public int find(int i){
        if(i != parent[i]) return find(parent[i]);
        return i;
    }
    public void union(int i, int j){
        int c = find(i);
        int n = find(j);
        if(c != n){
            parent[n] = c;
        }
    }
    public int solution(int n, int[][] computers) {
        int answer = 0;
        parent = new int[n+1];
        for(int i = 1 ; i <=n ; i++){
            parent[i] = i;
        }
        for(int[] com: computers){
            for(int i = 0 ; i < com.length - 1; i++){
                if(com[i] == 0) continue;
                for(int j = i+1 ; j < com.length; j++){
                    if(com[j] == 0) continue;
                    union(i+1,j+1);
                }
            }
        }
        // 부모만 세기
        for(int i = 1; i <=n ;i++){
            if(find(i) == i) answer++;
        }
        
        return answer;
    }
}