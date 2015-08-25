package cp21_暗黙の型変換とパラメーター

/*
 * 暗黙の型変換とパラメーター
 * implicitの規則
 */

object No21_2 extends App {

    /*
   * 以下に暗黙の型変換のルールを列挙する
   */

    /*
   * ●マーキングルール:implicitによって修飾された定義だけが暗黙の型変換が使われる
   */
    implicit def intToString(x: Int) = x.toString //型エラー発生時、コンパイラはimplicit修飾されている関数のみを探す。

    /*
   * ●スコープルール:挿入されてる暗黙の型変換は、単一の識別子としてスコープ内にあるか
   * 変換のソース型やターゲット型として対応付けられていなければならない
   */
    object A {
        implicit def convertAtoB(x: A) = new B //A型に関連づけられた暗黙変換関数
    }
    class A {
        def a(that: A) = "this is ["+this+"], that is ["+that+"]"
    }
    object B {
        implicit def convertBtoA(x: B) = new A //B型に関連づけられた暗黙変換関数
    }
    class B {
        def b(that: B) = "this is ["+this+"], that is ["+that+"]"
    }
    val a1 = new A
    val a2 = new A
    val b1 = new B
    val b2 = new B
    //ソース型やターゲット型へ関連付けられている例
    println(a1 a b2) //aメソッドへの引数値B型～A型へ変換
    println(a1 b a2) //レシーバa1をB型へ変換してbメソッドを使えるようにする
    println(b1 a b2) //レシーバb1をA型へ変換してaメソッドを使えるようにする
    println(b1 b a2) //bメソッドへの引数値A型～B型へ変換

    /*
   * ●あいまい回避ルール:暗黙の型変換は、ほかに挿入すべき変換がないときに限って挿入される
   */
    implicit def i2s(x: Int) = x.toString
    def toUpper(s: String) = s.map(_.toUpper)
    //  toUpper(123) //挿入すべき変換関数がintToStringとi2sの2つあるのでエラーが発生する

    /*
   * ●一度に一回ルール:暗黙の型変換は1度しか実行されない
   * コンパイラは暗黙の型変換関数を重複して適用しない。
   * intToString(i2s(123))は無いということである。
   */

    /*
   * ●明示的変換優先ルール:掛かれたままの状態でコードが型チェックをパスするときは、
   * 暗黙の型変換は行われない。
   */
    val int: Int = 1 //1はInt型なので型変換されずにそのままコンパイルされる。当たり前の話だな。
    val byte: Byte = 0xFF.toByte

}

