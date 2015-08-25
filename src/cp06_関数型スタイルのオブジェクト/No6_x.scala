package cp06_関数型スタイルのオブジェクト
/**
 *
 */
object No6_x {

    implicit def intToRational(x: Int) = new Rational(x) //必要に応じて整数をRational型に変換する

    def main(args: Array[String]) {
        /**
         * package cp6内に、Rational(分数)クラスを設計する。
         * Rationalクラスは分数を表現するクラスであり、加減乗除算をすることができる。
         * ex)	val r1 = new Rational(2, 3)
         * 		val r2 = new Rational(1, 2)
         * 			println(r1 + r2) //加算
         * 			println(r2 / r1)//除算
         *
         * Rationalの利用するクライアントプログラマの立場で考える。
         * 実際のインスタンス化方法を考える。
         * 関数型スタイルを考慮して、クラスRationalはimmutable(不変)な必要がある。
         * 実際にクラスRationalを同パッケージ内に作る。ファイル名⇒Rational.scala
         */

        //println(new Rational_1(2, 5)) //初歩版
        //println(new Rational_2(2, 5)) //toStringメソッド上書き版
        //new Rational_3(2, 0) //事前条件チェック追加版
        //println(new Rational_4(7, 10) add new Rational_4(3, 7)) //addメソッド追加版
        //println(new Rational_5(5, 2) max new Rational_5(2, 5)) //lessThan,maxメソッド追加版
        //println(new Rational_6(7)) //補助コンストラクタ追加版
        //println(new Rational_7(10, 100)) //約分機能追加版

        //クラスRational統一版テスト
        val x = Rational(10, 7)
        val y = Rational(5, 9)
        val z = Rational()
        println(x + y * x) //通常演算子による計算
        println(x / 5) //整数との演算
        println(5 / x max y) //整数を左被演算子とした演算(暗黙の型変換)
        println(z)
    }
}
