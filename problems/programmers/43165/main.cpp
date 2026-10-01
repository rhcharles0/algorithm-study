// 로컬 검증용. 제출하지 않는다.
// 실행: g++ -std=c++17 -O2 main.cpp solution.cpp -o /tmp/sol && /tmp/sol
#include <bits/stdc++.h>
using namespace std;

int solution(vector<int> arr);   // solution.cpp 에 있다

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    assert(solution({1, 2, 3}) == 0);

    cout << "모든 예제 통과\n";
}
