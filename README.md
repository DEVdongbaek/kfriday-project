> 반갑습니다. 
>
>더 나은 협업을 위한 코드를 작성 하기 위해 노력하는 이승건입니다.
>
>저는 동료가 같이 코드를 작성 하고 싶은, 그리고 누구나 이해할 수 있는 코드를 작성하는 개발자가 되고 싶습니다.
>
>아직 많이 부족하지만 최선을 다하여 코드를 작성하였으니 후배 개발자라고 생각해주시고 쓴 소리나 피드백등을 해주신다면 너무 감사드릴 것 같습니다.
>
>이렇게 만나뵙게 되어서 진심으로 영광이며, 감사드립니다.
>
>감사합니다.
>
> -- 이승건 드림 --

<br />

## 🥰 코드간 참고 사항

### 1️⃣ 비즈니스간 강결합을 피하기 위해 Event Handling 방식을 사용하였습니다.

*PackageEventHandler.class*

```java
@Component
@RequiredArgsConstructor
@Slf4j
public class PackageEventHandler {

    private final ImageService imageService;

    @Async
    @EventListener
    public void process(PackageEventDTO packageEventDTO) {

        log.info("EventListener를 통해 Event가 비동기로 Driven 되었습니다.");

        imageService.saveImages(packageEventDTO);

    }

}
```

각 Service Layer간 비즈니스 로직에 의해서 강하게 서로 의존하고, 의존되는 상황을 피하기 위해 Event Handling을 통해 각 연결을 끊어보았습니다.

그리고 `@Async` 어노테이션을 통해 비동기로 해당 메서드가 호출 되게끔 하여 Block이 되는 일을 방지 하였습니다.

