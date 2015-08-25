package cp12_トレイト

/*
 * トレイト
 * Orderedトレイト
 * ※このトレイトを定義する事でオブジェクトの比較演算子の定義を容易に･･･
 */
object No12_4 {

    implicit def intToRational(x: Int) = new Rational(x) //必要に応じて整数をRational型に変換する

    def main(args: Array[String]) {

        val r1 = new Rational(3, 8)
        val r2 = new Rational(7, 24)

        //比較演算子が使えてるぅぅうッッ！？
        println(r1 * r2 < r1 / r2)

        if (r1 > 1) //暗黙の型変換によって実現している
            println(r1 + " is Over 1...")
        else
            println(r1 + " was 1...?")
    }
}
object Rational

class Rational(n: Int, d: Int) extends Ordered[Rational] {
    /*
   * Rational最終版(cp06から転用)
   * クラスメンバとして比較演算子を定義するのではなく
   * Orderedトレイトをミックスインして定義を省略する。
   * 何らかの比較方法で順序を決定するクラスを定義する時は
   * このトレイトをミックスインするべきである。
   */

    require(d != 0)
    def this(n: Int) = this(n, 1)

    private val g = gcd(n.abs, d.abs) //引数を絶対値で渡す
    val numer = n / g //約分
    val denom = d / g //約分

    //トレイトOrderedからオーバーライドしたメソッド
    override def compare(that: Rational) = //ここにはオブジェクトの比較方法を定義する
        this.numer * that.denom - that.numer * this.denom

    override def toString = numer + " / " + denom

    def +(i: Int): Rational = this + new Rational(i)
    def +(that: Rational) = new Rational(
        numer * that.denom + that.numer * denom,
        denom * that.denom)

    def -(i: Int): Rational = this - new Rational(i)
    def -(that: Rational) = new Rational(
        numer * that.denom - that.numer * denom,
        denom * that.denom)

    def *(i: Int): Rational = this * new Rational(i)
    def *(that: Rational) =
        new Rational(numer * that.numer, denom * that.denom)

    def /(i: Int): Rational = this / new Rational(i)
    def /(that: Rational) =
        new Rational(numer * that.denom, denom * that.numer)

    def lessThan(that: Rational) = this.numer * that.denom < that.numer * this.denom
    def max(that: Rational) = if (this.lessThan(that)) that else this

    private def gcd(a: Int, b: Int): Int = //最大公約数算出メソッド
        if (b == 0) a else gcd(b, a % b)
}

