package cp21_暗黙の型変換とパラメーター

/*
 * 暗黙の型変換とパラメーター
 * 要求された型への暗黙の型変換
 */

object No21_3 extends App {

    implicit def doubleToInt(d: Double) = d.toInt
    //通常下のようなコードは、浮動小数点数の精度が極端に落ちるために変換できない
    val int: Int = 3.5
    //↑のコードはコンパイラによって↓のコードに書き換えられる
    val int2: Int = doubleToInt(3.5)
    //しかし上記のような操作は、水面下で数値の精度を落とすため奨励されない。
}

