# ERP 시스템

*UI 구성
-로그인
-회원가입
-사용자 UI(Kiosk)와 관리자 UI(ERP System)


*프로젝트 컨셉

관리자 권한여부에 따라 UI구성이 달리되며 고객들이 접하는 Kiosk UI는 일반사용자 계정으로 접속하여 사용되게 하고
ERP System은 관리자 계정으로 접속하여 사용되게합니다.
 
 
 
 
 1.Kiosk
 
 일반사용자 계정으로 로그인하게되면 Kiosk화면이 구현되는데 첫 프레임은 매장식사와 테이크아웃의 여부를 결정짓는 화면이고
 테이크아웃의 기능은 구현하지 않았으니 참고바랍니다.
 이어서 매장식사를 클릭하면 주문가능한 화면이 출력되며 주문하고자 하는 제품을 클릭하면 우측의 장바구니 기능에 제품들이 담기며
 주문이 완료되면 ERP System에 상품 재고량을 비롯하여 주문이력 및 주문 상세이력이 업데이트 됩니다.
 
 
 
 
 2.ERP System
 
 관리자 계정으로 로그인하게되면 ERP System화면이 구현되며 Admin Ui의 기능들은 설명이 길어지므로 직접 사용해보시길 바랍니다.
 기능이 몇가지 구현되지 않았으므로 참고바랍니다.
