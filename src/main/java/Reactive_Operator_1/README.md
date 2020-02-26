# Reactive Operator - 1

[RxJava 프로그래밍](http://www.yes24.com/Product/goods/45506284)

- 주의 : 참고 도서는 이 책이기 때문에 현재 ReactiveX 연산자 함수가 없을 수도 있음. 너무 의존하지 말기.

1. map() : 원하는 입력값을 어떤 함수에 넣어서 원하는 값으로 변환하는 함수 ( Python map() ? )

   ```java
    String[] balls = {"1", "2", "3"}'
    
    Observable.Observable<String> source = Observable.Observable.fromArray(balls)
    	.map(ball -> ball + "<>");
    source.subscribe(System.out::println);
    
    출력 결과
    1<>
    2<>
    3<>
   ```

   - with Function<> Interface

     ```java
       Function<? super T, ? extend R> mapper
     ```

     - Function Interface는 제네릭 타입 <T>를 인자로 받아 제네릭 타입 R을 반환한다

       String[] balls = {"1", "2", "3"};

       Function<String, String> addDiamond = ball -> ball + "<>"; Observable.Observable<String> source = Observable.Observable.fromArray(balls) .map(addDiamond); source.subscribe(System.out::println);

       출력 결과 1<> 2<> 3<>

     - 예제 2 : 적절한 숫자 반환

       String[] engs = {"A", "B", "C", "K"}; Function<String, Integer> engToIndex = eng -> { switch (eng) { case "A": return 1; case "B": return 2; case "C": return 3; default: return -1; } }; Observable.Observable<Integer> source = Observable.Observable.fromArray(engs) .map(engToIndex); source.subscribe(System.out::println);

       출력 결과 1 2 3 -1