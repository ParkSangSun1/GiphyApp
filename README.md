# GiphyApp
### Use stack
- **Retrofit & Okhttp** : 네트워크 통신을 위해 사용하였습니다
- **Dagger hilt** : 의존성 주입을 위해 쉽고 불필요한 dagger 코드를 줄여주는 hilt를 사용하였습니다
- **Coroutines & Flow** : 네트워크 통신을 할 때 비동기 처리 등과 Paging3를 사용할 때 사용하였습니다
- **Paging3 & ViewPager2** : 효율적으로 데이터를 불러오기 위해 Paging3를 사용하였고 페이지가 2페이지였기 때문에 (머트리얼 디자인 표준 가이드에 item 수는 3~5입니다) Navigation bar보다는 ViewPager2를 선택하여 사용하였습니다
- **Glide** : 이미지를 빠르고 효율적으로 불러오기 위해 사용하였습니다
- **Room** : 로컬 DB에 좋아요 표시 GIF를 저장해 앱을 다시 시작해도 정보를 남기기 위해 사용하였습니다
- **MVVM** : 디자인 패턴 중 MVVM을 사용하여 Model과 View의 의존 관계를 분리하기 위해 사용하였습니다
