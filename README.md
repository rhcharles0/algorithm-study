# algorithm-study

알고리즘 · 자료구조 공부 기록. 코딩테스트 준비용.

**Java 주력, C++ 병행.** 같은 문제를 두 언어로 풀면서 Java Collections ↔ C++ STL 대응을 몸에 익히는 게 목적이다.
풀이 코드만 남기지 않고, 각 문제마다 **복잡도 분석과 피드백**을, 막혔던 주제는 **개념 노트**로 정리한다.

---

## 구조

```
concepts/                       개념 정리 (글)
├── data-structure/             스택, 큐, 힙, 트리, 해시, 트라이 ...
├── algorithm/                  정렬, 이분탐색, DP, DFS/BFS, 최단경로 ...
├── _template.md                개념 노트 템플릿
└── README.md                   주제 목록 = 진도표

problems/                       문제 풀이 (코드) — 출처별
├── programmers/
├── leetcode/
├── swea/
├── codetree/
└── baekjoon/        (BOJ 서비스 종료, 복귀 대비해 유지)
     └── <번호>/
          ├── README.md         문제 이름 + 출처 + 복잡도 + 피드백
          ├── Solution.java
          └── solution.cpp

_template-problem/              문제 폴더 템플릿
.claude/skills/problem-feedback/ /problem-feedback 스킬 정의
```

### 명명 규칙

출처는 폴더로, 폴더명은 **문제 번호만** 쓴다. 문제 이름은 폴더 안 `README.md` 제목에 적는다.

| 출처 | 경로 예시 | 문제 |
|---|---|---|
| 프로그래머스 | `problems/programmers/42586/` | 기능개발 |
| LeetCode | `problems/leetcode/0001/` — 번호 4자리 zero-pad | Two Sum |
| SWEA | `problems/swea/1954/` | 달팽이 숫자 |
| 코드트리 | `problems/codetree/<문제-slug>/` | 번호가 없어 영문 slug 사용 |
| 백준 | `problems/baekjoon/1912/` | 연속합 — BOJ 종료로 당분간 미사용 |

> 폴더명에 이름을 넣지 않는 이유: 한글 경로가 길어지고, 문제 이름을 잘못 옮겨 적으면 고치기 번거롭다.
> 번호만 있으면 출처 + 번호로 문제를 바로 찾을 수 있다.

개념 노트는 주제당 `.md` 파일 하나: `concepts/data-structure/heap.md`

---

## 풀이 진행 순서

**1. 폴더 생성**

```bash
./ps pg 42586          # → problems/programmers/42586/ 생성
cd problems/programmers/42586
```

| 출처 인자 | 폴더 |
|---|---|
| `pg` | `programmers` |
| `swea` | `swea` |
| `ct` | `codetree` |
| `lc` | `leetcode` — 번호를 4자리로 자동 zero-pad (`./ps lc 1` → `0001`) |
| `boj` | `baekjoon` |

이미 있는 폴더면 덮어쓰지 않고 멈춘다. 레포 루트에서 실행한다 (설정 불필요).

**2. README 위쪽 먼저 작성** — 제목(문제 이름) / 출처 / 번호 / 링크 / 난이도 / 푼 날짜, 그리고 `문제 요약`, `풀이 접근`.

> `풀이 접근`은 **코드를 쓰기 전에** 채운다. 어떤 자료구조로 왜 접근할지 두세 줄.
> 여기서 막히면 아직 코드를 쓸 준비가 안 된 것이다.

`카테고리`, `관련 개념`, `---` 아래 영역은 비워 둔다. 6단계에서 채워진다.

**3. Java 풀이**

```bash
java -ea Solution.java
```

**4. C++ 풀이**

Java 코드를 그대로 옮기지 말고 C++답게 쓴다 (참조 전달, `priority_queue`, `unordered_map` 등).

```bash
g++ -std=c++17 -O2 solution.cpp -o /tmp/sol && /tmp/sol
```

**5. 제출**해서 통과 확인.

**6. 피드백**

```
/problem-feedback problems/programmers/42586
```

카테고리 · 시간/공간 복잡도 · 현재 풀이의 문제점 · 다른 대안 · 놓친 엣지 케이스가 README에 채워지고,
관련 개념 노트로 링크가 걸린다.

**7. 개념 노트** — 피드백이 짚어준 개념 중 노트가 없는 것만.

```bash
cp concepts/_template.md concepts/data-structure/heap.md
```

> 문제를 풀기 전에 개념부터 정리하려 들지 않는다. 막혔던 개념만 쓰는 쪽이 오래 남는다.

**8. 커밋**

```bash
git add . && git commit -m "solve: PG 42586 기능개발 (java, cpp)"
```

