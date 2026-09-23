# 백트래킹 + 비트마스크 방문 처리

| 항목 | 내용 |
|---|---|
| 분류 | 알고리즘 |
| 관련 문제 | [PG 42839 소수 찾기](../../problems/programmers/42839) |

## 한 줄 정의

모든 경우를 재귀로 만들되, **답이 될 수 없는 가지는 만들다 말고 되돌아가는(backtrack)** 완전탐색.
"누구를 이미 썼는가"를 `boolean[]` 대신 **`int` 한 개의 비트**로 관리하는 게 비트마스크 방문 처리다.

## 언제 쓰나

- 입력 크기가 **N ≤ 10 정도**이고 "모든 경우"를 봐야 할 때 (순열 N!, 부분집합 2ᴺ)
- 삼성 코테에서는 **"완전탐색으로 후보를 만들고 → 시뮬레이션으로 점수를 매긴다"** 형태로 거의 항상 나온다
  - 예: 연산자를 끼워넣는 모든 순서 / 벽 3개를 세우는 모든 조합 / CCTV 방향의 모든 경우

N 이 20을 넘으면 백트래킹은 답이 아니다. DP나 그리디를 의심해라.

## 동작 원리

```
재귀 진입 → (가지치기: 여기서 더 가봐야 소용없나?) → 현재 상태 기록
         → 안 쓴 후보마다: 쓴다 표시 → 재귀 → 표시 해제(되돌리기)
```

**비트마스크를 쓰면 "표시 해제"가 필요 없다.** `visited | 1 << i` 를 **인자로 넘기면**
호출이 끝나는 순간 원래 `visited` 가 그대로 남아 있기 때문이다.

```java
// boolean[] 방식 — 되돌리기를 반드시 짝으로 써야 한다
used[i] = true;
recur(...);
used[i] = false;      // ← 이걸 빼먹는 게 백트래킹 1위 버그

// 비트마스크 방식 — 되돌릴 게 없다
recur(..., visited | 1 << i);
```

### 비트 연산 치트시트

| 하고 싶은 것 | 식 |
|---|---|
| `i` 번을 썼나? | `(visited & 1 << i) != 0` |
| `i` 번을 쓴 상태 | `visited \| 1 << i` |
| `i` 번을 지운 상태 | `visited & ~(1 << i)` |
| 전부 다 쓴 상태 | `(1 << n) - 1` |
| 쓴 개수 | Java `Integer.bitCount(v)` · C++ `__builtin_popcount(v)` |
| 켜진 최하위 비트 | `v & -v` |

## 세 가지 기본 패턴

`n` 개 중에서 고르는 방식에 따라 뼈대가 다르다. **이 셋을 구분하는 게 핵심이다.**

| 패턴 | 순서가 의미 있나 | 개수 | 뼈대의 차이 |
|---|---|---|---|
| **순열** | O (`12` ≠ `21`) | N! | 매번 `0..n-1` 전체를 돌고 `visited` 로 거른다 |
| **조합** | X (`12` = `21`) | ₙCᵣ | `start` 인덱스부터만 돈다 (뒤로 안 간다) |
| **부분집합** | X, 크기 자유 | 2ᴺ | 각 원소마다 "넣는다 / 안 넣는다" 2갈래 |

## 복잡도

| 연산 | 시간 |
|---|---|
| 순열 전체 | O(N! · 각 경우 처리비용) |
| 조합 전체 | O(ₙCᵣ · 〃) |
| 부분집합 전체 | O(2ᴺ · 〃) |
| **부분집합의 모든 순열** | O(Σₖ P(N,k)) — N=7 이면 **13,699** |

N=10 순열이면 3,628,800 — 각 경우 처리가 O(N²)만 돼도 3억이라 위험하다.
**"경우의 수 × 한 경우당 비용"을 코드 쓰기 전에 곱해봐라.**

## 구현

### 순열 — 모든 순서

```java
static void perm(int n, int visited, List<Integer> path) {
    if (visited == (1 << n) - 1) {        // 전부 썼으면 완성
        process(path);
        return;
    }
    for (int i = 0; i < n; i++) {
        if ((visited & 1 << i) != 0) continue;
        path.add(i);
        perm(n, visited | 1 << i, path);
        path.remove(path.size() - 1);     // path 는 공유하므로 되돌린다
    }
}
```

### 부분집합의 모든 순열 — 삼성/PG 에 제일 자주 나오는 형태

**크기가 1인 것부터 N인 것까지, 순서까지 모두** 만든다. (PG 42839 소수 찾기가 이 형태)

