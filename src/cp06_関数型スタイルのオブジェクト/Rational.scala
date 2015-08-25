package cp06_関数型スタイルのオブジェクト
/*
class Rational_1(n: Int, d: Int) {
  /*
   * 分子と分母をクラスパラメータとして受け取る
   * Scalaコンパイラはこのパラメータから基本コンストラクタを生成する。
   * なのでJavaみたいに、コンストラクタを定義して仮引数をフィールドにコピーする必要はない。
   */
  println(n + " / " + d + " : " + toString()) //テスト出力
}
class Rational_2(n: Int, d: Int) {
  /*
   * toStringメソッドをオーバーライドする。
   * デフォルトではjava.lang.ObjectクラスのtoStringメソッドを継承しているため、
   * 役立つメッセージを出力しない。なので、分子と分母を出力するように変更する。
   */
  override def toString = n + " / " + d
}
class Rational_3(n: Int, d: Int) {
  /*
   * 事前条件チェックを実装する。
   * d(分母)に0が渡された場合は、分数として成立しないので
   * その時は、オブジェクトを生成できないよう制御する。
   * requireメソッドは、引数が真の時は制御が戻って来るが、
   * 偽の時は例外"IllegalArgumentException"を投げる。
   */
  require(d != 0) //事前条件チェックのため初めに記載する。
  override def toString = n + " / " + d
}
class Rational_4(n: Int, d: Int) {
  /*
   * フィールドを追加して、addメソッドを定義する。
   */
  require(d != 0) //事前条件チェックのため初めに記載する。
  val numer = n
  val denom = d
  override def toString = n + " / " + d
  def add(that: Rational_4): Rational_4 = //足した新たなRationalオブジェクトを返す
    new Rational_4(
      numer * that.denom + that.numer * denom,
      denom * that.denom)
}
*/

class Rational_5(n: Int, d: Int) {
    /*
   * lessThanメソッドを定義する。
   * レシーバーとパラメータのRaitonalオブジェクトを比較して
   * レシーバーの方が小さければTRUEを返す。
   */
    require(d != 0)

    val numer = n
    val denom = d

    override def toString = n+" / "+d

    //自己参照のための"this"キーワードを付けているが、この場合は無くても構わない。
    def lessThan(that: Rational_5) = this.numer * that.denom < that.numer * this.denom

    //自己と引数を比べて大きいほうを返す。戻り値として"this"自己そのものを返している。
    def max(that: Rational_5) = if (this.lessThan(that)) that else this
}

class Rational_6(n: Int, d: Int) {
    /*
   * 基本コンストラクタ以外に、補助コンストラクタを追加する。
   * 例えば、4/1のように分母が1の時はnew Rational(4)でインスタンス化できるようにする。
   * 全ての補助コンストラクタは、最初に他のコンストラクタをthis(...)で呼び出さなければならない。
   * これは、クラスへの唯一の入り口である基本コンストラクタを確実に呼び出さなければならない事を意味する。
   */
    require(d != 0)

    val numer = n
    val denom = d

    def this(n: Int) = this(n, 1) //補助コンストラクタ

    override def toString = n+" / "+d
}

class Rational_7(n: Int, d: Int) {
    /*
   * 非公開フィールドとメソッド
   * つまる所、約分機能を追加する。
   */
    require(d != 0)

    private val g = gcd(n.abs, d.abs) //引数を絶対値で渡す
    val numer = n / g //約分
    val denom = d / g //約分

    def this(n: Int) = this(n, 1)

    override def toString = numer+" / "+denom

    private def gcd(a: Int, b: Int): Int = //最大公約数算出メソッド
        if (b == 0) a else gcd(b, a % b)
}

object Rational {
    def apply(n: Int, d: Int) = new Rational(n, d)
    def apply(n: Int) = new Rational(n)
    def apply() = new Rational(1, 2)
}

class Rational(n: Int, d: Int) { //Rational最終版
    /*
   * addメソッドを通常演算子の+へ変更する。
   * ついでに、*メソッドも追加する。
   * 
   * Javaのメソッド名やクラス名などの指定方法をキャメルケースという。
   * getImage, FileInputStreamなど途中に大文字が含まれており、
   * ラクダのコブに見えることからそう呼ばれている。
   */
    require(d != 0)

    private val g = gcd(n.abs, d.abs) //引数を絶対値で渡す
    val numer = n / g //約分
    val denom = d / g //約分

    def this(n: Int) = this(n, 1)

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

    private def gcd(a: Int, b: Int): Int = //最大公約数算出メソッド
        if (b == 0) a else gcd(b, a % b)
}