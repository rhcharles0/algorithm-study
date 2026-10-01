# 네트워크

| 항목 | 내용 |
|---|---|
| 출처 | 프로그래머스 |
| 문제 번호 | `43162` |
| 링크 | https://school.programmers.co.kr/learn/courses/30/lessons/43162 |
| 난이도 | Lv.3 |
| 카테고리 | 유니온 파인드 · 그래프 - 연결 요소 |
| 관련 개념 | [union-find](../../../concepts/data-structure/union-find.md) *(아직 안 쓴 노트)* · [DFS / BFS](../../../concepts/algorithm/dfs-bfs.md) |
| 푼 날짜 | 2026-09-27 |
| 언어 | Java |

## 문제 요약

컴퓨터 `n` 대의 연결 관계가 **인접 행렬** `computers` 로 주어질 때 **네트워크(연결 요소)의 개수**를 구한다.
직접 연결뿐 아니라 **간접 연결도 같은 네트워크**다.

- `n`: **1 ~ 200**
- `computers[i][j] == 1` 이면 `i` 와 `j` 가 연결 · `computers[i][i]` 는 **항상 1**
- 행렬은 대칭이다 (`computers[i][j] == computers[j][i]`)
- 예: `[[1,1,0],[1,1,0],[0,0,1]]` → `{0,1}` 과 `{2}` 로 **2개**

## 풀이 접근

[유니온 파인드](../../../concepts/data-structure/union-find.md) *(아직 안 쓴 노트)* 로
연결된 컴퓨터를 같은 집합으로 묶고, **마지막에 남은 루트의 개수**를 셌다.

1. `parent` 를 `n+1` 크기로 잡고 `parent[i] = i` 로 초기화한다 (1-indexed).
2. `find` 는 `parent[i] != i` 인 동안 부모를 타고 올라가 루트를 재귀로 찾는다.
3. `union` 은 두 루트를 구해 다르면 `parent[두 번째 루트] = 첫 번째 루트` 로 붙인다.
4. **행 하나씩** 훑으면서, 그 행에서 값이 1인 인덱스들을 **쌍으로 만들어(`i < j`) 전부 `union`** 한다.
   한 행 안에서 1인 것들은 모두 같은 네트워크이므로 묶어도 된다는 발상이다.
5. 마지막에 `find(i) == i` 인 `i`(= 자기 자신이 루트)의 개수를 세서 반환한다.

---

<!-- 아래는 /problem-feedback 스킬이 항상 덮어쓰는 영역 -->

## 복잡도

`n = 컴퓨터 수` (문제 제약 1 ≤ n ≤ 200)

| 언어 | 시간 복잡도 | 공간 복잡도 |
|---|---|---|
| Java | **O(n³ · d)** — 행마다 쌍을 만들어 최악 **union 3,980,000회**, `find` 단계 1,588만, 실측 **16.4ms** | O(n) `parent` + O(d) 재귀 스택 |
| C++ | **미구현** (템플릿 그대로) | — |

`d` 는 트리 깊이다. 경로 압축이 없어 이론상 O(n) 까지 가능하지만,
무작위 3,000건에서 **실측 최대 17** 이었다 (n=53, 밀도 6%).

**통과 판정: 여유롭게 통과.** `n ≤ 200` 이 작아서 O(n³) 라도 16ms다.
다만 **필요한 일의 200배**를 하고 있다 (아래 1번).

> 무작위 500건을 BFS 해와 교차 검증해 **불일치 0건**. 예제 2개와 `n = 1` 도 통과.

## 피드백

### 현재 풀이의 문제점

**1. 행 안에서 쌍을 만드는 게 불필요하다 — union 호출 200배**

```java
for(int[] com: computers){
    for(int i = 0 ; i < com.length - 1; i++){
        if(com[i] == 0) continue;
        for(int j = i+1 ; j < com.length; j++){
            if(com[j] == 0) continue;
            union(i+1,j+1);          // ← 행 안의 모든 쌍
        }
    }
}
```

