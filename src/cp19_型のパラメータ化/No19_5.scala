package cp19_型のパラメータ化

/*
 * 型のパラメータ化
 * 下限境界(lower bounds)
 */

object No19_5 extends App {

    /*
   * 前々回で作成したQueueクラスを共変にして、
   * appendメソッドの型パラメータに下限境界を設定した。
   */
    val qstr = Queue("ABC")
    val qany = qstr.append(123) //String型のQueueに整数を追加
    //↑自動的にInt型とString型の共通のスーパー型であるAny型のQueueが返される。

    println(qstr.head)
    println(qany.head)

    /*
   * 変位指定アノテーション(型パラメータ前の+と-)と下限境界(U>:T)がうまく機能している例
   * 定義サイドで変位指定すれば利用サイドはクラス設計時に苦労しないとか、するとか･･･
   */
    object Queue { def apply[T](xs: T*) = new Queue[T](xs.toList, Nil) }
    class Queue[+T] private (private val leading: List[T], private val trailing: List[T]) {
        def this() = this(Nil, Nil)
        def this(e: T*) = this(e.toList, Nil)
        private def mirror = if (leading.isEmpty) new Queue[T](trailing.reverse, Nil) else this
        def head = mirror.leading.head
        def tail = { val q = mirror; new Queue[T](q.leading.tail, q.trailing) }
        //ここで下限境界を設定してる
        def append[U >: T](x: U) = new Queue[U](leading, x :: trailing)
    }
}

