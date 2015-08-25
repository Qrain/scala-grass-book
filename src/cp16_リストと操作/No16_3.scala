package cp16_リストと操作
/*
 * リストの操作
 * リストの構築
 * ※
 */

object No16_3 extends App {

  //実際のところ、List(1,2)は 1 :: 2 :: Nil に展開される。
  //空リストの先頭に要素を追加していくことで特定の型のリストが構築される。
  val fruit = "apples" :: "oranges" :: "pears" :: Nil

  val nums = 1 :: 2 :: 3 :: 4 :: Nil

  val diag3 =
    (1 :: 0 :: 0 :: Nil) :: (0 :: 1 :: 0 :: Nil) :: (0 :: 0 :: 1 :: Nil) :: Nil

  val empty = Nil
}
