# DFS / BFS

| 항목 | 내용 |
|---|---|
| 분류 | 알고리즘 |
| 관련 문제 | [PG 43165 타겟 넘버](../../problems/programmers/43165) · [PG 42839 소수 찾기](../../problems/programmers/42839) |

## 한 줄 정의

**DFS** 는 한 갈래를 끝까지 파고들었다가 되돌아오는 탐색, **BFS** 는 가까운 것부터 층층이 퍼지는 탐색이다.
"모든 경우"를 만들 땐 DFS, **"최단 거리"** 를 구할 땐 BFS.

## 언제 쓰나

| 묻는 것 | 쓸 것 |
|---|---|
| 경우의 수 / 가능한가 / 연결 요소 | DFS (재귀가 짧다) |
| **최단 거리 / 최소 횟수** | **BFS** — DFS 로 풀면 틀린다 |
| 가중치가 있는 최단 거리 | 다익스트라 (BFS 아님) |

가지치기가 들어가면 그건 백트래킹이다 → [backtracking.md](backtracking.md)
2ᴺ 가 감당이 안 되면 → [dp.md](dp.md) · 후보를 식으로 줄일 수 있으면 → [brute-force.md](brute-force.md)

---

## 비트마스크가 필요한가 — 판별 기준 하나

**이 노트의 핵심이다.** 백트래킹을 배우면 `visited` 비트마스크를 반사적으로 붙이게 되는데,
**절반은 필요 없다.** 기준은 하나다.

> ### 후보를 `idx` 로 **차례대로** 훑는가, **매번 전체**에서 고르는가

| | 매번 전체에서 고름 | `idx` 를 0부터 순차 훑음 |
|---|---|---|
| 뼈대 | `for (i = 0; i < n; i++)` | `dfs(idx + 1)` |
| 같은 원소 재방문 | **가능** → 막아야 한다 | 구조상 **불가능** |
| `visited` | **필요** | **불필요** |
| 예 | 순열, 부분집합의 순열 | 각 원소 넣/뺀다 2갈래, 격자 BFS |
| 문제 | [PG 42839](../../problems/programmers/42839) | [PG 43165](../../problems/programmers/43165) |

### PG 42839 — 필요한 경우

```java
for (int i = 0; i < s.length(); i++) {
    if ((visited & 1 << i) != 0) continue;   // ← 없으면 같은 조각을 두 번 쓴다
    build(s, cur * 10 + s.charAt(i) - '0', visited | 1 << i);
}
```

조각을 **임의 순서로** 고른다. `"17"` 에서 `17` 과 `71` 이 둘 다 답이므로 `i` 를 매번 0부터 돈다.
그러면 같은 조각을 재방문할 길이 열려 있어 `visited` 가 **실제로 가지를 쳐낸다.**

### PG 43165 — 필요 없는 경우

```java
static void dfs(int cur, int idx, int visited){
    if (visited == (1 << nums.length) - 1) { ... }
    dfs(cur,                    idx + 1, visited | (1 << idx));
    dfs(cur - nums[idx] * 2,    idx + 1, visited | (1 << idx));
}
```

두 갈래 **모두** `idx` 비트를 켜고 `idx + 1` 로 내려간다. 둘이 맞물려 움직이므로

```
어떤 경로로 오든  visited == (1 << idx) - 1
```

이 **항상** 성립한다 (전 경로에 assert 를 걸어 확인했다). 따라서 종료 조건

```java
visited == (1 << nums.length) - 1     ⟺     idx == nums.length
```

는 **같은 말을 어렵게 쓴 것**이다. 실측 호출 수도 2,097,151회로 완전히 동일하다.

```java
// 같은 일을 하는 코드
static void dfs(int sum, int idx) {
    if (idx == nums.length) { if (sum == target) answer++; return; }
    dfs(sum, idx + 1);                  // + 유지
    dfs(sum - nums[idx] * 2, idx + 1);  // - 로 전환
}
```

> **판별법**: `dfs` 안에서 `idx` 가 `+1` 로만 움직이면 `visited` 를 지워라.
> `for` 로 매번 전체를 도는 경우에만 남겨라.

비트마스크가 공짜도 아니다 — `1 << n` 은 **n ≥ 31 에서 오버플로우**해 종료 조건이 조용히 깨진다.
`idx == n` 은 그런 상한이 없다.

---

## DFS 뼈대

### 1. 각 원소를 넣/뺀다 — 2갈래 (부분집합)

```java
static void dfs(int idx, int acc) {
    if (idx == n) { process(acc); return; }
    dfs(idx + 1, acc);              // 안 넣는다
    dfs(idx + 1, acc + arr[idx]);   // 넣는다
}
```

