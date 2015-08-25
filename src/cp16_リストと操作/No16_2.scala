package cp16_リストと操作
/*
 * リストの操作
 * List型
 * ※配列と同様に等質的であり、T型の要素のリストはList[T]である。
 */

object No16_2 extends App {

  //以下リストの型を明示的に指定した例
  val fruit: List[String] = List("apples", "oranges", "pears")

  val nums: List[Int] = List(1, 2, 3, 4)

  val diag3: List[List[Int]] = List(
    List(1, 0, 0),
    List(0, 1, 0),
    List(0, 0, 1))

  val empty: List[Nothing] = List()

  //リスト型は共変であり、S型がT型のサブ型ならばList[S]はList[T]のサブ型になる。
  val anyval: List[AnyVal] = List(0.5) //List[Double]はList[AnyVal]のサブ型となり代入できる

  //Nothingは全てのサブ型なのでどんな型のリストにも代入できる
  val list1: List[String] = List()
  val list2: List[Int] = List()
  val list3: List[java.io.File] = List()

}
