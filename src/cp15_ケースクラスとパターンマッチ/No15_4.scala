package cp15_ケースクラスとパターンマッチ
/*
 * ケースクラスとパターンマッチ
 * パターンのオーバーラップ
 * ※
 */

object No15_4 {
    def main(args: Array[String]) {

        //↓パターンの順序が重要な意味を持つ例
        def simplifyAll(e: Expr): Expr = e match { //包括的なパターンは個別的なパターンの後に定義してある
            case UnOp("-", UnOp("-", e))  => simplifyAll(e) //最後のパターン以外は再帰呼び出しをしている
            case BinOp("+", e, Number(0)) => simplifyAll(e)
            case BinOp("*", e, Number(1)) => simplifyAll(e)
            case UnOp(op, e)              => UnOp(op, simplifyAll(e))
            case BinOp(op, l, r)          => BinOp(op, simplifyAll(l), simplifyAll(r))
            case _                        => e
        }

    }
}