```java
static void build(String s, int cur, int visited) {
    if (cur > 0) process(cur);            // 중간 단계도 전부 답 후보다
    for (int i = 0; i < s.length(); i++) {
        if ((visited & 1 << i) != 0) continue;
        build(s, cur * 10 + s.charAt(i) - '0', visited | 1 << i);
    }
}
```

```cpp
void build(const string& s, int cur, int visited) {
    if (cur > 0) process(cur);
    for (int i = 0; i < (int)s.size(); i++) {
        if (visited & 1 << i) continue;
        build(s, cur * 10 + s[i] - '0', visited | 1 << i);
    }
}
```

### 조합 — `start` 로 뒤만 본다

```java
static void comb(int[] arr, int start, int r, List<Integer> path) {
    if (path.size() == r) { process(path); return; }
    for (int i = start; i < arr.length; i++) {   // ← start 부터
        path.add(arr[i]);
        comb(arr, i + 1, r, path);
        path.remove(path.size() - 1);
    }
}
```

조합은 순서가 무의미하므로 **비트마스크가 필요 없다.** `start` 하나로 중복이 사라진다.

## 자주 하는 실수

### 1. "건너뛰기" 분기를 따로 만든다 — 중복 탐색

```java
// ✗ 안 쓰는 선택까지 재귀로 만든다
recur(s, cur,                      visited | 1 << i);   // 건너뛰기
recur(s, cur * 10 + digit,         visited | 1 << i);   // 사용
```

**"안 쓴다"는 선택은 애초에 고르지 않으면 되는 것**이라, 같은 결과를 여러 경로로 만들게 된다.
`Set` 으로 중복을 지우면 답은 맞지만 탐색량이 폭발한다.

| | 재귀 호출 수 (N=7) | 실측 |
|---|---|---|
| 건너뛰기 분기 있음 | 1,063,623 | 39ms |
| 없음 (위 `build` 형태) | **13,700** | **2.6ms** |

> **중간 단계에서 이미 답을 기록하고 있다면, 건너뛰기 분기는 필요 없다.**
> 1자리 → 2자리 → … 가 재귀 경로 위에 자연스럽게 전부 등장하기 때문이다.

### 2. `boolean[]` 의 되돌리기를 빼먹는다

비트마스크를 인자로 넘기면 이 버그 자체가 생기지 않는다. **N ≤ 20 이면 비트마스크를 기본으로 써라.**

### 3. static 상태를 초기화하지 않는다

`Set`, 결과 리스트, 방문 배열을 static 으로 두고 초기화를 빼먹으면
**두 번째 테스트케이스부터 전부 틀린다.** 프로그래머스·SWEA 모두 한 프로세스에서 여러 케이스를 돌린다.

### 4. 가지치기를 안 한다

백트래킹의 본질은 **되돌아가기**가 아니라 **안 가보기**다.
"지금까지 만든 값이 이미 최선보다 나쁘다" 같은 조건을 재귀 맨 위에 넣으면 수십 배가 빨라진다.

```java
if (best <= cur) return;   // 더 가봐야 소용없다
```

### 5. 비트 연산자 우선순위

Java/C++ 모두 `&` 가 `==` 보다 **낮다.** 괄호 없이 쓰면 컴파일 에러거나 의도와 다르게 동작한다.

```java
if (visited & 1 << i != 0)      // ✗ (1 << (i != 0)) 로 해석
if ((visited & 1 << i) != 0)    // ✓
```

## 표준 라이브러리

| | Java | C++ |
|---|---|---|
| 켜진 비트 수 | `Integer.bitCount(v)` | `__builtin_popcount(v)` |
| 순열 직접 생성 | 없음 (직접 구현) | `next_permutation(v.begin(), v.end())` |
| 경로 저장 | `ArrayDeque<Integer>` / `int[]` | `vector<int>` |
| 결과 중복 제거 | `HashSet<Integer>` | `unordered_set<int>` |

C++ 은 `next_permutation` 이 있어서 **"N개 전부를 쓰는 순열"** 은 재귀 없이 끝난다.
단 **정렬된 상태에서 시작**해야 모든 순열이 나온다.

```cpp
sort(v.begin(), v.end());
do { process(v); } while (next_permutation(v.begin(), v.end()));
```

Java 에는 대응물이 없으니 위 `perm` 뼈대를 외워두는 게 낫다.

## 관련 문제

- [PG 42839 소수 찾기](../../problems/programmers/42839) — 부분집합의 모든 순열
- PG 42839 이후: SWEA `4008` 숫자 만들기 (연산자 순열), SWEA `4012` 요리사 (조합)
- SWEA `1767` 프로세서 연결하기 — 가지치기가 없으면 시간 초과
- SWEA `2112` 보호 필름 — 조기 종료(가지치기)가 핵심
