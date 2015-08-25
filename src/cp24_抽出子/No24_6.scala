package cp24_抽出子

/**
 * 抽出子
 * 抽出子とケースクラス
 */
object No24_6 extends App {
  //私は抽出子とケースクラスとで何が具体的に違うのか興味がある
  //抽出子の機能はケースクラスで十分補完できるのでは？と思う。

  case class CC[T](x: T*)

  object CO {
    def apply[T](x: T*) = x.toList
    def unapplySeq[T](x: List[T]) = Some(x.toSeq)
  }

  val cc = CC(1, 2, 3)
  val co = CO(1, 2, 3)

  cc match { //←セレクター式がケースクラスCCのインスタンスだと分かってしまう
    case CC(x @ _*) => println(x) //可変要素をxに束縛
  }

  co match { //←はセレクター式がList[Int]であり具体的なオブジェクト名は分からない
    case CO(x @ _*) => println(x)
  }

  //COはオブジェクトの正体を隠せる反面、定義するためのコードが長くなる
  //CCはケースクラスであるため定義が簡潔である反面、。。。
  //どちらが良いかはケースバイケースだが、初めケースクラスで定義しておいて
  //あとから抽出子へ乗り換えるという選択肢もある。
}

