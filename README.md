으으.... 커밋만 계속 하다가 마지막에 깃헙에 푸시했는데 하필 학교 노트북 일반 자격증명에 다른 사람 깃헙 계정이 등록되어 있었네요...
rebase/ammend해도 커밋한 사람으로 기록 남고 그렇다고 그 동안 개발한 기록들 삭제하기 아까워서 그냥 올렸습니다...ㅠㅠ

# schedule_app
winter-coding schedule app

## 초기화면
1.3초 후 탭뷰 레이아웃으로 전환됩니다.<br>
![초기화면](http://postfiles3.naver.net/MjAxODExMDVfMTY1/MDAxNTQxMzUwMTQ2NTA3.fJCyVfYm9YRQpN08NHF885jdDNFaM5tVopGcBKbTN5cg.EXrRzm8jUawttIn4mA4URo-ejb5XdKcHP1vb3KURVoQg.PNG.dragon20002/Screenshot_20181105-013528.png?type=w580)

## Monthly 탭
위 아래로 스위핑하여 다른 달의 달력을 볼 수 있습니다.<br>
달력의 날짜(일)을 선택하면 작은 원이 선택된 날짜로 이동합니다.<br>
우측 하단에 플로팅 액션 버튼으로 새로운 일정을 추가할 수 있습니다. 새로운 일정의 기본값은 선택된 날짜가 들어갑니다.<br>
![월 화면](http://postfiles14.naver.net/MjAxODExMDVfNTYg/MDAxNTQxMzUwMTQ2NDAx.K1Be5jEu_mZpGmPXHIqx43goII6VGq_536z44fRX5sMg.NIzJto5WcvAr58meNO3Rh_BFlNbrYBcPqXxnhaVLckYg.PNG.dragon20002/Screenshot_20181105-013536.png?type=w580)

## Weekly 탭
조작법은 Monthly와 같고, 다른 점은 달력 하단에서 등록된 이번 주 일정 목록을 확인할 수 있습니다.
![주 화면](http://postfiles5.naver.net/MjAxODExMDVfMjA3/MDAxNTQxMzUwMTQ2MzAz.XzpRrg8jZWRY6D4iBWkZVg8ng0t-h_oaxW_HJ4oeFgYg.NwlVtr-KLaxQBkDqKtKTu1GbdOr7CgMYhM1eRZ3c6o0g.PNG.dragon20002/Screenshot_20181105-013539.png?type=w580)

## Daily 탭
![일 화면](http://postfiles13.naver.net/MjAxODExMDVfODAg/MDAxNTQxMzUwMTQ2MzE2.HnOKrYKVi4SGgfkESCzFbSvgvjGNK_2WqTGXjS-ZiLog.caVuMCQc8EHCqcHJJU4Oln4psCBjLhgVZ1Z3qvJ1Dewg.PNG.dragon20002/Screenshot_20181105-013545.png?type=w580)

## 새로운 일정 등록하기
아무 탭에서 우측 하단 플로팅 액션 버튼을 누르면 새로운 일정을 추가하는 액티비티가 나타납니다.<br>
내용을 한 자라도 입력해야만 일정을 저장할 수 있습니다. 날짜는 기본값으로 각 탭에서 선택된 날짜가 설정되어 있고 DatePicker뷰로 년/월/일을 수정할 수 있습니다.<br>
![일정 등록](http://postfiles5.naver.net/MjAxODExMDVfMTEg/MDAxNTQxMzUwMTQ2NDE2.Z5P7OMThh7P-raYsLuabriDueZJbl6z7tfnc-rZKJNYg.SXucOUV-TNcvG6A_DHR6Oz87rmWZnlQ0wqRn2QHjDgYg.PNG.dragon20002/Screenshot_20181105-013550.png?type=w580)

## 등록된 일정 확인
![월 등록된 일정 확인](http://postfiles16.naver.net/MjAxODExMDVfMjUg/MDAxNTQxMzUwMTQ2NDI5.G25jhx_5xdH0-nQhOBAjEihCKzGrso_qL9f9zA16b5Ig.X77-Qo9TtEt2T0gUoBa1O8GZv55dtvpfMauqCdU-jM0g.PNG.dragon20002/Screenshot_20181105-013629.png?type=w580)

![주 등록된 일정 확인](http://postfiles11.naver.net/MjAxODExMDVfNjQg/MDAxNTQxMzUwMTQ2NTQ5.SC9VO1sruEkxaGs4I03ml5NNIS7cGZKdDbWt3viqkIcg.NUXUY-jCj_Oc3xjXRqHAP1DDQW_s8ZdCYe9Xmlrofm4g.PNG.dragon20002/Screenshot_20181105-013651.png?type=w580)

![일 등록된 일정 확인](http://postfiles9.naver.net/MjAxODExMDVfMTky/MDAxNTQxMzUwMTQ2NTg4.zbS9UD9HkBphzfWp_NP1jAZHqngpIj38Lt7AaKNtpjog.8fptIln8PtNkT4S0yKaVZc0kPX9VqzGHUj-na4Y9oV4g.PNG.dragon20002/Screenshot_20181105-013656.png?type=w580)

## 등록된 일정 수정
등록된 일정은 Weekly, Daily 탭에서 리스트 항목을 클릭하여 내용 및 날짜를 수정하거나 삭제할 수 있습니다.<br>
![등록된 일정 수정](http://postfiles9.naver.net/MjAxODExMDVfNDgg/MDAxNTQxMzUwMTQ2Njcz.EDnHwPKXlT7bG-ewgpJDD5h-ASKIBD1tt1-ZiyJzakcg.tWHdKMB4oQCtIMGunEFwwtLNOdGVUXVyxkb0Lp9dJDEg.PNG.dragon20002/Screenshot_20181105-014344.png?type=w580)



