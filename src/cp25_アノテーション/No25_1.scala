package cp25_アノテーション
import scala.collection.mutable.ListBuffer
import scala.beans.BeanProperty

/**
 * アノテーション
 * アノテーションを使う理由
 */
object No25_1 extends App {
  //meta-programming??

  @deprecated def 非推薦メソッド() {} //使用すべきでないメソッド

  @volatile var 揮発性フィールド = 0.0 //複数スレッドからアクセスされる変数

  @BeanProperty var a = 0 //set/getメソッドが自動生成される

  (List(1, 2, 3): @unchecked) match { //Nilのパターンマッチをチェックしない
    case x :: xs => println(x)
  }

}

