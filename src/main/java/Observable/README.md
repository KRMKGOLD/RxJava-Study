# Observable.Observable

1. Observalbe 클래스

   - Observer 패턴을 구현 

     1. 객체의 상태 변화를 관찰하는 관찰자(Observer) 목록을 객체로 등록
     2. 상태 변화가 있을 때마다 메소드를 호출하여 객체가 직접 목록의 각 Observer에게 변화를 알려준다.

     - onNext : Observable이 데이터의 발행을 알림
     - onComplete : 모든 데이터의 발행을 완료함을 알림, 한 번만 발생, onComplete 이후에는 onNext가 발생해서는 안 된다.
     - onError : Observable에서 에러가 발생함을 알림. onError 이후 onNext와 onComplete 이벤트가 발생하지 않는다. = Observable의 실행 종료

   1. just() : 인자로 넣은 데이터를 차례로 발행하기 위해서 Observable를 생성

      ```java
       Observable.Observable.just(1, 2, 3, 4, 5).subscribe(System.out::println);
       // 실제 데이터의 발행은 subscribe() 함수 호출
      ```

   2. subscribe() / Disposable

      - subscribe() : 내가 동작시키기 원하는 것을 사전에 정의한 후 다음 실제로 그것이 실행되는 시점을 정의해주는 함수.
      - dispose() : Observable에게 더 이상 데이터를 발행하지 않게 하는 함수, onComplete()가 정상적으로 실행되었으면 실행하지 않아도 됨 ( onComplete()는 자동적으로 dispose()를 호출하기 때문)
      - isDisposed() : Observable이 더 이상 데이터를 발행하지 않는지 (구독을 해제하였는지) 확인하는 함수.

   3. create() : just() 함수와 다르게 onNext, onComplete, onError를 개발자가 직접 선언해줘야 함

      ```java
       Observable.Observable<Integer> observable = Observable.Observable.create(
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

   4. fromArray() : 배열에 들어있는 데이터를 처리하기 위한 함수

      - RxJava 1에서 from()을 이용해 여러가지 계산에 사용해 모호함이 있었다

      - RxJava 2에서는 from()을 세분화해서 사용

        ```java
        Integer[] intArray = {400, 500, 600, 700}; Observable.Observable.fromArray(intArray).subscribe(System.out::println);
        ```

   5. fromIterable() : Iterator 인터페이스를 구현한 클래스에서 선언

      - Iterator 인터페이스는 이터레이터 패턴을 구현한 것으로 다음에 어떤 데이터가 있는지와 그 값을 얻어오는 것만 관여할 뿐 특정 데이터 타입에 의존하지 않는 장점 존재

        ```java
        public interface Iterator<E> {
            boolean hasNext(); E next();
        }
        ```

        

      - Iterable<E> 인터페이스를 구현하는 대표적인 클래스

        1. ArrayList (List Interface)
        2. BlockingQueue (BlockingQueue Interface)
        3. HashSet (Set Interface)
        4. LinkedList
        5. Stack
        6. TreeSet
        7. Vector ...

        ```java
        List<String> names = new ArrayList<>(); names.add("Jerry"); names.add("William"); names.add("Bob");
        
        Observable.Observable<String> source = Observable.Observable.fromIterable(names); source.subscribe(System.out::println);
        ```

      - HastSet, BlockingQueue로도 같은 방식으로 만들 수 있음

   6. fromCallable() : Java에서 제공하는 Callable<V> 비동기 인터페이스와의 연동할 때 사용하는 함수

      ```java
       Callable<String> callable = () -> {
       	Thread.sleep(1000);
       	return "Hello Callable";
       }
       
       Observable.Observable<String> source = Observable.Observable.fromCallable(callable);
       source.subscribe(System.out::println);
       
       // use lambda
      ```

   7. fromFuture() : Java에서 제공하는 Future 동시성 API 인터페이스와의 연동

      - Future : 비동기 계산의 결과를 구할 때 사용함

        ```java
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> { Thread.sleep(1000); return "Hello Future"; });
        
        Observalbe<String> source = Observable.Observable.fromFuture(future); source.subscribe(System.out::println);
        ```

      - Executors 클래스의 newSingleThreadExecutor() 메서드에 람다 표현식의 Callable 객체를 인자로 넣었다.

   8. fromPubilsher() : Java에서 제공하는 Publisher Flow API의 일부

      ```java
       Publisher<String> publisher = (Subscriber<? super String> s) -> {
       	s.onNext("Hello Observable.Observable.fromPublisher()");
       	s.onComplete();
       };
       
       Observable.Observable<String> source = Observable.Observable.fromPublisher(publisher);
       source.subscribe(System.out::println);
      ```

2. Single 클래스 : Observable의 특수한 형태로, 오직 한 개의 데이터만 발행할 수 있게 한정.

   - 데이터 하나가 발행되는 순간 종료된다 (onSuccess). 
     - onSuccess() = onNext() + onComplete()
     - Single LifeCycle : onSuccess(T value) , onError()

   1. just() : Observable과 비슷한 방법.

      ```java
       Single<String> source = Single.just("Hello Single!");
       source.subscribe(System.out::println);
      ```

   2. Observable과 Single

      - Single은 Observable의 특수한 형태이므로 Observable를 Single로 변환시킬 수 있다.

        ```java
        Observable.Observable<String> source = Observable.Observable.just("Hello Single");
        Single.fromObservable(source).subscribe(System.out::println);
        
        Observable.Observable.just("Hello Single").single("default item")
        	.subscribe(System.out::println);
        
        Single.fromObservable;
        Observable.Observable.just().single;
        Observable.Observable.fromArray().first;
        Observable.Observable.empty().single;
        ```

   3. Maybe 클래스 : Single 클래스와 마찬가지로 데이터 하나만 가진다

      - 데이터 발행 없이 바로 데이터 발생을 완료할 수도 있다.
      - Maybe = Single + onComplete()
      - elementAt(), firstElement(), flatMapMaybe(), lastElement(), reduce(), singleElement() ...
      - 자세한 내용은 Reactive Operator에서.

   4. Hot Observables / Cold Observables

      - Observable에는 뜨거운 Observable과 차가운 Observable이 있다.
      - 차가운 Observable.Observable : Observer가 .subscribe()를 호출해 구독하지 않으면 데이터를 발행하지 않는, lazy 접근법에 해당하는 Observable.Observable. 
        - 웹 요청, DB 쿼리, 파일 읽기 ...
      - 뜨거운 Observable.Observable : 구독자에 신경쓰지 않고 데이터를 발행하는 Observable.Observable 
        - 여러 구독자를 고려할 수 있음.
        - 단 구독자는 Observable에서 발행하는 내용이 처음부터 모두 수신되었는지 확신할 수 없음.
        - 마우스 이벤트, 키보드 이벤트, 시싀템 이벤트, 센서 데이터, 주식 가격..

   5. Subject 클래스 : 차가운 Observable를 뜨거운 Observable로 바꾸어주는 클래스.

      - Observable의 속성과 구독자의 속성을 모두 가지고 있음. 즉 데이터를 발행할 수도 있고 발행된 데이터를 바로 처리할 수도 있음.

      1. AsyncSubject : Observable에서 발행한 마지막 데이터를 불러올 수 있고, 이전 데이터는 무시한다.

         ```
          subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
                  subject.onNext("A");
                  subject.onNext("B");
          subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
                  subject.onNext("C");
                  subject.onComplete();
          
          출력결과
          Subscriber #1 -> C
          Subscriber #2 -> C
         ```

         - onComplete됨과 동시에 마지막 데이터를 발행하고 종료한다.

         - 다른 방식 1

           ```java
           Float[] temperature = {10.1f, 13.4f, 12.5f};
           Observable.Observable<Float> source = Observable.Observable.fromArray(temperature);
           
           AsyncSubject<Float> subject = AsyncSubject.create();
           subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
           
           source.subscribe(subject);
           
           출력 결과
           Subscriber #1 => 12.5f
           ```

         - 가능한 이유

           ```java
           import Observable.Observable;public abstract class Subject<T> extends Observable<T> implements Observer<T>
           ```

         - Subject가 Observable과 Observer를 둘 다 상속받고 있기 때문에 가능하다.

         - 다른 방식 2

           ```java
           AsyncSubject<Integer> subject = AsyncSubject.create();
           subject.onNext(10);
           subject.onNext(11);
           subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
           subject.onNext(12);
           subject.onComplete();
           subject.onNext(13); // 무시당함.
           subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
           subject.subscribe(data -> System.out.println("Subscriber #3 => " + data));
           
           출력 결과
           Subscriber #1 => 12
           Subscriber #2 => 12
           Subscriber #3 => 12
           ```

      2. BehaviorSubject 클래스 : 구독 시 가장 최근 값 혹은 기본값을 넘겨주는 클래스.

         ex ) 온도계에서 가장 최근 온도 값을 받아오는 역할을 할 수 있음.

         ```
          BehaviorSubject<Integer> subject = BehaviorSubject.createDefault(6);
          subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
          subject.onNext(1);
          subject.onNext(3);
          subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
          subject.onNext(5);
          subject.onComplete();
          
          출력 결과
          Subscriber #1 => 6
          Subscriber #1 => 1
          Subscriber #1 => 3
          Subscriber #2 => 3
          Subscriber #1 => 5
          Subscriber #2 => 5
         ```

         - 1번째 줄 : 그 전에 발행된 데이터가 없으니 디폴드값 6을 전달받는다
         - 4번째 줄 : subscribe() 전에 발행된 3을 전달받음.

      3. PublishSubject 클래스 : 가장 평범한 Subject 클래스. 오직 해당 시간에 발행된 데이터만 발행해준다.

         ```
          PublishSubject<String> subject = PublishSubject.create();
          subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
          subject.onNext("1");
          subject.onNext("3");
          subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
          subject.onNext("5");
          subject.onComplete();
          
          출력 결과
          Subscriber #1 => 1
          Subscriber #1 => 3
          Subscriber #1 => 5
          Subscriber #2 => 5
         ```

         - 첫 번째 구독자가 subscirbe() 후 1, 3을 발행하고, 두 번째 구독자가 subscribe() 후 5를 발행한다 - 첫 번째 구독자 1, 3, 5, 두 번째 구독자 5

      4. ReplaySubject 클래스 : 구독자가 새로 생기면 항상 데이터의 처음부터 끝까지 다시 발행한다.

         ```
          ReplaySubject<String> subject = ReplaySubject.create();
          subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
          subject.onNext("1");
          subject.onNext("3");
          subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
          subject.onNext("5");
          subject.onComplete();
         ```

         - 모든 데이터를 저장한다는 것은 데이터 누수의 가능성이 있으니 주의하면서 사용해야 한다.
         - Subject는 뜨거운 Observable를 활용하는 것인데 차가운 Observable처럼 동작하므로 주의해야 한다. - [[Hot Observable과 Colde Observable의 차이](http://minsone.github.io/programming/reactive-swift-hot-and-cold-observables)]

   6. ConnectableObservable : Subject와 마찬가지로 차가운 Observable를 뜨거운 Observable로 바꾸는 클래스

      - 데이터 하나를 여러 구독자에게 공유할 때 사용

      - not only subscribe() → with connect()

      - connect() : 호출한 시점으로부터 subscribe()를 호출한 구독자에게 데이터를 발행

        ```java
        String[] dt = {"1", "3", "5"};
        Observable.Observable<String> balls = Observable.Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);
        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        source.connect();
        
        CommonUtil.sleep(250);
        source.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        CommonUtil.sleep(100);
        
        출력 결과
        Subscriber #1 => 1
        Subscriber #2 => 1
        Subscriber #1 => 3
        Subscriber #2 => 3
        Subscriber #1 => 5
        Subscriber #2 => 5
        Subscriber #3 => 5
        ```

      - 2번째 줄 : String[] dt를 100ms(100L, TimeUnit.MILLISECONDS) 단위로 0부터 데이터를 발행한다.

      - 4번째 줄 : dt[0]부터 접근해서 발행한다.

      - 9번째 줄 : 구독자 2개가 추가된 후 connect() 함수로 데이터 발생을 시작함

      - 11번째 줄 : 250ms를 기다린 후 3번째 구독자를 추가함, 이 때 바로 다음 데이터를 발행한다.

      - 출력 결과, 3번째 구독자는 2.5초 후부터 바로 발행된 데이터를 받을 수 있으므로, 3초 후에 발행되는 5를 출력할 수 있음.