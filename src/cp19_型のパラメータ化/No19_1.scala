package cp19_型のパラメータ化

/**
 * 型のパラメータ化
 * 関数型待ち行列(キュー)
 */
object No19_1 extends App {

    /*
 * 関数型待ち行列の基本操作はこうだ
 * ●head:先頭要素を返す
 * ●tail:先頭要素を取り除いた残りを返す
 * ●append:指定した要素を末尾へ追加して返す
 */

    var q = new Queue[Int](Nil, Nil)

    (0 to 50) foreach { i =>
        q = q append i
    }

    (0 to 30) foreach { i =>
        println(q.head)
        q = q.tail
    }

    /*
   * 公開メソッドhead,tail,appendの呼び出し頻度に偏りがないという前提で、それぞれの平均計算量が一定である待ち行列。
   * ただし、headの呼び出しが多いとQueueの再構築を何度も実行する可能性がある。そして、その処理は比較的コストが高い。
   */
    class Queue[T](private val leading: List[T], private val trailing: List[T]) {

        private def mirror = if (leading.isEmpty) new Queue(trailing.reverse, Nil) else this

        def head = mirror.leading.head

        def tail = { val q = mirror; new Queue(q.leading.tail, q.trailing) }

        def append(x: T) = new Queue(leading, x :: trailing)
    }
}