더 자세한 사항은 제가 [Event Handling](https://velog.io/@yardyard/Spring-EventListener-비동기)에 대해서 작성한 글을 참고 부탁드립니다.

<br />


### 2️⃣ 단위 TEST를 진행 하였습니다.

*PackageServiceTest.class*

```java
@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @Mock
    private ImageService imageService;

    @Mock
    private PackageJpaRepository packageJpaRepository;

    @Mock
    private ImageJpaRepositoryTest imageJpaRepositoryTest;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void get_package() {

        // given
        long fakeId = 1L;

        Package packageEntity = MockUtil.getPackage();

        List<ImageDTO.ResponseDTO> images = MockUtil.getImageDtoResponseDtos();

        // mocking
        given(packageJpaRepository.findById(any()))
                .willReturn(Optional.ofNullable(packageEntity));

        given(imageService.getImages(any()))
                .willReturn(images);

        // when
        PackageDTO.ResponseDTO dto = packageService.getPackage(fakeId);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getImages().size()).isEqualTo(3);
        assertThat(dto instanceof PackageDTO.ResponseDTO).isEqualTo(true);

    }

    @Test
    public void get_packages() {

        // given
        int offset = 0;

        int limit = 5;

        List<Package> packages = MockUtil.getPackages(offset, limit);

        // mocking
        given(packageJpaRepository.findPackagesWithOffsetAndLimit(any()))
                .willReturn(packages);

        // when
        PackageDTO.ResponseDTOs dto = packageService.getPackages(offset, limit);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getResponseDTOS().size()).isEqualTo(limit - offset);
        assertThat(dto instanceof PackageDTO.ResponseDTOs).isEqualTo(true);

    }
}

```

위 단위 테스트 동안 [제가 작성한 글](https://velog.io/@yardyard/Service-계층을-독립적으로-테스트를-해야-하는-이유)을 토대로 Service Layer를 독립적으로 테스팅 하기 위해 `Mock` 객체등을 통해 독립적인 환경을 구축하였습니다.

해당 테스트간 `JUnit`을 사용하였으며, `BDD` 패턴에 맞춰 `given-when-then` 패턴을 사용하였습니다.

<br />


### 3️⃣ 통합 TEST를 진행 하였습니다.

*PackageRestControllerTest.class*

```java

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PackageRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PackageRestController packageRestController;

    private static final String URL_PREFIX = "/api/v1";

    @BeforeEach
    public void set_up() {
        mvc = MockMvcBuilders.standaloneSetup(packageRestController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void save_test() throws Exception {

        // given
        List<ImageDTO.RequestDTO> requestImageDTOs = MockUtil.getImageDtoRequestDtos();

        PackageDTO.RequestDTO requestPackageDTO = new PackageDTO.RequestDTO(1L, requestImageDTOs);

        String requestBody = om.writeValueAsString(requestPackageDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post(URL_PREFIX + "/package")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    public void get_package() throws Exception {

        // given
        long fakeId = 1L;

        // when
        ResultActions resultActions = mvc.perform(
                get(URL_PREFIX + "/package/" + fakeId)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
        resultActions.andExpect(jsonPath("$.data.id").value(1));
        resultActions.andExpect(jsonPath("$.data.trackingNo").value(1));
        resultActions.andExpect(jsonPath("$.data.images[0].filename").value("파일 이름1"));
    }

    @Test
    public void get_packages() throws Exception {

        // given
        int fakeOffset = 0;

        int fakeLimit = 5;
        
        // when
        ResultActions resultActions = mvc.perform(
                get(URL_PREFIX + "/packages/" + fakeOffset + "/" + fakeLimit)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
        resultActions.andExpect(jsonPath("$.data.responseDTOS[0].id").value(5));
        resultActions.andExpect(jsonPath("$.data.responseDTOS[1].id").value(4));
    }
}

```

통합(Integration) 테스트를 통해 전 Layer들을 테스트 하였습니다.

통합 테스트간 Servlet을 Mocking 하여 테스트 속도등을 향상 시켰습니다.

단위 테스트와 마찬가지로 `BDD` 패턴에 맞춰 `given-when-then` 패턴을 사용하였습니다.

더 자세한 사항은 [제가 통합 테스트에 대해 작성한 글](https://velog.io/@yardyard/Spring-Integration-test-부제-근거-있는-자신감)을 참고해주시면 감사드리겠습니다.

<br />


### 4️⃣ 공통 Response를 사용 하였습니다.

*ApiResponse.class*

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static ApiResponse<?> createSuccessWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();

        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL_STATUS, errors, null);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> createError(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }

    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
```

공통 API Response를 사용함으로써, 해당 API를 사용하게 되는 개발자들이 통일 되지 못하는 API로 인해서 혼선을 겪거나 생산성이 떨어지는 문제를 방지 하고자 하였습니다.

또한 아래 Custom Exception과 더불어 예외 발생시에도 정해진 API 규격에 맞춰 Client에게 API를 Response 하고자 하였습니다.

<br />


### 5️⃣ Custom Exception을 사용 하였습니다.

*RestExceptionHandler.class*

```java
@RestControllerAdvice(basePackages = {"com.kfriday.kevin"})
public class RestExceptionHandler {

    @ExceptionHandler(DuplicatedPackageException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicatedUserException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
    }

}
```

Custom Exception 객체를 생성하여 `RuntimeExcption`으로 500 에러 만을 반환해 Client로 하여금 어떠한 Exception이 발생했는지를 알기 어려웠던 문제를 각 상황에 맞는 Exception을 반환 함으로써 해결 하고자 하였습니다.

<br />


### 6️⃣ Swagger를 사용한 API 문서화를 하였습니다.

<img width="1512" alt="스크린샷 2024-06-25 오후 12 26 02" src="https://github.com/DEVdongbaek/kfriday-project/assets/102592414/953f4d44-8666-434b-b2d0-94db5e7edfa8">

http://localhost:8080/swagger-ui/index.html#/

위 주소를 통해 Swagger를 통한 API 문서를 확인 하실 수 있습니다.

<br />


### 7️⃣ Entity의 공통된 필드들을 하나의 클래스로 추출하였습니다.

*Common.class*

```java
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class Common {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime updatedAt;

}
```

각 Entity 마다 공통적으로 들어가는 필드들을 공통 Entity 클래스로 분리해 이를 상속케 하였습니다.

이를 통해 각 Entity 클래스의 코드의 가독성을 향상 시켰습니다.

<br />


### 8️⃣ 정적 팩토리 메서드 사용으로 객체 생성을 캡슐화 하였습니다.

*Image.class*

```java
    public static Image of(Package packageEntity, ImageDTO.RequestDTO requestDTO){
        return Image.builder()
                .packageEntity(packageEntity)
                .filename(requestDTO.getFilename())
                .type(requestDTO.getType())
                .build();
    }
```

정적 팩토리 메서드를 사용한 이유에 대해서는 [제가 작성한 글](https://velog.io/@yardyard/내가-from-of-메서드로-네이밍-했던-이유)을 통해 더 자세히 확인 하실 수 있습니다.
