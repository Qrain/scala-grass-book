package cp19_型のパラメータ化

/*
 * 型のパラメータ化
 * 情報隠蔽(前回の続き)
 */

object No19_2 extends App {

    /*
 * 関数型待ち行列の基本操作はこうだ
 * ●head:先頭要素を返す
 * ●tail:先頭要素を取り除いた残りを返す
 * ●append:指定した要素を末尾へ追加して返す
 */

    var q = Queue[Int]() //空のキュー(非公開コンストラクタ)
    val r = 0 to 30
    r foreach (i => q = q append i)
    r foreach { i =>
        println(q.head)
        q = q.tail
    }

    var q2 = Queue2[Int]() //空のキュー(非公開クラス)
    r foreach (i => q2 = q2 append i)
    r foreach { i =>
        println(q2.head)
        q2 = q2.tail
    }

    /*
   * 前回での非常に分かりにくいクラスパラメータをクライントコードから隠す手段が、情報隠蔽だそうで。。。
   * クラス名とクラスパラメータの間にprivate修飾子を入れることで基本コンストラクタを非公開にできる。
   * この時、基本コンストラクタにアクセスできるのはそのクラス内部とコンパニオンオブジェクトだけである。
   */
    object Queue {
        def apply[T](xs: T*) = new Queue[T](xs.toList, Nil) //コンパオブジェからはクラスQueueの基本コンスへアクセス可能
    }
    class Queue[T] private (private val leading: List[T], private val trailing: List[T]) {

        //外部から基本コン...にアクセスできないので補助コン...を定義する(直感的に使いやすいヤツ)
        def this() = this(Nil, Nil) //空の待ち行列を作る
        def this(e: T*) = this(e.toList, Nil)

        private def mirror = if (leading.isEmpty) new Queue[T](trailing.reverse, Nil) else this

        def head = mirror.leading.head

        def tail = { val q = mirror; new Queue[T](q.leading.tail, q.trailing) }

        def append(x: T) = new Queue[T](leading, x :: trailing)
    }
    /*
   * ↑以外の別の方法(非公開クラス)
   * 基本コンストラクタやクラスメンバを非公開にするのではなく
   * クラス自体を非公開にするというより過激な方法。。。
   * ここでQueue2はトレイトであって型ではないが、Queue2[T]と型パラメータを指定することで型になりうる。
   * 型パラメータの指定によって特化された型を生成できることから、トレイトQueue2を型コンストラクタと呼ぶ。
   */
    trait Queue2[T] {
        def head: T
        def tail: Queue2[T]
        def append(x: T): Queue2[T]
    }
    object Queue2 {
        def apply[T](xs: T*): Queue2[T] = new QueueImpl[T](xs.toList, Nil)

        //以下が非公開クラスで、オブジェクトQueue2内部からのみインスタンス化出来る
        private class QueueImpl[T](private val leading: List[T], private val trailing: List[T]) extends Queue2[T] {
            private def mirror = if (leading.isEmpty) new QueueImpl[T](trailing.reverse, Nil) else this
            def head = mirror.leading.head
            def tail = { val q = mirror; new QueueImpl[T](q.leading.tail, q.trailing) }
            def append(x: T) = new QueueImpl[T](leading, x :: trailing)
        }
    }
}

