## 🌏 Table_Reservation_Service
- 식당이나 점포를 이용하기 전에 미리 예약을 할 수 있는 서비스를 제공합니다.
- 사용자는 매장을 검색하고 예약할 수 있으며, 매장 관리자는 매장 정보를 관리할 수 있습니다.

## ⚙ 기술 스택
- Language : `Java 17`
- Framework : `Spring Boot 3.3.4`
- Build : `Gradle`
- DB : `MariaDB`
- ORM : `JPA (Java Persistence API)`
- Server : `Embedded Tomcat`
- Library : `Lombok`
- Security : `Spring Security`
- IDE : `Eclipse`

## 📚 기능 사항
### 1. 회원가입 및 로그인 (점장, 사용자)
- 회원가입 : 사용자는 아이디, 비밀번호 등의 정보를 입력하여 계정을 생성할 수 있습니다. 점장과 사용자는 별도의 권한을 가지고 가입합니다.
- 로그인 : 생성된 계정을 사용하여 시스템에 로그인할 수 있으며, JWT(JSON Web Token)를 사용한 인증 방식으로 보안을 강화합니다.

### 2. 매장 등록, 수정, 삭제 (파트너 전용)
- 매장 등록 : 점장은 자신의 매장을 시스템에 등록할 수 있습니다. 매장의 이름, 주소, 연락처 등의 정보를 입력합니다.
- 매장 수정 : 등록된 매장의 정보를 수정할 수 있으며, 매장의 소유자만 이 작업을 수행할 수 있습니다.
- 매장 삭제 : 점장은 더 이상 운영하지 않는 매장을 시스템에서 삭제할 수 있습니다. 이때 해당 매장에 연관된 모든 예약 정보도 함께 삭제됩니다.

### 3. 매장 검색 및 상세 조회
- 사용자는 매장 이름을 기준으로 매장을 검색할 수 있으며, 검색 결과에서 매장의 상세 정보를 조회할 수 있습니다.
- 매장 정보에는 주소, 전화번호, 예약 내역 등이 포함됩니다.

### 4. 예약 생성 및 관리
- 예약 생성 : 사용자는 선택한 매장에 대해 예약을 생성할 수 있습니다.
- 예약 관리 : 사용자는 자신의 예약 내역을 조회하고, 필요 시 예약을 수정하거나 취소할 수 있습니다.

### 5. 키오스크를 통한 예약 방문 확인
- 사용자는 매장을 방문했을 때, 키오스크를 통해 예약 정보를 확인하고, 도착을 알릴 수 있습니다. 이를 통해 매장 관리자는 예약 현황을 실시간으로 파악할 수 있습니다.

### 6. 리뷰 작성, 수정, 삭제, 조회
- 사용자는 매장 방문 후 경험에 대한 리뷰를 작성할 수 있으며, 다른 사용자의 리뷰도 조회할 수 있습니다.
- 작성한 리뷰는 수정 및 삭제할 수 있으며, 리뷰의 내용은 매장에 대한 사용자들의 인식을 높이는 데 기여합니다.

## 📌 ERD
<img src="https://github.com/user-attachments/assets/4d31eba6-c786-44fe-9b1f-9b5cae00b1f1" width="800" height="550"/>
