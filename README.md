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

1. Observalbe 클래스 

   - Observer 패턴을 구현 

     1. 객체의 상태 변화를 관찰하는 관찰자(Observer) 목록을 객체로 등록
     2. 상태 변화가 있을 때마다 메소드를 호출하여 객체가 직접 목록의 각 Observer에게 변화를 알려준다.

     - onNext : Observable이 데이터의 발행을 알림
     - onComplete : 모든 데이터의 발행을 완료함을 알림, 한 번만 발생, onComplete 이후에는 onNext가 발생해서는 안 된다.
     - onError : Observable에서 에러가 발생함을 알림. onError 이후 onNext와 onComplete 이벤트가 발생하지 않는다. = Observable의 실행 종료

   1. just() : 인자로 넣은 데이터를 차례로 발행하기 위해서 Observable를 생성

      ```kotlin
       Observable.just(1, 2, 3, 4, 5).subscribe(System.out::println);
       // 실제 데이터의 발행은 subscribe() 함수 호출
      ```

   2. - subscribe() / Disposable

        - subscribe() : 내가 동작시키기 원하는 것을 사전에 정의한 후 다음 실제로 그것이 실행되는 시점을 정의해주는 함수.
        - dispose() : Observable에게 더 이상 데이터를 발행하지 않게 하는 함수, onComplete()가 정상적으로 실행되었으면 실행하지 않아도 됨 ( onComplete()는 자동적으로 dispose()를 호출하기 때문)
        - isDisposed() : Observable이 더 이상 데이터를 발행하지 않는지 (구독을 해제하였는지) 확인하는 함수.

      - create() : just() 함수와 다르게 onNext, onComplete, onError를 개발자가 직접 선언해줘야 함

        ```
         Observable<Integer> observable = Observable.create(
                         (ObservableEmitter<Integer> emitter) -> {
                             emitter.onNext(100);
                             emitter.onNext(200);
                             emitter.onNext(300);
                             emitter.onComplete();
                         });
         
                 observable.subscribe(System.out::println);
         
         observable.subscribe(data -> System.out.println("Result : " + data));
        ```

        - observable 변수는 차가운 Observable이다 : 실제 데이터는 observable.subscribe를 통해서 출력하게 된다.
        - 마지막 줄은 subscribe() 함수를 변형해 람다 표현식으로 이용해서 표현
        - create()는 RxJava에 숙련된 사람만이 사용하는 것이 좋다고 권장하고 있음 
          - create()는 여러가지 사항을 고려해서 사용해야 하기 때문.

## Reactive Operator



## Schelduer



## RxAndroid



## Testing & Flowable

