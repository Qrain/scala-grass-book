package cp16_リストと操作
/*
 * リストの操作
 * Listオブジェクトのメソッド
 */

object No16_8 extends App {

  //要素からリストを作る
  println(List.apply(1, 2, 3) == List(1, 2, 3))

  //数値の範囲を作る
  println(List.range(5, 20, 3)) //from until step

  //同じ値からリストを作る make → fill に置き換え（カリー化を利用）
  println(List.fill(5)('T'))

  //リストのジッパー外し(unzip)
  val zipped = List.fill(10)("Oh").zipWithIndex
  val unzipped = zipped.unzip //ここでジッパーを外している
  println(unzipped)

  //リストの連結
  val ls = List.fill(5)(List("a"))
  println(ls)
  println(ls.flatten)

  //リストのペアマッピングやテスト
  println((zipped, ls).zipped.map((_, _))) //2リストの要素同士をペアにして返す

  val xxx = (zipped, ls).zipped.map((z, l) => z._1 + ":" + l.mkString(","))

}
