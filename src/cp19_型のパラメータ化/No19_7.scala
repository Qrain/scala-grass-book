package cp19_型のパラメータ化

/**
 * 型のパラメータ化
 * オブジェクト非公開データ
 */
object No19_7 extends App {

    /**
     * 前々回で作成したQueueクラスを共変にして、
     * appendメソッドの型パラメータに下限境界を設定した。
     */
    val qstr = Queue("ABC")
    val qany = qstr.append(123) //String型のQueueに整数を追加
    //↑自動的にInt型とString型の共通のスーパー型であるAny型のQueueが返される。

    println(qstr.head)
    println(qany.head)

    /*
   * 変位指定アノテーション(型パラメータ前の+とか-)と下限境界(U>:T)がうまく機能している例
   * 定義サイドで変位指定すれば利用サイドはクラス設計時に苦労しないとか、するとか･･･
   * No19_5でのQueueクラスを最適化(leading空状態で連続headしてもコピーを繰り返さない)したもの。
   */
    object Queue { def apply[T](xs: T*) = new Queue[T](xs.toList, Nil) }
    class Queue[+T] private (
            private[this] var leading: List[T], //←これのお陰で共変なのに
            private[this] var trailing: List[T]) { //←varが使える

        def this() = this(Nil, Nil)
        def this(e: T*) = this(e.toList, Nil)

        private def mirror() {
            if (leading.isEmpty)
                while (!trailing.isEmpty) {
                    leading = trailing.head :: leading
                    trailing = trailing.tail
                }
        }

        def head = {
            mirror()
            leading.head
        }

        def tail = {
            mirror()
            new Queue[T](leading.tail, trailing)
        }

        def append[U >: T](x: U) = new Queue[U](leading, x :: trailing)
    }
}

