package cp24_抽出子

/*
 * 抽出子
 * 抽出子とシーケンスパターン
 */

object No24_5 extends App {
  //Scalaではリストや配列などのシーケンスに対して
  //以下のようなパターンマッチを書くと各要素へアクセスできる
  List("apple", "banana", "cope", "documents", "elem", "functional") match {
    case List() => println("Nil")
    case List(x, "banana", "cope", z @ _*) => println(z)
    case List(x, y, _*) => println(x + ":" + y)
  }
  //上記のようなシーケンスパターンが書けるのは
  //scala.List	コンパニオンオブジェクトがunapplySeqを定義する抽出子だからである
  object ListX {
    def apply[T](elems: T*) = elems.toList //注入メソッド
    def unapplySeq[T](x: List[T]): Option[Seq[T]] = Some(x) //抽出メソッド
  }

}