한 행의 1들이 전부 같은 네트워크인 건 맞다. 그런데 **쌍으로 다 묶을 필요가 없다.**
`k` 행의 1들은 전부 `k` 와 연결돼 있으므로 **`k` 하나에만 붙이면** 나머지는 따라온다.
`|S|` 개를 묶는 데 `C(|S|,2)` 번이 아니라 `|S|-1` 번이면 된다.

한 걸음 더: 애초에 **행렬의 상삼각만 훑으면** 각 간선을 한 번씩만 본다.

| 방식 | union 호출 (n=200 전부 연결) | 실측 |
|---|---|---|
| 현재 (행 안 모든 쌍) | **3,980,000** | 16.4ms |
| 행 대표에만 붙이기 | 39,800 | — |
| **상삼각만 (아래 대안 A)** | **19,900** | **0.9ms** |

`n ≤ 200` 이라 셋 다 통과한다. 하지만 `n` 이 2,000이면 현재 방식은 **40억 번**이라 즉사한다.

**2. `find` 에 경로 압축이 없다 — 한 줄이면 끝난다**

```java
public int find(int i){
    if(i != parent[i]) return find(parent[i]);   // 올라가기만 하고 갱신을 안 한다
    return i;
}
```

같은 노드를 다시 찾을 때마다 같은 경로를 처음부터 타고 올라간다.
**올라가면서 부모를 루트로 바꿔주면** 두 번째부터는 한 칸이다.

```java
private int find(int i) {
    return parent[i] == i ? i : (parent[i] = find(parent[i]));
}
```

이 문제에서 실측 최대 깊이는 17이라 체감 차이는 없다. 그래도 **유니온 파인드를 쓰면
경로 압축은 기본 세트**다 — 빠뜨리면 `n` 이 커지는 순간 O(n) 조회가 된다.

**3. union by rank / size 가 없다**

```java
if(c != n){ parent[n] = c; }   // 항상 두 번째를 첫 번째 밑으로
```

작은 트리를 큰 트리 밑에 붙이면 깊이가 안 자란다. 경로 압축과 같이 쓰면
`find` 가 사실상 O(1)(역아커만 함수)이 된다. **다만 둘 중 하나만 쓴다면 경로 압축이다** — 한 줄이라 싸다.

**4. `c` 와 `n` 은 이름이 아니다**

```java
int c = find(i);
int n = find(j);
```

`AGENTS.md` 코드 규칙: *변수명은 짧아도 되지만 `a`, `b`, `tmp` 는 안 된다.*
특히 **`n` 은 `solution(int n, ...)` 의 컴퓨터 개수와 같은 이름**이라 읽다가 걸린다.
`rootA` / `rootB` 면 무엇인지 바로 보인다.

**5. 1-indexed 라 `i+1` 변환이 코드 전체에 흩어진다**

`parent` 를 `n+1` 로 잡고 1번부터 쓰는데, `computers` 는 0-indexed다.
그래서 `union(i+1, j+1)` 처럼 **매번 +1** 을 붙이고, 세는 루프도 `1..n` 이다.
0-indexed (`parent = new int[n]`)로 맞추면 **변환이 통째로 사라진다.**
유니온 파인드에서 오프셋 실수는 단골 버그다.

**6. `find` / `union` / `parent` 가 외부에 열려 있다**

제출본의 공개 API 는 `solution` 하나면 된다. 나머지는 `private` 이 맞다.

**7. `union` 은 C++ 예약어다 — C++ 포팅 때 걸린다**

Java 에서는 문제없지만 `solution.cpp` 를 채울 때 그대로 옮기면 **컴파일 에러**다.
`unite` / `merge` 로 쓰는 게 관례다. 미리 알아둬라.

### 다른 대안

**A. 상삼각만 훑기 + 경로 압축 (권장) — 1·2·5 동시 해결**

```java
class Solution {
    private int[] parent;

    private int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));   // 경로 압축
    }

    public int solution(int n, int[][] computers) {
        parent = new int[n];                       // 0-indexed — +1 변환이 사라진다
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)        // 상삼각만 = 간선 하나당 한 번
                if (computers[i][j] == 1) parent[find(j)] = find(i);

        int answer = 0;
        for (int i = 0; i < n; i++) if (find(i) == i) answer++;
        return answer;
    }
}
```