`visited` 없음. 호출 2ᴺ. **N ≤ 20 까지만** (2²⁰ ≈ 100만 = 5ms, 2³⁰ 이면 죽는다).

### 2. 격자 DFS — 연결 요소 / 영역 개수

```java
static final int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

static void fill(int x, int y) {
    visited[x][y] = true;
    for (int d = 0; d < 4; d++) {
        int nx = x + dx[d], ny = y + dy[d];
        if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
        if (visited[nx][ny] || grid[nx][ny] == 0) continue;
        fill(nx, ny);
    }
}
```

격자가 1000×1000 이면 재귀 깊이가 100만까지 갈 수 있다 → **`StackOverflowError`.**
그 크기에서는 DFS 를 스택으로 풀거나 BFS 로 바꾼다.

## BFS 뼈대 — 격자 최단거리

```java
static int bfs(int[][] grid) {
    int n = grid.length, m = grid[0].length;
    int[][] dist = new int[n][m];
    ArrayDeque<int[]> q = new ArrayDeque<>();
    q.add(new int[]{0, 0});
    dist[0][0] = 1;                        // 방문 표시를 겸한다

    while (!q.isEmpty()) {
        int[] cur = q.poll();
        int x = cur[0], y = cur[1];
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d], ny = y + dy[d];
            if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
            if (dist[nx][ny] != 0 || grid[nx][ny] == 0) continue;   // 방문했거나 벽
            dist[nx][ny] = dist[x][y] + 1;
            q.add(new int[]{nx, ny});
        }
    }
    return dist[n-1][m-1] == 0 ? -1 : dist[n-1][m-1];
}
```

**방문 표시는 큐에 넣는 순간 한다.** 꺼낼 때 하면 같은 칸이 큐에 여러 번 들어가 터진다.
`dist` 배열 하나가 방문 표시와 거리를 겸하므로 `visited` 를 따로 안 만든다.

### 동시에 퍼지는 BFS (여러 시작점)

시작점을 **전부 큐에 먼저 넣고** 시작하면 된다. 토마토 익히기, 불 번지기 유형.

```java
for (int i = 0; i < n; i++)
    for (int j = 0; j < m; j++)
        if (grid[i][j] == START) { q.add(new int[]{i, j}); dist[i][j] = 1; }
```

## 복잡도

| | 시간 | 공간 |
|---|---|---|
| 격자 DFS/BFS | O(V + E) = **O(N·M)** (4방향이라 E ≈ 4V) | O(N·M) |
| 부분집합 DFS | O(2ᴺ) | O(N) 재귀 스택 |
| 순열 DFS | O(N!) | O(N) |

**경우의 수 × 한 경우당 비용을 코드 쓰기 전에 곱해봐라.** 1억이 넘으면 접근을 바꾼다.

## 표준 라이브러리

| | Java | C++ |
|---|---|---|
| 큐 | `ArrayDeque<int[]>` (`LinkedList` 보다 빠르다) | `deque<array<int,2>>` 또는 `queue<>` |
| 꺼내기 | `poll()` | `front()` + `pop()` |
| 좌표 묶기 | `int[]{x, y}` | `array<int,2>` / `pair<int,int>` |
| 재귀 깊이 한계 | 약 1만 (기본 스택) | 비슷 |

## 주의할 점

1. **최단 거리에 DFS 를 쓰면 틀린다.** 먼저 도착한 경로가 최단이라는 보장이 없다.
2. **BFS 방문 표시는 큐에 넣을 때.** 꺼낼 때 하면 중복 삽입으로 메모리가 터진다.
3. **`idx` 순차 훑기에 `visited` 를 붙이지 마라** (위 판별 기준).
4. **방향 배열 순서** — 문제가 우선순위를 정했으면(위 → 왼쪽 → 아래 → 오른쪽) 그대로 쓴다.
   삼성 문제에서 단골로 틀리는 자리다.
5. **static 상태 초기화** — 프로그래머스·SWEA 는 한 프로세스에서 여러 케이스를 돌린다.
6. **격자 크기가 크면 DFS 재귀는 스택이 터진다.** 1000×1000 부터는 BFS 를 기본으로.

## 관련 문제

- [PG 43165 타겟 넘버](../../problems/programmers/43165) — 2갈래 DFS, `visited` 불필요
- [PG 42839 소수 찾기](../../problems/programmers/42839) — 매번 전체 순회, `visited` 필요
- [PG 43162 네트워크](../../problems/programmers/43162) — 연결 요소 세기 (유니온 파인드와 비교)
- [PG 1844 게임 맵 최단거리](../../problems/programmers/1844) — 격자 BFS 기본형
- SWEA `1953` 탈주범 검거 — 방향이 제한된 BFS
