# Spring Boot Multi-Database Demo

Spring Boot, MyBatis, PostgreSQL을 사용하여 2개의 데이터베이스를 연결하는 예제 프로젝트입니다.

## 기술 스택

- **Spring Boot 3.2.0**
- **MyBatis 3.0.3**
- **PostgreSQL**
- **Lombok**
- **Java 17**

## 프로젝트 구조

```
src/main/java/com/example/multidb/
├── config/
│   ├── PrimaryDataSourceConfig.java    # Primary DB 설정
│   └── SecondaryDataSourceConfig.java  # Secondary DB 설정
├── entity/
│   ├── primary/
│   │   └── User.java                   # Primary DB 엔티티
│   └── secondary/
│       └── Product.java                # Secondary DB 엔티티
├── mapper/
│   ├── primary/
│   │   └── UserMapper.java             # Primary DB 매퍼
│   └── secondary/
│       └── ProductMapper.java          # Secondary DB 매퍼
├── service/
│   ├── UserService.java                # User 서비스
│   └── ProductService.java             # Product 서비스
├── controller/
│   ├── UserController.java             # User REST API
│   └── ProductController.java          # Product REST API
└── MultiDbApplication.java             # 메인 애플리케이션

src/main/resources/
├── application.yml                     # 애플리케이션 설정
└── mapper/
    ├── primary/
    │   └── UserMapper.xml              # Primary DB SQL 매퍼
    └── secondary/
        └── ProductMapper.xml           # Secondary DB SQL 매퍼
```

## 데이터베이스 설정

### application.yml 주요 설정

```yaml
spring:
  datasource:
    primary:
      jdbc-url: jdbc:postgresql://localhost:5432/primary_db
      username: postgres
      password: password
      driver-class-name: org.postgresql.Driver
      hikari:
        maximum-pool-size: 10
        minimum-idle: 5
    
    secondary:
      jdbc-url: jdbc:postgresql://localhost:5432/secondary_db
      username: postgres
      password: password
      driver-class-name: org.postgresql.Driver
      hikari:
        maximum-pool-size: 10
        minimum-idle: 5

mybatis:
  primary:
    mapper-locations: classpath:mapper/primary/*.xml
    type-aliases-package: com.example.multidb.entity.primary
  
  secondary:
    mapper-locations: classpath:mapper/secondary/*.xml
    type-aliases-package: com.example.multidb.entity.secondary
```

## 주요 특징

### 1. 데이터소스 분리
- `@Primary` 어노테이션으로 기본 데이터소스 지정
- `@Qualifier`를 사용하여 각 데이터소스 구분

### 2. MyBatis 설정 분리
- 각 데이터베이스별로 별도의 SqlSessionFactory 설정
- 매퍼 패키지와 XML 위치 분리

### 3. 트랜잭션 관리
- 각 데이터베이스별로 별도의 TransactionManager 설정
- `@Transactional(transactionManager = "xxxTransactionManager")` 사용

### 4. Lombok 활용
- `@Data`, `@Builder`, `@RequiredArgsConstructor` 등 활용
- 보일러플레이트 코드 최소화

## 실행 방법

### 1. PostgreSQL 데이터베이스 생성
```sql
-- Primary DB
CREATE DATABASE primary_db;

-- Secondary DB  
CREATE DATABASE secondary_db;
```

### 2. 테이블 생성
```bash
# Primary DB 스키마 실행
psql -h localhost -U postgres -d primary_db -f database/primary_db_schema.sql

# Secondary DB 스키마 실행
psql -h localhost -U postgres -d secondary_db -f database/secondary_db_schema.sql
```

### 3. 애플리케이션 실행
```bash
mvn spring-boot:run
```

## API 엔드포인트

### User API (Primary DB)
- `GET /api/users` - 모든 사용자 조회
- `GET /api/users/{id}` - ID로 사용자 조회
- `GET /api/users/username/{username}` - 사용자명으로 조회
- `POST /api/users` - 사용자 생성
- `PUT /api/users/{id}` - 사용자 수정
- `DELETE /api/users/{id}` - 사용자 삭제

### Product API (Secondary DB)
- `GET /api/products` - 모든 상품 조회
- `GET /api/products/{id}` - ID로 상품 조회
- `GET /api/products/category/{category}` - 카테고리별 상품 조회
- `POST /api/products` - 상품 생성
- `PUT /api/products/{id}` - 상품 수정
- `DELETE /api/products/{id}` - 상품 삭제

## 설정 팁

### 1. 환경별 설정 분리
```yaml
# application-dev.yml
spring:
  datasource:
    primary:
      jdbc-url: jdbc:postgresql://localhost:5432/primary_db_dev
    secondary:
      jdbc-url: jdbc:postgresql://localhost:5432/secondary_db_dev

# application-prod.yml
spring:
  datasource:
    primary:
      jdbc-url: jdbc:postgresql://prod-server:5432/primary_db
    secondary:
      jdbc-url: jdbc:postgresql://prod-server:5432/secondary_db
```

### 2. 커넥션 풀 최적화
```yaml
spring:
  datasource:
    primary:
      hikari:
        maximum-pool-size: 20
        minimum-idle: 10
        connection-timeout: 30000
        idle-timeout: 600000
        max-lifetime: 1800000
```

### 3. MyBatis 설정 최적화
```yaml
mybatis:
  primary:
    configuration:
      cache-enabled: true
      lazy-loading-enabled: true
      aggressive-lazy-loading: false
      default-executor-type: SIMPLE
      default-statement-timeout: 30
      default-fetch-size: 100
```

## 주의사항

1. **트랜잭션 관리**: 각 데이터베이스별로 별도의 트랜잭션 매니저를 사용하므로, 크로스 데이터베이스 트랜잭션은 지원되지 않습니다.

2. **데이터소스 구분**: `@Qualifier` 어노테이션을 사용하여 올바른 데이터소스를 주입받도록 주의해야 합니다.

3. **매퍼 패키지 분리**: 각 데이터베이스별로 매퍼 패키지를 분리하여 혼동을 방지합니다.

4. **로깅 설정**: 각 데이터베이스의 쿼리 로그를 구분하여 확인할 수 있도록 로깅 설정을 적절히 구성합니다.
