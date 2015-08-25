package cp10_合成と継承

import scala.collection.mutable.ArrayBuffer

abstract class Element { //レイアウト要素を表す中傷クラス

    import Element.elem

    //等号と本体を省けば抽象メソッドとなる
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

    def above(that: Element) = { //プロトタイプ(幅一定ていう設定)
        val thisx = this widen that
        val thatx = that widen this
        elem(thisx.contents ++ thatx.contents)
    }

    def beside(that: Element) = { //プロトタイプ(高さ一定ていう設定)
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
            })

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

object Element { //クラスElementのコンパニオンオブジェクト

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

    /**
     *  chでwidth height分埋め尽くす
     */
    private class UniformElement(
            ch: Char,
            override val width: Int,
            override val height: Int) extends Element { //↓からクラスUniformElementの内容定義です。
        private val line = ch.toString * width

        def contents = {
            val ab = ArrayBuffer.empty[String]
            for (i <- 1 to height)
                ab += line
            ab.toArray
        }
    }
}

////////////////////////////////////////////////////パラメータフィールドはこんなことも定義できる。
class Cat { val dangerous = false }
class Tiger( //パラメータフィールドでオーバーライドしたり非公開メンバを定義できる
    override val dangerous: Boolean,
    private var age: Int)
        extends Cat

