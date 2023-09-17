# GyeongNam GyeongMae

## Contributer

- 임채성 | [@puleugo](https://github.com/puleugo)
- 김동현 | [@haroya01](https://github.com/haroya01)

## Conventions

- [컨벤션 정리 페이지](https://github.com/gyeongnam-gyeongmae/server/wiki/02.-Convention)

## 로컬 환경 구축 방법

gradle build & 도커 컴포즈 빌드 & 실행

```bash
./gradlew build && docker compose build && docker compose up
```

로컬 환경에서는 `ghcr.io/gyeongnam-gyeongmae/gyeongnam-gyeongmae-api:test`으로 실행할 수 있습니다.<br><ins>실서버의 태그와 겹치지 않게
주의부탁드립니다.</ins>

> 로컬환경의 스웨거 주소는 `http://localhost:5001/swagger-ui/index.html` 입니다.
