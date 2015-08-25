package cp29_ScalaとJavaの結合

/**
 * ScalaとJavaの結合
 * 存在型
 */
object No29_3 extends App {

  // 存在型
  // Iterator[T] forSome { type T }

  // Iteratorの型パラメータが何型か分からない
  val wild = new Wild
  val sat = javaSet2ScalaSet(wild.contents)

  println(sat.set)

  // val set =Set.empty[???]//←で???に何を指定するべきか分からない

  //↑を実現するためには以下のテクニックを用いる必要がある(めんどくせー)
  import java.util.Collection
  import scala.collection.mutable.Set
  abstract class SetAndType {
    type Elem //抽象型
    val set: Set[Elem]
  }
  def javaSet2ScalaSet[T](jset: Collection[T]): SetAndType = {
    val sset = Set.empty[T] //Setを"T型"で初期化できるようになった
    val iter = jset.iterator //イテレータを得る
    while (iter.hasNext) sset += iter.next //Set[T]へ要素:Tを追加
    new SetAndType {
      type Elem = T //抽象型をT型に具象化
      val set = sset //setにssetをSET!!!
    }
  }
}
