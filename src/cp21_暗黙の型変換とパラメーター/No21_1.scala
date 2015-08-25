package cp21_暗黙の型変換とパラメーター

import scala.collection.immutable.WrappedString

/*
 * 暗黙の型変換とパラメーター
 * 暗黙の型変換
 */

object No21_1 extends App {

    /*
   * ある型からある型へとコンパイラが暗黙の内に変換メソッドを挿入する機構
   */
    val str = "A3BC2DE4F1G1"
    println(str.count(_.isDigit))
    //↑は暗黙的に↓のコードへ変換される
    println(stringWrapper(str).count(_.isDigit))
    println(withSpaces(stringWrapper(str))) //似たように機能する暗黙型変換が存在するのでエラーなのか･･･？

    implicit def stringWrapper(s: String) = new IndexedSeq[Char] { //ランダムアクセスSEQに暗黙変換する
        def length = s.length
        def apply(i: Int) = s.charAt(i)
    }
    def withSpaces(seq: IndexedSeq[Char]) = seq mkString "->"

}

