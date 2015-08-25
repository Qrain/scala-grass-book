package cp24_抽出子

/*
 * 抽出子
 * 抽出子。ってまんまなんだけど･･･
 */

object No24_2 extends App {

  val addr: Any = "abc@def.com" //型がunapplyメソッドの引数型に適合しないときStringへキャストされる

  addr match {
    //抽出子オブジェクトを参照する以下のようなパターンを検出したら
    //セレクター式を対象に抽出子のunapplyメソッドが呼び出される
    //EMail.unapply(addr)
    case EMail(user, domain) => println(user + " AT " + domain)
    case _ => println("not an email address")
  }

  val em = EMail("abc", "def.com") //apply(注入)メッソドでメールアドレスを構築
  println(EMail.unapply(em)) //構築されたメールアドレスをunapply(抽出)メソッドで分解

  println { //unapplyの戻り値がapplyの引数となるような相補的な関係になるべきである
    EMail.unapply(addr.toString) match {
      case Some((u, d)) => EMail.apply(u, d)
      case None => "not an email address"
    }
  }
}

//EMail文字列抽出子オブジェクト
object EMail extends ((String, String) => String) {
  //継承させることで((String, String) => String)を要求するメソッドへと渡せるようになる
  //注入メソッド(オプション)
  def apply(user: String, domain: String) = user + "@" + domain
  //抽出メソッド(必須)
  def unapply(str: String) = {
    //オブジェクトを抽出子へと変えるメソッドでありapplyの構築プロセスを逆戻しにする
    val parts = str split "@"
    if (parts.size == 2)
      Some(parts(0), parts(1))
    else //文字列がメールアドレスでない時
      None
  }
}
