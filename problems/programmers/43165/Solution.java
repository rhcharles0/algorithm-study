class Solution {
    private int[] nums;
    private int target, answer;

    public int solution(int[] numbers, int target) {
        this.nums = numbers;
        this.target = target;
        this.answer = 0;
        int sum = 0;
        for (int n : numbers) sum += n;
        dfs(sum, 0, 0);
        return answer;
    }

    private void dfs(int cur, int idx, int visited) {
        if (visited == (1 << nums.length) - 1) {
            if (cur == target) answer++;
            return;
        }
        dfs(cur, idx + 1, visited | (1 << idx));
        dfs(cur - (nums[idx] * 2), idx + 1, visited | (1 << idx));
    }
}
