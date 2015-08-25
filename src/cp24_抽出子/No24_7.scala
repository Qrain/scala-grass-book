package cp24_抽出子

/*
 * 抽出子
 * 正規表現
 */

object No24_7 extends App {

  val Decimal = """(-)?(\d+)(\.\d*)?""".r
  //↑先頭に(-)がオプションで次に(1桁以上の数値)が、最後に(ドットと0桁以上の数値)がオプションで

  val input = "for -1.0 to 99 by 3"

  for (s <- Decimal findAllIn input) println(s)
  for (s <- Decimal findFirstIn input) println(s)
  for (s <- Decimal findPrefixOf input) println(s)

  //Scalaの正規表現は抽出子を利用して文字列を分解できる
  val Decimal(sign, integerpart, decimalpart) = "-1.23"
  println(sign + ":" + integerpart + ":" + decimalpart)

}

