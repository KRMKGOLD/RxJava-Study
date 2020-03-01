package Observable

import io.reactivex.Observable

fun main() {
    Observable.just(1, 2, 3, 4, 5).subscribe(System.out::println);
}