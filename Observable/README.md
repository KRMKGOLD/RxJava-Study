# Observable

1. Observalbe 클래스 

   - Observer 패턴을 구현 

     1. 객체의 상태 변화를 관찰하는 관찰자(Observer) 목록을 객체로 등록
     2. 상태 변화가 있을 때마다 메소드를 호출하여 객체가 직접 목록의 각 Observer에게 변화를 알려준다.

     - onNext : Observable이 데이터의 발행을 알림
     - onComplete : 모든 데이터의 발행을 완료함을 알림, 한 번만 발생, onComplete 이후에는 onNext가 발생해서는 안 된다.
     - onError : Observable에서 에러가 발생함을 알림. onError 이후 onNext와 onComplete 이벤트가 발생하지 않는다. = Observable의 실행 종료

   1. just() : 인자로 넣은 데이터를 차례로 발행하기 위해서 Observable를 생성

      ```
       Observable.just(1, 2, 3, 4, 5).subscribe(System.out::println);
       // 실제 데이터의 발행은 subscribe() 함수 호출
      ```

   2. subscribe() / Disposable

      - subscribe() : 내가 동작시키기 원하는 것을 사전에 정의한 후 다음 실제로 그것이 실행되는 시점을 정의해주는 함수.
      - dispose() : Observable에게 더 이상 데이터를 발행하지 않게 하는 함수, onComplete()가 정상적으로 실행되었으면 실행하지 않아도 됨 ( onComplete()는 자동적으로 dispose()를 호출하기 때문)
      - isDisposed() : Observable이 더 이상 데이터를 발행하지 않는지 (구독을 해제하였는지) 확인하는 함수.

   3. create() : just() 함수와 다르게 onNext, onComplete, onError를 개발자가 직접 선언해줘야 함

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

   4. fromArray() : 배열에 들어있는 데이터를 처리하기 위한 함수

      - RxJava 1에서 from()을 이용해 여러가지 계산에 사용해 모호함이 있었다

      - RxJava 2에서는 from()을 세분화해서 사용

        Integer[] intArray = {400, 500, 600, 700}; Observable.fromArray(intArray).subscribe(System.out::println);

   5. fromIterable() : Iterator 인터페이스를 구현한 클래스에서 선언

      - Iterator 인터페이스는 이터레이터 패턴을 구현한 것으로 다음에 어떤 데이터가 있는지와 그 값을 얻어오는 것만 관여할 뿐 특정 데이터 타입에 의존하지 않는 장점 존재

        public interface Iterator<E> { boolean hasNext(); E next(); }

      - Iterable<E> 인터페이스를 구현하는 대표적인 클래스

        1. ArrayList (List Interface)
        2. BlockingQueue (BlockingQueue Interface)
        3. HashSet (Set Interface)
        4. LinkedList
        5. Stack
        6. TreeSet
        7. Vector ...

        List<String> names = new ArrayList<>(); names.add("Jerry"); names.add("William"); names.add("Bob");

        Observable<String> source = Observable.fromIterable(names); source.subscribe(System.out::println);

      - HastSet, BlockingQueue로도 같은 방식으로 만들 수 있음

   6. fromCallable() : Java에서 제공하는 Callable<V> 비동기 인터페이스와의 연동할 때 사용하는 함수

      ```
       Callable<String> callable = () -> {
       	Thread.sleep(1000);
       	return "Hello Callable";
       }
       
       Observable<String> source = Observable.fromCallable(callable);
       source.subscribe(System.out::println);
       
       // use lambda
      ```

   7. fromFuture() : Java에서 제공하는 Future 동시성 API 인터페이스와의 연동

      - Future : 비동기 계산의 결과를 구할 때 사용함

        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> { Thread.sleep(1000); return "Hello Future"; });

        Observalbe<String> source = Observable.fromFuture(future); source.subscribe(System.out::println);

      - Executors 클래스의 newSingleThreadExecutor() 메서드에 람다 표현식의 Callable 객체를 인자로 넣었다.

   8. fromPubilsher() : Java에서 제공하는 Publisher Flow API의 일부

      ```
       Publisher<String> publisher = (Subscriber<? super String> s) -> {
       	s.onNext("Hello Observable.fromPublisher()");
       	s.onComplete();
       };
       
       Observable<String> source = Observable.fromPublisher(publisher);
       source.subscribe(System.out::println);
      ```

2. Single 클래스 : Observable의 특수한 형태로, 오직 한 개의 데이터만 발행할 수 있게 한정.

   - 데이터 하나가 발행되는 순간 종료된다 (onSuccess). 
     - onSuccess() = onNext() + onComplete()
     - Single LifeCycle : onSuccess(T value) , onError()

   1. just() : Observable과 비슷한 방법.

      ```
       Single<String> source = Single.just("Hello Single!");
       source.subscribe(System.out::println);
      ```

   2. Observable과 Single

      - Single은 Observable의 특수한 형태이므로 Observable를 Single로 변환시킬 수 있다.

        Observable<String> source = Observable.just("Hello Single"); Single.fromObservable(source).subscribe(System.out::println);

        Observable.just("Hello Single").single("default item") .subscribe(System.out::println);

        Single.fromObservable; Observable.just().single; Observable.fromArray().first; Observable.empty().single;

3. Maybe 클래스 : Single 클래스와 마찬가지로 데이터 하나만 가진다

   - 데이터 발행 없이 바로 데이터 발생을 완료할 수도 있다.
   - Maybe = Single + onComplete()
   - elementAt(), firstElement(), flatMapMaybe(), lastElement(), reduce(), singleElement() ...
   - 자세한 내용은 Reactive Operator에서.

4. Hot Observables / Cold Observables

   - Observable에는 뜨거운 Observable과 차가운 Observable이 있다.
   - 차가운 Observable : Observer가 .subscribe()를 호출해 구독하지 않으면 데이터를 발행하지 않는, lazy 접근법에 해당하는 Observable. 
     - 웹 요청, DB 쿼리, 파일 읽기 ...
   - 뜨거운 Observable : 구독자에 신경쓰지 않고 데이터를 발행하는 Observable 
     - 여러 구독자를 고려할 수 있음.
     - 단 구독자는 Observable에서 발행하는 내용이 처음부터 모두 수신되었는지 확신할 수 없음.
     - 마우스 이벤트, 키보드 이벤트, 시싀템 이벤트, 센서 데이터, 주식 가격..