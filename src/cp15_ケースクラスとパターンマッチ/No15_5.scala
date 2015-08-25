package cp15_ケースクラスとパターンマッチ
/*
 * ケースクラスとパターンマッチ
 * シールドクラス
 * ※ケースクラスのスーパークラスにsealed句を付ける
 */

object No15_5 {
    def main(args: Array[String]) {

        def describe1(e: Expr): String = e match { //パターンが網羅できていないため警告が表示される
            /*
       * match is not exhaustive!
       * missing combination BinOp
       * missing combination UnOp
       */
            case Number(n) => "a number : "+n
            case Var(n)    => "a variable : "+n
        }

        def describe2(e: Expr): String = e match { //文脈上NumberとVar以外は渡されないと判明している時に、警告を消す方法。
            case Number(n) => "a number : "+n
            case Var(n)    => "a variable : "+n
            case _         => throw new RuntimeException("決して起きない例外") //包括的なパターンにより警告を消す
        }

        def describe3(e: Expr): String = (e: @unchecked) match { //アノテーションで警告を消す
            case Number(n) => "a number : "+n //これによってコンパイラはeに関して徹底的なチェックを行わない
            case Var(n)    => "a variable : "+n
        }

    }
}
