[![Android CI](https://github.com/jinhaEom/seoulFestival/actions/workflows/android.yml/badge.svg)](https://github.com/jinhaEom/seoulFestival/actions/workflows/android.yml)
# SeoulFestival 어플



## 1. 설명
 서울시 공연 정보 API를 활용하여 오페라, 뮤지컬, 연극 등 다양한 공연 정보를 제공하며, 사용자가 선호하는 장르에 맞춘 추천 공연 제시. 공연 장소를 지도에서 확인하고 관련 뉴스를 읽을 수 있는 기능을 갖춘 앱


- 제작 목적
    -
    - Rest Api 활용 연습과 UI 구성 요소 공부를 위해 
    - 여러가지 기능들을 구현해보면서 앱의 기능을 추가할 예정

- 제작 기간
    - 
    - 2024. 04.04 ~ 2024.05.23 (앱의 전체적인 틀과 기능 구현완료)
    - 2024.05 이후~ (써보고싶거나 공부해보고싶은 기술들을 앱에 적용시킬 예정)

- 사용된 기술
    -
    -  사용된 언어 : Kotlin
    - 디자인 패턴 : MVVM 
    - UI/UX: ViewModel, LiveData, DataBinding, Navigation Component, Glide, SharedPreferences
    - 통신 및 API : Retrofit(Rest API) , 서울시 공연정보 API(공공API), 네이버 지도 API, 네이버 검색 API
  

- 어플 Flow
    -
    - Splash 화면 -> 추천받을 공연 장르 선택 화면 -> 메인화면(하단 메뉴구성)

    - Menu 구성
        - 홈화면
        - 검색화면
        - 뉴스 화면




### 2. 화면이미지 및 설명



1. Splash 화면
    -
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/786e2bb5-7780-4d48-a85f-ff9177f85304" width="200" height="400"/>

2. 추천받을 공연 장르 선택 화면
    -
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/6c1d1933-5ed0-45b3-a33f-607e9245f602" width="200" height=400/>
    </br>
    
    - 5가지 메뉴 중 사용자가 선택한 장르의 공연을 '홈화면'에서 추천하여 보여준다.
    - 홈화면의 '추천 공연장르 바꾸기' Text를 누르면 현재 화면으로 다시 돌아와 다른 장르를 선택할 수 있다.


3. 홈화면 (첫화면)
    -
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/fa700f8d-b42e-4596-ab2a-5fb85b43d030" width="200" height="400"/>
     <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/65d54c9f-df90-4484-bfda-44ddb7e97234" width="200" height="400"/>

    - 상단의 큰 이미지는 사용자가 추천받을 장르의 공연이고 총 4개의 공연을 슬라이드해서 볼 수있도록 기능을 제공한다.
    - 추천공연장르 바꾸기를 누르면 '추천받을 공연 장르 선택 화면' 으로 이동한다.
    - 아래 5가지 메뉴를 들어가면 서울시에서 공연하는 해당 장르의 리스트를 볼 수 있다.

    - (메뉴 클릭시 화면)

        <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/02dbc321-e892-4aaa-9ed6-173caab2fb34" width="200" height="400"/>
        <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/b5d60357-0846-4535-8ee3-640169a5ab09" width="200" height="400"/>
        
        - 사용자에게 공연 리스트를 제공하고 리스트 클릭 시 자세한 정보를 제공한다.
        - 링크를 누르면 티켓을 예매하는 화면으로 넘어간다. (실제 티켓예매페이지로)

4. 검색화면
    -
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/e67c6667-fefe-4117-af4e-7745f9d65c1e" width="200" height="400"/>
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/08813b4f-0c3c-4481-9636-b81a617c2a2b" width="200" height="400"/>
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/80608213-5384-4e71-b902-ad1d31af1908" width="200" height="400"/>
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/a5f93d04-ace1-43ec-86cd-eedce03e965b" width="200" height="400"/>

    - Naver 지도 API 이용
    - 공연이 있는 지역에 마커로 나타내서 지도에 보여준다.
    - 해당 지역구에 공연이 없다면 공연이 없다는 Toast메시지를 출력한다.
    - 마커를 클릭하면 해당 지역에 공연 정보를 나타내서 보여준다.
        - 같은장소에 여러 공연이 있을시 스크롤하여 여러 공연정보들을 볼 수 있다.

5. 뉴스 화면
    -
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/dd5d8ab9-608a-4402-9863-1f612a124bb8" width="200" height="400"/>
    <img src="https://github.com/jinhaEom/seoulFestival/assets/84216838/5f0a37e8-9d60-4dad-806f-a7a317d8af96" width="200" height="400"/>
    
    - 네이버 검색 API 를 활용하여 나타낸 News 화면
    - 검색어를 최대한 공연정보와 맞는 뉴스를 나타내기 위해 연관된 검색어 list를 구현해서 나타내었음.
    - 뉴스 내용 화면은 WebView로 구현






