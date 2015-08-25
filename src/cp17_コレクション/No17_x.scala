package cp17_コレクション

/**
 * コレクション
 * 色々なコレクション
 */

object No17_x extends App {

    import scala.collection.mutable
    import scala.collection.immutable.{ Queue, Stack, TreeSet, TreeMap, Seq }

    /**
     *  ----------
     * | Iterable |
     *  ----------
     *  ↑
     *  | Seq, Set, Map のスーパートレイトは Iterable
     *  |
     *  -----   -----   -----
     * | Seq | | Set | | Map |
     *  -----   -----   -----
     */

    /**
     * << Seqトレイトを継承するコレクション達 >>
     * ・List
     * ・Array
     * ・ListBuffer
     * ・ArrayBuffer
     * ・Queue
     * ・Stack
     * ・etc...
     */
    val list = List(1, 2, 3) //先頭要素に高速アクセスできるが、末尾要素は遅い
    val ary = Array(1, 2, 3) //任意の配列要素へインデックスを使い効率的にアクセスできる
    val listbuf = mutable.ListBuffer(1, 2, 3) //先頭末尾要素の追加・削除ができる(先頭末尾関わらず追加処理時間は一定)
    val arybuf = mutable.ArrayBuffer(1, 2, 3) //↑の配列版的な意味

    val que = Queue() //待ち行列を表すコレクション
    val en1 = que.enqueue(1)
    val en2 = en1.enqueue(2 :: 3 :: 4 :: Nil)
    val de1 = en2.dequeue
    println(de1)

    val stack = Stack() //スタックを表すコレクション
    val pu1 = stack.push(1)
    val pu2 = pu1.push(2)
    val po1 = pu2.pop
    println(po1)

    val str = "Abcd!! Efch??" //文字列もChar型のシーケンスである
    println(str.filter(_.isLower)) //小文字のみ抽出する

    /**
     * << Setトレイトを継承するコレクション達 >>
     * ・Set
     * ・TreeSet
     * ・etc...
     */
    val set = Set(1, 2, 3) //集合
    println(set ++ List(1, 3, 5, 7)) //重複値は無い
    val ts = TreeSet(3, 9, 1, 3, 9, 4, 9, 8, 2) //適当な値を設定
    println(ts) //重複値が無く自然順序でソートされている

    /**
     * << Mapトレイトを継承するコレクション達 >>
     * ・Map
     * ・TreeMap
     * ・HashMap
     * ・SynchronizedMap
     * ・etc...
     */
    val map = Map(0 -> "A", 1 -> "B", 2 -> "C", 3 -> "D")
    println(map)
    val tm = TreeMap(2 -> "C", 0 -> "A", 3 -> "D")
    println(tm + (1 -> "B"))

    //同期する集合とマップ
    import mutable.{ Map, SynchronizedMap, HashMap }

    def makeMap: Map[String, String] =
        new HashMap[String, String] with SynchronizedMap[String, String] {
            override def default(key: String) = "Why do you want to know?"
        }

    val synmap = makeMap
    synmap ++= List("US" -> "Washington", "France" -> "Paris", "Japan" -> "Tokyo")
    println(synmap("Japan"))
    println(synmap("New Zealand"))
    synmap += ("New Zealand" -> "Wellington")
    println(synmap("New Zealand"))

    /*
   * ミュータブルとイミュータブルのどちらを使うべきか？
   *
   * 1.どちらか判然としない時はイミュータブルの方が動作を想定しやすい。
   * 2.イミュータブルで実装しておいて必要ならば後から切り替えることもできる。
   * 3.格納する要素数が少ない場合、イミュータブルの方がコンパクトにデータを格納できる
   * 4.格納要素が少ないミュータブルなコレクションが多数ある時は、イミュータブルへ切り替える道がある
   *
   */

}
