package cp16_リストと操作
/*
 * リストの操作
 * Listクラスの一階メソッド
 * ※一階メソッドとは引数として関数を取らないメソッド(！高階メソッド)
 */

object No16_6 extends App {

    //リストの連結(::は要素をリストへ追加するが、:::はリスト同士を結合する)
    println(List(1, 2) ::: List(3, 4, 5))
    println(Nil ::: List(1, 2, 3))
    println(List(1, 2, 3) ::: List(4))

    //分割統治原則
    def append[T](xs: List[T], ys: List[T]): List[T] = //引数として２つのリストを取り連結する関数
        xs match { //パターンに分けるのが分割
            case Nil      => ys
            case x :: xs1 => x :: append(xs1, ys) //xsを分割していくといずれxs1はNilとなる
            //↑この再帰呼び出しの計算部分が統治らしい
        }
    println(append(List("和", "田", "谷"), List("広", "人")))

    //リストの末尾へのアクセス
    val list = List("先頭", "要素", "要素", "要素", "要素", "末尾")
    println(list.tail) //先頭要素を除いたリスト
    println(list.init) //末尾要素を除いたリスト
    //先頭要素へのアクセスは単位時間で実行可能だが、末尾要素へのアクセス時間はリスト長に比例する
    //よってデータ構造の設計は、末尾ではなく先頭へのアクセス操作で実現したほうがいい。

    //要素の選択
    println(list.indices) //各要素に対応するインデックスリストを生成

}
