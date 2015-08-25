package cp15_ケースクラスとパターンマッチ {
  /*
 * ケースクラスとパターンマッチ
 * より大きなサンプル
 * ※パターンマッチを利用した算術式整形サンプルを作る
 */

  object No15_8 extends {

    def main(args: Array[String]): Unit = {
      import org.stairwaybook.expr._
      val f = new ExprFormatter

      val e1 =
        BinOp("*",
          BinOp("/", Number(1), Number(2)),
          BinOp("+", Var("x"), Number(1)))

      val e2 =
        BinOp("+",
          BinOp("/", Var("x"), Number(2)),
          BinOp("/", Number(1.5), Var("x")))

      val e3 = BinOp("/", e1, e2)

      for (e <- Array(e1, e2, e3))
        println(f.format(e) + "\n" * 2)

    }
  }

  //必要なライブラリ軍をパッケージにまとめるお
  package org.stairwaybook {

    package expr {

      import layout.Element //レイアウトライブラリをインポートしとくよ
      import Element.elem

      sealed abstract class Expr //継承されるので抽象
      case class Var(name: String) extends Expr
      case class Number(num: Double) extends Expr
      case class UnOp(operator: String, arg: Expr) extends Expr
      case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

      class ExprFormatter {
        //優先順位の昇順でグループにまとめた演算子を格納する配列
        private val opGroups = Array( //除算演算子以外を定義する
          Set("|", "||"),
          Set("&", "&&"),
          Set("^"),
          Set("==", "!="),
          Set("<", "<=", ">", ">="),
          Set("+", "-"),
          Set("*", "%"))

        //演算子から優先順位を導き出すマップ
        private val precedence = {
          for {
            i <- 0 until opGroups.size //インデックス兼優先順位を生成
            op <- opGroups(i) //SetじゃなくSetの各要素をStringとしてマッチさせる
          } yield op -> i
        }.toMap

        private val unaryPrecedence = opGroups.size //最も高い優先順位
        private val fractionPrecedence = -1 //除算演算子用の特別な優先順位

        private def format(e: Expr, enclPrec: Int): Element = e match {

          case Var(name) => elem(name)

          case Number(num) =>
            val s = num.toString
            elem(if (s endsWith ".0") s.substring(0, s.size - 2) else s)

          case UnOp(op, arg) => //単項演算子
            elem(op) beside format(arg, unaryPrecedence)

          case BinOp("/", left, right) => //除算なら整形済みrightの上に整形済みleftを積んだ形にする
            val top = format(left, fractionPrecedence) //1/2 左は分子なので上になる
            val bot = format(right, fractionPrecedence)
            val line = elem('-', top.width max bot.width, 1) //分子と分母を隔てる横線
            val frac = top above line above bot
            if (enclPrec != fractionPrecedence)
              frac //fracが他の除算の引数でなければ(除算が入れ子じゃなければ)
            else
              elem(" ") beside frac beside elem(" ") //入れ子ならば左右にスペースを入れる

          case BinOp(op, left, right) => //除算以外の全ての2項演算にマッチ
            val opPrec = precedence(op) //演算子から優先順位を得る
            val l = format(left, opPrec)
            val r = format(right, opPrec + 1)
            val oper = l beside elem(" " + op + " ") beside r
            if (enclPrec <= opPrec)
              oper
            else
              elem("(") beside oper beside elem(")") //この二項演算の方が高い優先順位ならば括弧で囲む

        }

        def format(e: Expr): Element = format(e, 0) //トップレベルの演算を整形する公開メソッド
      }

    }

    package layout {

      private[cp15_ケースクラスとパターンマッチ] abstract class Element { //cp10から転用

        import Element.elem

        def contents: Array[String] /////抽象メソッドなり
        /*
     * メソッド本体を定義すれば自動的に具象メソッドとなる
     * さらに、以下のメソッドは height(): Int (空括弧メソッド)と定義しておらず
     * パラメータリストを持たない為、パラメーターなしメソッドと呼ばれる。
     * そのため、this.heightのようにフィールドと同様にアクセスできる。
     * これは、統一形式アクセスの原則に従っている。
     * (フィールド及びメソッド、どちらの実装方法でもクライアントコードが影響を受けてはならない。)
     * 一方で、パラメータをとらないメソッドであっても、何らかの副作用を及ぼすようであれば
     * method(){ } のように、空括弧を省略してはならない。これはクライアントプログラマーに、フィールドではなく
     * メソッドを実行していると確実に分からせ混乱を防ぐためである。
     */
        def height: Int = contents.size
        def width: Int = if (height == 0) 0 else contents.head.size

        def above(that: Element) = {
          val thisx = this widen that
          val thatx = that widen this
          elem(thisx.contents ++ thatx.contents)
        }

        def beside(that: Element) = {
          val thisx = this.heighten(that)
          val thatx = that.heighten(this)
          elem(for {
            (line1, line2) <- thisx.contents zip thatx.contents //要素同士をタプルにまとめたコレクションを返す
          } yield line1 + line2)
        }

        private def widen(that: Element): Element =
          if ((this.width max that.width) == this.width)
            this
          else
            elem(this.contents.map { s =>
              val pad = " "
              val left = pad * ((that.width - s.size) / 2)
              val right = pad * (that.width - s.size - left.size)
              left + s + right
            }) ensuring {
              (this.width max that.width) == _.width //ここで条件が真ならば結果値が返され、偽ならば例外が投げられる。
            }

        private def heighten(that: Element): Element =
          if ((this.height max that.height) == this.height)
            this
          else {
            val top = elem(' ', this.width, (that.height - this.height) / 2)
            val bot = elem(' ', this.width, that.height - this.height - top.height)
            top above this above bot
          }

        override def toString = contents mkString "\n"
      }

      private[cp15_ケースクラスとパターンマッチ] object Element { //クラスElementのコンパニオンオブジェクト

        /*
                 * 以下のelemメソッドは戻り値型を指定してないと、
                 * 型推論により非公開クラスの型となってしまい
                 * エラーが起きる。なので、全非公開クラスのスーパー型である
                 * Elementを指定する必要がある。
                 */

        def elem(contents: Array[String]): Element = new ArrayElement(contents)
        def elem(ch: Char, w: Int, h: Int): Element = new UniformElement(ch, w, h)
        def elem(line: String): Element = new LineElement(line)

        /*
                 * クライアントはElementクラスのサブクラス群を
                 * 直接インスタンス化する必要が無くなったので
                 * ↓のように非公開クラスにして安全性を高める。
                 */
        private class ArrayElement(val contents: Array[String]) extends Element

        private class LineElement(s: String) extends Element { //一行の短型要素
          val contents = Array(s) //Elementの抽象メソッドをOverride
          override def width = s.size
          override def height = 1
        }

        private class UniformElement( //chでwidth height分埋め尽くす
            ch: Char,
            override val width: Int,
            override val height: Int) extends Element { //↓からクラスUniformElementの内容定義です。
          private val line = ch.toString * width
          def contents = {
            val ab = scala.collection.mutable.ArrayBuffer.empty[String]
            for (i <- 1 to height)
              ab += line
            ab.toArray
          }
        }
      }
    }
  }
}
