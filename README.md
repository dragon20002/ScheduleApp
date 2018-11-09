으으.... 커밋만 계속 하다가 마지막에 깃헙에 푸시했는데 하필 학교 노트북 일반 자격증명에 다른 사람 깃헙 계정이 등록되어 있었네요...
rebase/ammend해도 커밋한 사람으로 기록 남고 그렇다고 그 동안 개발한 기록들 삭제하기 아까워서 그냥 올렸습니다...ㅠㅠ

# schedule_app
winter-coding schedule app

## 초기화면
1.3초 후 탭뷰 레이아웃으로 전환됩니다.<br>
![초기화면](http://postfiles7.naver.net/MjAxODExMDlfMjY1/MDAxNTQxNzQwNjE1MzY2.xsnMe5SdBzNBgfJH6G1kz4HpKPyq7zpdTUr7UROe96Ag.7_j-5o4U9NaCz3yZCafa9XP51yjxyM9lW0bNv_-vj0wg.PNG.dragon20002/image_5035105451541740482332.png?type=w580)

## Monthly 탭
위 아래로 스위핑하여 다른 달의 달력을 볼 수 있습니다.<br>
달력의 날짜(일)을 선택하면 작은 원이 선택된 날짜로 이동합니다.<br>
우측 하단에 플로팅 액션 버튼으로 새로운 일정을 추가할 수 있습니다. 새로운 일정의 기본값은 선택된 날짜가 들어갑니다.<br>
![월 화면](http://postfiles6.naver.net/MjAxODExMDlfMTk3/MDAxNTQxNzQwNjE1OTcz.lMaWma4ySYQ1djRYSdYqJtB2C4yh56BuVyYiPW8zZ_cg._ZJmGifXYf552efFRC24GFSY63-UeBCMvODpIFRNFx8g.PNG.dragon20002/image_8013112311541740482332.png?type=w580)

## Weekly 탭
조작법은 Monthly와 같고, 다른 점은 달력 하단에서 등록된 이번 주 일정 목록을 확인할 수 있습니다.
![주 화면](http://postfiles3.naver.net/MjAxODExMDlfMTky/MDAxNTQxNzQwNjE2Mzk5._n5hNun5FvCSIl0C7KbGPo-lMyeO1cJM4VFNmoTv-MEg.v3ZbomcwlrzsukJACUgak6p8fdC6yAxgy-gYQyjRLyUg.PNG.dragon20002/image_5448224831541740482332.png?type=w580)

## Daily 탭
![일 화면](http://postfiles15.naver.net/MjAxODExMDlfMTUz/MDAxNTQxNzQwNjE2OTE5.2b8VQlDxAeKrI22XfzvCZCp7ty3XMOl5dUOpfYjYUnIg.8cKmfYIiVtfbJz_6kEHLS_LJZHwrB6BaWPDqtmGkjCwg.PNG.dragon20002/image_5435113051541740482332.png?type=w580)

## 새로운 일정 등록하기
아무 탭에서 우측 하단 플로팅 액션 버튼을 누르면 새로운 일정을 추가하는 액티비티가 나타납니다.<br>
내용을 한 자라도 입력해야만 일정을 저장할 수 있습니다. 날짜는 기본값으로 각 탭에서 선택된 날짜가 설정되어 있고 DatePicker뷰로 년/월/일을 수정할 수 있습니다.<br>
![일정 등록](http://postfiles7.naver.net/MjAxODExMDlfOTQg/MDAxNTQxNzQwNjE3NDE1.W4eFyqTtbF6XJVUZVzq4uYMnAJhLs8Dx-2RI10spy8Ag.2S8SkZo1vTYqORM3mxgkvwXkzKqyDyCXbiC4B7koQZ8g.PNG.dragon20002/image_6220454431541740482333.png?type=w580)

## 등록된 일정 확인
![월 등록된 일정 확인](http://postfiles3.naver.net/MjAxODExMDlfMjgg/MDAxNTQxNzQwNjE4MDc4.1_rQO4wMlJdAz48AmF2darUNtLKMfOXKg4h9CvKOUTog.T4G6ibaS5hezYkEDaQnZuDszygxVNJkhVMaifEeArIQg.PNG.dragon20002/image_368234901541740482333.png?type=w580)

![주 등록된 일정 확인](http://postfiles6.naver.net/MjAxODExMDlfMjQ4/MDAxNTQxNzQwNjE4NjA2.NpJKSffr9BLnHvNS10mjJVUdQ6c31ZRJkvLqcHGv3HIg.L5jpmiUZ08fBT1IO6VC-gI2YgxIuhWaKeX-9ftcTBzAg.PNG.dragon20002/image_6235795871541740482333.png?type=w580)

![일 등록된 일정 확인](http://postfiles9.naver.net/MjAxODExMDlfMTQ4/MDAxNTQxNzQwNjE5MTU0.dBLfJv4NeiJDaBBd8C2qpxvMwMqUW0CfFBgZGx5xD2gg.iUtXbpUiANP2H6n-_rtnTRqEyCaaiEcYbKpYZXhnhwgg.PNG.dragon20002/image_5578434491541740482333.png?type=w580)

## 등록된 일정 수정
등록된 일정은 Weekly, Daily 탭에서 리스트 항목을 클릭하여 내용 및 날짜를 수정하거나 삭제할 수 있습니다.<br>
![등록된 일정 수정](http://postfiles1.naver.net/MjAxODExMDlfMTAy/MDAxNTQxNzQwNjE5NjA0.NbxTTdQr9k6xSk0UKwuZ_cRIIUuk29aiigdgrK-sl4cg.qt2ppzmb6Zqg6UzFiLkVUqFw9TGQxIVaGoosfuleDY8g.PNG.dragon20002/image_7444942971541740482333.png?type=w580)



