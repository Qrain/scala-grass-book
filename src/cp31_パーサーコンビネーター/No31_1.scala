package cp31_パーサーコンビネーター

import scala.util.parsing.combinator._

/**
 * パーサーコンビネーター
 * サンプル：算術式
 */
object No31_1 extends Arith {
    def main(args: Array[String]) {
        println(parseAll(expr, "2 * (3 + 7)"))
        println(parseAll(expr, "2 * (3 + 7))"))
        println(parseAll(expr, "2 * (3 7)"))

    }
}

class Arith extends JavaTokenParsers {
    def expr: Parser[Any] = term ~ rep("+" ~ term | "-" ~ term)
    def term: Parser[Any] = factor ~ rep("*" ~ factor | "/" ~ factor)
    def factor: Parser[Any] = floatingPointNumber | "(" ~ expr ~ ")"
}
