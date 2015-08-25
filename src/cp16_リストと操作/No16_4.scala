package cp16_リストと操作
/*
 * リストの操作
 * リストに対する基本操作
 * ※
 */

object No16_4 extends App {
  /*
   * リストの全ての操作は以下の3つによって表現される。
   * ●head : リストの先頭要素を返す
   * ●tail : 先頭要素を除いたリストを返す
   * ●isEmpty : リストが空ならtrueを返す
   */

  val fruit = "apples" :: "oranges" :: "pears" :: Nil

  val nums = 1 :: 2 :: 3 :: 4 :: Nil

  val diag3 =
    (1 :: 0 :: 0 :: Nil) :: (0 :: 1 :: 0 :: Nil) :: (0 :: 0 :: 1 :: Nil) :: Nil

  val empty = Nil

  println(empty.isEmpty) //空だからtrue
  println(fruit.isEmpty) //空じゃないからfalse
  println(fruit.head) //fruitの先頭要素を返す
  println(fruit.tail.head) //fruitの2番目の要素を返す
  println(diag3.head)

  //Nil.head

  def isort(xs: List[Int]): List[Int] =
    if (xs.isEmpty)
      Nil
    else
      insert(xs.head, isort(xs.tail))

  def insert(x: Int, xs: List[Int]): List[Int] =
    if (xs.isEmpty || x <= xs.head)
      x :: xs
    else
      xs.head :: insert(x, xs.tail)
}
