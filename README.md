# Study RxJava

- ReactiveX를 Java부터 시작해보자 (RxKotlin과 RxAndroid를 위해서)

## Reactive Programming

1. 리액티브 프로그래밍이란? 

   - 데이터의 흐름과 전달에 관한 프로그래밍 패러다임
   - 데이터 흐름을 먼저 정의하고 데이터가 변경되었을 대 연관되는 함수나 수식이 업데이트되는 방식.

   1. Java와 리액티브 프로그래밍 

      - pull 방식의 프로그래밍 개념을 push 방식으로 프로그래밍 개념으로 바꾼다. 

        - Push : 주제 객체가 구독 객체에게 상태를 보내는 방식.
        - Pull : 구독 객체가 주제 객체에게 상태를 요청해 받아오는 방식.

      - 함수형 프로그래밍의 지원을 받는다. 

        - 함수형 프로그래밍은 Side Effect(부수효과)가 없는 순수 함수를 지향해 스레드에 안전함 
          - Side Effect : 콜백이나 옵저버 패턴이 스레드에 안전하지 않은 이유는 같은 자원에 여러 스레드가 Race condition(경쟁조건)에 빠지게 되었을 때 알 수 없는 결과가 나오기 때문

      - RxJava의 시작 

        - 콜백 문제점, Future 조합 문제점, 동시성 문제점 해결을 위한 라이브러리
## Observable



## Reactive Operator



## Schelduer



## RxAndroid



## Testing & Flowable

