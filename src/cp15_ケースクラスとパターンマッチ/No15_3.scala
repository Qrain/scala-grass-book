package cp15_ケースクラスとパターンマッチ
/*
 * ケースクラスとパターンマッチ
 * パターンガード
 * ※
 */

object No15_3 {
    def main(args: Array[String]) {

        //e + e => e * 2 に置き換えるという単純化規則を実装するものとする

        BinOp("+", Var("x"), Var("x"))
        //↑の式は↓に変換できる
        BinOp("*", Var("x"), Number(2))

        /*
     * 単純化するための関数を以下のように定義するとエラーになる
     * なぜなら、それぞれのパターン変数はパターン中に1度しか定義できないからである。
     * そのため、パターンガードを使用する。
     * def simplifyAdd(e: Expr) = e match {
     * 	case BinOp("+", x, x) => BinOp("*", x, Number(2))
     * 	case _ => e
     * }
     */
        def simplifyAdd(e: Expr) = e match { //if~ パターンガードである
            case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2))
            case _                          => e
        }

    }
}
