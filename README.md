# woowahan-batch-service

본 프로젝트는 매일 새벽 1시에 Scheduler 가 동작되어 유저의 Score 와 Rank 를 갱신합니다.
<br/><br/>

<프로젝트 실행 방법><br/>
해당 프로젝트는 MySQL 기반 프로젝트입니다. 로컬 PC에 MySQL 환경설정 셋팅이 필요합니다.

1. 본 배치 서비스를 실행 시키기 위하여 우선적으로 woowahan-board-service repository 의 README.md 를 참고하여 Table 생성이 필요합니다.
2. /resources/application.yml 파일의 datasource.url, datasource.username, datasource.password 값을 로컬 환경의 MySQL 설정 값으로 수정하여야 합니다.
3. 본 프로젝트에서 파일 검색으로 'schema-mysql.sql' 파일(spring-batch-core 폴더에 존재)을 탐색하여 Spring Batch 의 META TABLE 들을 생성합니다.
4. ./gradlew build 을 실행합니다.
5. java -jar build/libs/woowahan-batch-service-0.0.1-SNAPSHOT.jar version=1
6. 배치 실행되면 배치 인스턴스가 Spring Batch META 테이블에 기록되어 같은 파라미터로는 재실행이 불가능합니다. 한번 이상 배치를 수행하고자 할때는 Spring Batch META 테이블 데이터를 삭제하거나 version 값을 1씩 증가시켜 실행합니다.