# GyeongNam GyeongMae

## Contributer

- 임채성 | [@puleugo](https://github.com/puleugo)
- 김동현 | [@haroya01](https://github.com/haroya01)

## Conventions

- [Commit Convention](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)
- [Code Style Convention](https://google.github.io/styleguide/javaguide.html)
- [Clean Code Checklists](https://github.com/woowacourse/woowacourse-docs/blob/main/cleancode/pr_checklist.md)
- [Java Folder Structure]
- Branch Naming Convention
    - 종류
        - `main` : 제품 출시 가능 브랜치
        - `release` : 이번 출시 버전을 준비하는 브랜치
        - `dev` : 다음 출시 버전을 개발하는 브랜치
            - `feat` : 새로운 기능 개발
            - `refactor` : 기존 개발 리팩토링
        - `hotfix` : 출시 버전에서 발생한 버그를 수정 하는 브랜치
    - Branch 네이밍 규칙
        - `{종류}/{issue-number}-{feature-name}`
        - 예시: `feat/1/login`

## .env file to environment variable

```bash
set -a
source .env
set +a
```