union 호출 3,980,000 → **19,900**, 16.4ms → **0.9ms**. 코드도 더 짧다.

**B. BFS / DFS — 이 문제에선 이쪽이 더 자연스럽다**

인접 **행렬**이 통째로 주어지고 그래프가 정적이라 **한 번만 훑으면 된다.**
간선이 동적으로 추가되는 상황이 아니면 유니온 파인드의 강점이 안 살아난다.

```java
public int solution(int n, int[][] computers) {
    boolean[] visited = new boolean[n];
    int answer = 0;
    for (int s = 0; s < n; s++) {
        if (visited[s]) continue;
        answer++;                                   // 새 네트워크 하나 발견
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(s); visited[s] = true;
        while (!q.isEmpty()) {
            int x = q.poll();
            for (int y = 0; y < n; y++)
                if (computers[x][y] == 1 && !visited[y]) { visited[y] = true; q.add(y); }
        }
    }
    return answer;
}
```

| | 시간 | 특징 |
|---|---|---|
| 현재 (유니온 파인드) | O(n³·d) | — |
| 대안 A | O(n²·α) | 간선이 **나중에 추가돼도** 대응 가능 |
| 대안 B (BFS) | **O(n²)** | 인접 행렬을 정확히 한 번 훑는다. 가장 단순 |

**둘 다 쓸 줄 알아야 한다.** 유니온 파인드는 "간선이 하나씩 추가될 때 연결 여부"를 물을 때,
BFS 는 "다 주어진 그래프의 연결 요소"를 셀 때. 자세한 건 [dfs-bfs.md](../../../concepts/algorithm/dfs-bfs.md).

**C. `find` 를 반복문으로 — 여기선 불필요**

`n ≤ 200` 이라 재귀 깊이가 문제되지 않는다. 격자 100만 칸짜리에서나 고민할 일이다.

### 놓친 엣지 케이스

**제약 내에서 코드가 틀리는 케이스는 없다.** 확인한 것:

| 케이스 | 입력 | 기대 | 결과 |
|---|---|---|---|
| 예제 1 | `n=3`, 두 덩어리 | 2 | ✅ |
| 예제 2 | `n=3`, 전부 연결 | 1 | ✅ |
| **`n = 1`** | `[[1]]` | 1 | ✅ 안쪽 루프가 `i < 0` 이라 안 돌고, 자기 루트라 1 |
| **전부 연결** | `n=200` 전부 1 | 1 | ✅ 16.4ms |
| **전부 독립** | 대각선만 1 | 200 | ✅ union 이 한 번도 안 불림 |
| 무작위 500건 | `n ≤ 30` | BFS 와 일치 | ✅ 불일치 0 |

- **오버플로우 없음** — `n ≤ 200`, 인덱스뿐이다.
- **재귀 깊이 안전** — 실측 최대 17, 이론상 최악도 200이라 스택에 여유가 크다.
- `computers[i][i] == 1` 이 보장되므로 `i == j` 를 따로 거를 필요가 없다.
  대각선에서 `union(i+1, i+1)` 이 불려도 `c == n` 이라 아무 일도 안 한다.

### 잘한 점

행 안의 1들이 전부 같은 네트워크라는 관찰은 옳다. 묶는 횟수만 과했다.

---

## 다음 할 일

- [ ] 대안 A 로 리팩터링 — 상삼각 + 경로 압축 + 0-indexed, `refactor:` 커밋
- [ ] **대안 B(BFS)도 한 번 써 보기** — Phase 0 목표가 "BFS 뼈대를 보지 않고 치기"다.
      같은 문제를 두 방법으로 푸는 게 제일 빨리 는다
- [ ] **C++ 풀이 작성** — `solution.cpp` 이 템플릿 그대로다. `union` 이 예약어라는 것에 주의
- [ ] `concepts/data-structure/union-find.md` 작성 — 경로 압축 / union by rank 를 이 문제 실측과 함께
