package cp24_抽出子

/*
 * 抽出子
 * 可変個の引数をとる抽出子
 */

object No24_4 extends App {

  object Domain {
    def apply(parts: String*): String = parts.reverse.mkString(".") //トップレベルドメインからのため逆順にする
    def unapplySeq(whole: String): Some[Seq[java.lang.String]] =
      Some(whole.split("\\.").reverse)
  }

  def isTomInDotCom(s: String) = s match {
    case EMail("tom", Domain("com", _*)) => true //抽出メソッドの一部("com")を固定要素とし残りを可変要素とする
    case _ => false
  }

  println(isTomInDotCom("tom@sun.com"))
  println(isTomInDotCom("peter@sun.com"))

  //メールアドレス用の新しい抽出子
  object ExtendedEMail {
    def unapplySeq(email: String): Option[(String, Seq[String])] = {
      val parts = email split "@" //@で区切って
      if (parts.size == 2)
        Some(parts(0), parts(1).split("\\.").reverse)
      else
        None
    }
  }

  val mail = "abc@def.ne.jp"

  //パターンマッチ
  val EMail(u, d) = mail //従来のEMail抽出子
  println(u + " : " + d)

  val ExtendedEMail(uu, dd @ _*) = mail //拡張したEMail抽出子(ddを可変要素として束縛)
  println(uu + " : " + dd)

}

