package cp16_リストと操作
/*
 * リストの操作
 * リストパターン
 * ※List(...)形式だとリストの全要素を、::とNilを使えば任意の要素に少しずつ分解できる。
 */

object No16_5 extends App {
    val fruit = List("apples", "oranges", "pears")
    val nums = List(1, 2, 3, 4)
    val diag3 = List(
        List(1, 0, 0),
        List(0, 1, 0),
        List(0, 0, 1))
    val empty = List()

    val List(a, b, c) = fruit //長さ3のリストにマッチする
    val x :: y :: rest = fruit //長さ2以上のリストにマッチする

    //整数リストの昇順ソートテスト
    isort(List(13, 6, 67, 7365, 6776, 453, 5, 90, 361)) foreach println

    /*
   * 前節のソートメソッドもパターンマッチを使えば
   * リストの基本メソッド(head,tail,isEmpty)を使わなくて済む。
   */
    def isort(xs: List[Int]): List[Int] = xs match {
        case Nil       => Nil
        case x :: tail => insert(x, isort(tail))
    }

    def insert(x: Int, xs: List[Int]): List[Int] = xs match {
        case Nil => List(x)
        case y :: tail =>
            if (x <= y)
                x :: xs
            else
                y :: insert(x, tail)
    }
}
