package cp15_ケースクラスとパターンマッチ

sealed abstract class Expr //継承されるので抽象
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

object No15_1 {

    def main(args: Array[String]) {
        /*
     * 算術式を操作するライブラリを書いてみる
     *
     * 式を司るケースクラスを4つ定義する。
     * 以下にケースクラスの定義でコンパイラが行うことを示す。
     * ・ケースクラスと同名のファクトリーメソッドを提供する。生成にnewが必要なくなる。
     * ・クラスパラメータリストにvalプレフィックスを付ける。つまり、自動的にパラメータフィールドになる。
     * ・クラスに対して、自然なtoString,hashCode,equalsメソッドを追加する。
     */

        val v = Var("x")
        val op = BinOp("+", Number(1), v)
        println(v.name) //パラメータフィールドであることが確認できる
        println(op.left)

        println(op) //自然な？toString
        assert(op.right == Var("x")) //自然なequals

        println(simplifyTop(UnOp("-", UnOp("-", Var("n"))))) //ただのVar("n")になっている

    }

    def simplifyTop(expr: Expr): Expr = expr match {
        //"-"などを定数パターン,eを変数パターンという。
        //パターンにマッチしなければMatchErrorという例外を投げる。
        case UnOp("-", UnOp("-", e))  => e //負の負は元のまま
        case BinOp("+", e, Number(0)) => e //0加算は元のまま
        case BinOp("*", e, Number(1)) => e //1乗算は元のまま
        case _                        => expr //↑以外だったらそのまま返す

    }

}
