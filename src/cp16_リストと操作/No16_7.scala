package cp16_リストと操作
/*
 * リストの操作
 * Listクラスの高階メソッド
 */

object No16_7 extends App {

    //リストのフィルタリング
    val list = List("先頭 ", " 要 素", "炭素", "  箪笥  ", " 算  数", "末尾")

    println(list.partition(_.indexOf(" ") != -1)) //空白が含まれているのとそうでないの

    println(list.find(_.startsWith(" "))) //空白で始まる要素があればその最初の要素を返す
    println(list.find(_.startsWith("天"))) //↑該当しなければNoneを返す

    println(list.span(!_.startsWith(" "))) //記述式を満たす側とそうでない側に分割
    //↑ takeWhile と dropWhile  を合わせたもの

    //リストを対象とした述語関数:forall & exists
    val list2 = List(1, 2, 3, 4, 5, 6)
    println(list2.forall(_ >= 0)) //全要素が正の整数かどうか
    println(list2.exists(_ == 6)) //要素内に6が存在するか

    //リストの畳み込み: /: & :\ (全要素同士を二項演算子で結合する)結合順が違うよ
    println((0 /: list2)(_ + _)) //全要素同士を足し合わせる(先頭要素0)
    println((1 /: list2)(_ * _)) //全要素同士を掛け合わせる(先頭要素1)
    println((list2 :\ 0)(_ + _)) //全要素同士を足し合わせる(先頭要素0)
    println((list2 :\ 1)(_ * _)) //全要素同士を掛け合わせる(先頭要素1)

    println((List[Int]() /: list2)((x, y) => y :: x)) //リスト長に比例した計算量で要素逆転

}
