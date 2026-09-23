#!/bin/bash
# git cm — 타입 한 글자 고르고 메시지만 입력하면 컨벤션에 맞게 커밋한다.
set -e

echo "  s) solve    b) feedback   n) note    f) fix"
echo "  r) refactor d) docs       c) chore"
read -rsn1 -p "type: " k
case "$k" in
  s) t=solve ;;    b) t=feedback ;; n) t=note ;;  f) t=fix ;;
  r) t=refactor ;; d) t=docs ;;     c) t=chore ;;
  *) echo "취소"; exit 1 ;;
esac

read -rp "$t: " msg
[ -z "$msg" ] && { echo "메시지가 비었습니다"; exit 1; }

git commit -m "$t: $msg" "$@"
