package cp24_抽出子

/*
 * 抽出子
 * サンプル:メールアドレスの抽出
 */

object No24_1 extends App {

  //ヘルパー関数を利用した通常のメールアドレス抽出の例
  def isEMail(s: String): Boolean = true //メールアドレスか否か判定する
  def domain(s: String): String = "" //メールアドレスのドメイン部を示す
  def user(s: String): String = "" //メールアドレスのユーザー部を示す

  //以上の関数を利用してアドレスを構文解析した例
  val addr = "abc@def.com"
  if (isEMail(addr))
    println(user(addr) + " AT " + domain(addr))
  else
    println("not an email address")

  //上の例は使えなくはないが体裁と効率が悪い。よって・・・

  case class EMail(user: String, domain: String) //サンプル用のケースクラス
  //↓のようにできたら便利である
  //  addr match {
  //    case EMail(user, domain) => println(" AT ")
  //    case _ => println("not an email address")
  //  }

}