막혔을 땐 30분까지 붙잡고, 넘어가면 답을 보되 `풀이 접근`에 **"왜 스스로 떠올리지 못했는지"** 한 줄을 남긴다.

---

## 실행 환경

빌드 시스템 없이 단일 파일로 실행한다. 빌드 산출물(`*.class`, 실행 파일, IDE 설정)은 `.gitignore` 처리되어 **순수 소스 코드만 커밋된다.**

| 언어 | 요구 버전 | 실행 |
|---|---|---|
| Java | JDK 11+ | `java -ea Solution.java` — **`-ea` 없으면 `assert` 가 무시된다** |
| C++ | g++ / clang++ (C++17) | `g++ -std=c++17 -O2 solution.cpp -o /tmp/sol && /tmp/sol` |

- 표준입력 문제는 `< input.txt` 로 테스트한다.
- 프로그래머스처럼 함수만 제출하는 문제는 각 파일의 `main` 에서 `assert` 로 예제 케이스를 검증한다.
- 실행 파일은 `/tmp` 로 빼서 레포를 더럽히지 않는다.

---

## `/problem-feedback` 스킬

문제 폴더의 코드를 읽고 그 폴더 `README.md` 의 `---` 아래를 갱신한다. **코드는 고치지 않는다.**

```
/problem-feedback problems/programmers/42586
/problem-feedback                                      # 인자 없으면 가장 최근 수정된 문제 폴더
```

채워지는 항목:

- **출처 · 문제 번호 · 링크** — 폴더 경로(`problems/<출처>/<번호>`)에서 뽑아 상단 표에 채운다
- **카테고리** — 자료구조/알고리즘 태그, 관련 `concepts/` 노트 링크
- **복잡도** — 언어별 시간·공간 복잡도, 문제의 입력 제한과 대조한 통과 가능성 판정
- **현재 풀이의 문제점** — 불필요한 정렬/복사, `Scanner` 사용, 오버플로우, 값 복사 등 실제 코드에 있는 것만
- **다른 대안** — 더 나은 접근을 복잡도 비교와 함께
- **놓친 엣지 케이스** — 빈 입력, 원소 1개, 경계값, 음수 등

정의: [.claude/skills/problem-feedback/SKILL.md](.claude/skills/problem-feedback/SKILL.md)

---

## 커밋 컨벤션

```
<type>: <설명>
```

| type | 용도 | 예시 |
|---|---|---|
| `solve:` | 문제 풀이 추가 | `solve: PG 42586 기능개발 (java, cpp)` |
| `feedback:` | 피드백 추가/수정 | `feedback: PG 42586 복잡도 분석` |
| `note:` | 개념 노트 | `note: 힙 / 우선순위 큐` |
| `fix:` | 틀린 풀이·오타 수정 | `fix: SWEA 2382 int 오버플로우` |
| `refactor:` | 통과한 풀이 개선 | `refactor: SWEA 5656 O(N²) → O(N log N)` |
| `docs:` | 문서 | `docs: 실행 방법 보강` |
| `chore:` | 구조, 템플릿, 설정 | `chore: gitignore에 cmake 산출물 추가` |

출처 약어: `PG`(프로그래머스) · `SWEA` · `CT`(코드트리) · `LC`(LeetCode) · `BOJ`(백준)

`Merge`, `Revert` 로 시작하는 커밋은 검사 대상에서 제외된다.

### 규칙 강제

| 시점 | 수단 |
|---|---|
| 커밋할 때 (로컬) | `.githooks/commit-msg` — 형식이 틀리면 커밋 자체가 막힌다 |
| push / PR (원격) | [.github/workflows/commit-convention.yml](.github/workflows/commit-convention.yml) — 위반 커밋이 있으면 CI 실패 |

클론 후 **한 번만** 설정한다:

```bash
git config core.hooksPath .githooks
git config alias.cm '!bash scripts/git-cm.sh'
```

### `git cm` — 타입 자동 완성

직접 `git commit -m "..."` 을 치면 오타가 나므로, 타입은 한 글자로 고른다.

```
$ git cm
  s) solve    b) feedback   n) note    f) fix
  r) refactor d) docs       c) chore
type:            ← f 를 누르면
fix:             ← "fix: " 가 자동으로 붙고 설명만 입력
```

| 키 | type | 키 | type |
|---|---|---|---|
| `s` | solve | `r` | refactor |
| `b` | feedback | `d` | docs |
| `n` | note | `c` | chore |
| `f` | fix | | |

`f` 는 `fix`, feedback 은 `b` 다 (첫 글자가 겹쳐서).

## 라이선스

[MIT](LICENSE)
