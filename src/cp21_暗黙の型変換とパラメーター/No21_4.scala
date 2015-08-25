package cp21_暗黙の型変換とパラメーター

/*
 * 暗黙の型変換とパラメーター
 * レシーバーの変換
 */

object No21_4 extends App {

    /*
   * 新しい型の同時利用
   */
    val r1 = new Rational(2, 5)
    val r2 = new Rational(8, 15)
    println(r1 / r2) //普通にできる
    println(r1 + 19) //普通にできる
    println(23 + r2) //Int型にRational型を引数とする+メソッドが定義されていないのでエラー
    //そこで↓のようなimplicit関数定義を記述する
    implicit def intToRational(x: Int) = new Rational(x)
    //するとInt型のレシーバーをRational型へ変換して+メソッドが使えるようになる

    class Rational(n: Int, d: Int) extends Ordered[Rational] {
        /*
     * Rational最終版(cp06から転用)
     */
        require(d != 0)
        def this(n: Int) = this(n, 1)

        private val g = gcd(n.abs, d.abs) //引数を絶対値で渡す
        val numer = n / g //約分
        val denom = d / g //約分

        //トレイトOrderedからオーバーライドしたメソッド
        override def compare(that: Rational) = //ここにはオブジェクトの比較方法を定義する
            this.numer * that.denom - that.numer * this.denom

        override def toString = numer+" / "+denom

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
        //最大公約数算出メソッド
        private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
    }

    /*
   * 新しい構文のシミュレーション
   */
    Map((1, "one"), (2, "two"), (3, "three"))
    //↑に示すMap定義は↓のような可読性の高いコードに変換できる
    Map(1 -> "one", 2 -> "two", 3 -> "three")
    //上記を実現しているのは特別な言語構文ではなく、単に暗黙の型変換を行っているだけである
    //このimplicit関数はScala.Predefで定義されており、デフォルトでインポートされている。
    //関数は、Any型を同じくPredefで定義されているArrowAssoc型へと変換して->メソッドを使えるようにする。
    //->メソッドはレシーバーと引数のペアを生成して返す

    //このような機能を利用すれば、他言語では言語外DSLを開発する必要がある場合でも、
    //Scalaでは言語内DSLの定義でしのげる場合が多いらしい。。。
}

