package cp23_for式の再説

/*
 * for式の再説
 * for式
 */

object No23_1 extends App {

  //for式 => for ( seq ) yield expr
  //seq => [ジェネレーター | 定義 | フィルター]

  val ls = List(1, 2, -3, 5, -7, 9, -10)

  for {
    n <- ls //ジェネレーター
    num = n.abs //定義
    if num % 2 == 0 //フィルター
  } println(num) //整数リストから偶数を正の値として表示する

  for {
    x <- List(1, 2, 3) //複数ジェネレータが含まれる時、前のジェネレータより
    y <- List("One", "Two", "Three") //後ろのジェネレータの方が変化が速い
  } println(x + " : " + y)

}

