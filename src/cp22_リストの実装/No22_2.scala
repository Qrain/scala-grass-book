package cp22_リストの実装
import scala.collection.mutable.ListBuffer

/*
 * リストの実装
 * ListBufferクラス
 */

object No22_2 extends App {

  def incAll(xs: List[Int]) = {

    def inner(xs: List[Int]): List[Int] = xs match { //スタックフレームの関係でリストサイズが3~5万に制限される
      case Nil => Nil
      case x :: xs1 => inner(xs1).::(x + 1) //末尾再帰ではない関数
    }
    val t1 = System.currentTimeMillis
    inner(xs)
    System.currentTimeMillis - t1

  }

  def incAll2(xs: List[Int]) = { //手続的でクソ効率の悪いコード
    val t1 = System.currentTimeMillis
    var result = List[Int]() //空の整数リスト
    for (x <- xs) result = result ::: List(x + 1) //リストを末尾へ追加してくから効率が悪い
    System.currentTimeMillis - t1

  }

  def incAll3(xs: List[Int]) = { //ListBufferを使い効率よく処理をする
    val t1 = System.currentTimeMillis
    val buf = new ListBuffer[Int]
    for (x <- xs) buf += x + 1
    buf.toList
    System.currentTimeMillis - t1
  }

  val list = List.fill(5000)(1) //5,000個の整数リストを作る

  println(incAll(list)) //万どころか五千くらいでスタックオーバーフローを起こし使えない
  println(incAll2(list)) //確かに時間掛かる
  println(incAll3(list)) //リストサイズが大きくなると威力を発揮する
}

