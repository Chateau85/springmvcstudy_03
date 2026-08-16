# springmvcstudy_03

Spring MVC와 Thymeleaf로 상품 등록·조회·수정 및 PRG(Post/Redirect/Get) 패턴을 학습하는 예제 프로젝트입니다.

## 실행 환경

- Java 17
- Spring Boot 3.5.16
- Gradle 8.14.3 (Wrapper 포함)
- Spring MVC 6 / Jakarta Annotation 2
- Thymeleaf

## 빌드 및 실행

```powershell
.\gradlew.bat clean test bootJar
.\gradlew.bat bootRun
```

실행 후 `http://localhost:8080/` 또는 `http://localhost:8080/basic/items`에서 상품 예제를 확인할 수 있습니다.

## 입력 처리

- 상품명은 공백이 아닌 문자열이어야 합니다.
- 가격과 수량은 0 이상이어야 합니다.
- 존재하지 않는 상품은 HTTP 404, 잘못된 상품 입력은 HTTP 400을 반환합니다.
- Thymeleaf의 기본 이스케이프를 사용해 상품명을 안전하게 출력합니다.

## 보안 및 의존성 점검

SpotBugs와 FindSecBugs 정적 분석 및 CycloneDX SBOM은 다음 명령으로 실행할 수 있습니다.

```powershell
.\gradlew.bat spotbugsMain spotbugsTest
.\gradlew.bat cyclonedxBom
```

결과는 각각 `build/reports/spotbugs/`와 `build/reports/cyclonedx/`에 생성됩니다.
