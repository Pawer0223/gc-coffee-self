# gc-coffee-self

- 판매 상품목록을 조회할 수 있다.
- 상품 추가 후, 이메일, 주소, 우편번호를 입력하여 주문할 수 있다.

## ERD
<img width="635" alt="image" src="https://user-images.githubusercontent.com/26343023/167268126-d85b150f-4531-4059-9c76-275f4903c93f.png">

## API

- `GET` /api/v1/products
  - 등록 가능한 상품 목록을 조회할 수 있다.

- `POST` /api/v1/orders
  - 주문을 생성할 수 있다.


## 개선 사항

### PK값을 UUID가 아닌 AUTO_INCREAMENT를 사용
- UUID보다 적은 메모리 공간을 사용하기 위함.
- UUID를 조작하는 번거로움을 줄이기 위함.
  - `AS-IS`
    - `UUID_TO_BIN(:productId)`
  - `TO-BE`
    - `productId`

### createdAt, updatedAt의 Default 값 부여
- 편의성
- `created_at   datetime(6) DEFAULT NOW(6)`

### DTO 분리
- Dto란 데이터 교환을 위해 사용하는 객체
- `AS-IS` 
  - Entity 객체를 View에 직접 반환 함
  - 이 경우 민감한 도메인 비즈니스 기능이 노출될 수 있으며 Model과 View 사이에 의존성이 생길 수 있다.
  - View의 변경이 Entity에게 영향을 끼칠 수 있다.
- `TO-BE`
  - View에 반환하는 Dto객체를 별도로 정의 함.

### Entity 객체 개선
- pk로 equals & hashCode 재정의
  - 엔티티의 식별자는 바뀌지않고 고유하기 때문에 두 엔티티 객체의 식별자가 같으면 두 엔티티는 같다고 판단할 수 있다.

### 테스트코드 강화
- 클론 프로젝트에서 `@SpringBootTest`로 Repository 테스트가 진행 됨.
  - 이 경우 테스트 진행 시 사용되지 않는 대상도 함께 로딩되기 때문에 필요한 객체만 생성될 수 있도록 개선
  - `@SpringBootTest(classes = {TestJdbcConfig.class, ProductJdbcRepository.class})`

- @Order로 수행되던 테스트 코드의 순서를 제거
  - `Isolated`: 다른 테스트에 종속적인 테스트는 절대로 작성하지 않는다.
  - `@BeforeEach`와 `@Transactional`을 적용하여 개선

- @RestController 테스트 코드 작성
  - `@MockMvc` 활용
  
## 참조

- [UUID와 Auto_Increament의 PK사용 시점](https://americanopeople.tistory.com/378)
- [DTO의 사용범위](https://tecoble.techcourse.co.kr/post/2021-04-25-dto-layer-scope/)
- [SpringBootTest](https://meetup.toast.com/posts/124)
- [Test 기본원칙](https://tech.buzzvil.com/handbook/test-principles/)
- [MockMvc](https://astrid-dm.tistory.com/536)
- [equals와 hashCode 사용하기](https://jojoldu.tistory.com/134)
