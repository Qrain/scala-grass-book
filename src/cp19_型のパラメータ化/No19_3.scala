package cp19_型のパラメータ化

/*
 * 型のパラメータ化
 * 変位指定アノテーション
 */

object No19_3 extends App {

    /*
 * 非変⇒SがTのサブ型であっても、X[S]とX[T]に親子関係は成立しない。
 * 共変⇒SがTのサブ型であるとき、X[S]はX[T]のサブ型になる。
 * 反変⇒SがTのサブ型であるとき、X[T]はX[S]のサブ型になる。(??ちょい意味不)
 * ※Scalaにおける変位指定はデフォルトで非変になっている。
 */

    /*
   * 変位指定アノテーション理解用サンプル
   */
    class Cell[T](init: T) { //非変
        private var current = init
        def get = current
        def set(x: T) { current = x }
    }
    /*
   * Scalaコンパイラは型パラメータについて、それぞれポジションをチェックする。
   * ポジションとは型パラメータを使える全ての場所であり、変数の型やメソッドなどが対象となる。
   * ポジションはそれぞれ、陽性・中性・陰性に分類される。
   */
    class Cell2[+T](init: T) { //共変にする条件は厳しい(varなしとかSetterだめとか･･･etc)
        def get = init
        //def set(x: T) { init = x }
    }
}

