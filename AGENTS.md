# AGENTS.md

알고리즘 / 자료구조 공부 레포. 에이전트가 이 레포에서 작업할 때 지킬 규칙.
전체 소개와 워크플로는 [README.md](README.md) 참고.

## 스킬

- **`ponytail`** — 이 레포의 모든 코드 작업에 적용한다. 가장 단순하게 동작하는 풀이가 정답이다.
- **`problem-feedback`** — 문제 폴더의 코드를 읽고 그 폴더 `README.md` 의 `---` 아래를 갱신한다. 정의: [.claude/skills/problem-feedback/SKILL.md](.claude/skills/problem-feedback/SKILL.md)

## 구조

```
concepts/{data-structure,algorithm}/<주제>.md      개념 노트
problems/<출처>/<번호>/                             문제 풀이
  ├── README.md  Solution.java  solution.cpp
_template-problem/    concepts/_template.md        템플릿
```

출처: `programmers` `swea` `codetree` `leetcode` `baekjoon`(BOJ 종료, 미사용) · 폴더명은 **번호만** (`problems/programmers/42586/`) · LeetCode 는 4자리 zero-pad (`0001`)
문제 이름은 폴더명이 아니라 그 안 `README.md` 제목에 적는다.

## 코드 규칙

- **Java 11+ / C++17.** 한 문제는 두 언어 모두로 푼다. C++ 는 Java 번역이 아니라 STL 관용구로 쓴다.
- **빌드 시스템 없음.** `java -ea Solution.java`, `g++ -std=c++17 -O2 solution.cpp -o /tmp/sol` 로 끝. Gradle/Maven/CMake 파일을 만들지 않는다.
- 실행 파일은 `/tmp` 로 뺀다. 빌드 산출물은 커밋하지 않는다 (`.gitignore`).
- 예제 케이스는 각 파일 `main` 의 `assert` 로 검증한다. 테스트 프레임워크를 도입하지 않는다.
- 파일명 고정: `Solution.java`, `solution.cpp`. 한 문제 = 한 파일 (언어당).

## 코테 풀이에서의 ponytail

과한 추상화는 버리되, **복잡도는 타협하지 않는다.**

- 클래스 분리·인터페이스·헬퍼 계층 금지. 코테 코드는 제출하고 끝이다.
- 자료구조는 표준 라이브러리에서 꺼내 쓴다. 힙·해시맵·덱을 직접 구현하지 않는다
  (단 `concepts/` 개념 노트의 학습용 구현은 예외).
- 그러나 O(N²) 를 O(N log N) 으로 줄이는 건 "과한 최적화"가 아니라 **통과 조건**이다. 짧다고 느린 걸 고르지 않는다.
- 변수명은 짧아도 되지만 `a`, `b`, `tmp` 는 안 된다. 6개월 뒤 다시 읽는다.

## 에이전트가 하지 말 것

- **사용자 대신 문제를 풀지 않는다.** 풀이 코드는 사용자가 쓴다. 요청받으면 힌트와 접근법을 주고, 정답 코드는 명시적으로 요구할 때만 쓴다.
- `/problem-feedback` 은 피드백만 쓴다. 코드를 고치지 않는다. 고쳐달라고 하면 그때 고친다.
- 문제 README 에서 **사용자가 이미 쓴 내용은 덮어쓰지 않는다.** 비어 있는 칸만 채운다.
- 푼 문제 목록을 README 에 손으로 관리하지 않는다. 반드시 썩는다.

## 커밋

**에이전트도 이 컨벤션을 반드시 지켜서 커밋 메시지를 작성한다.** 지키지 않으면 훅이 커밋을 거부한다.

형식 `<type>: <설명>` — **CI 와 `.githooks/commit-msg` 가 강제한다. 어기면 커밋/푸시가 막힌다.**

type: `solve` `feedback` `note` `fix` `refactor` `docs` `chore`
출처 약어: `PG` `SWEA` `CT`(코드트리) `LC` `BOJ`

```
solve: PG 42586 기능개발 (java, cpp)
feedback: PG 42586 복잡도 분석
note: 힙 / 우선순위 큐
```

작업 내용이 여러 갈래면 **한 덩어리로 몰지 말고 갈래별로 나눠 커밋한다.**
(예: 구조 변경 / 템플릿 추가 / 문서 수정은 각각 별개 커밋)

사람은 `git cm` 으로 커밋한다 (타입 한 글자 → 자동 완성). 클론 직후 한 번:

```bash
git config core.hooksPath .githooks
git config alias.cm '!bash scripts/git-cm.sh'
```

문제 폴더는 `./ps <pg|swea|ct|lc|boj> <번호>` 로 만든다. `cp -r _template-problem ...` 을 직접 치지 않는다.
