package cp15_ケースクラスとパターンマッチ
/*
 * ケースクラスとパターンマッチ
 * Option型
 * ※Some(x)とかNoneとか…１
 */

object No15_6 {
    def main(args: Array[String]) {
        val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")

        println(capitals get "France") //Some(value)が返される
        println(capitals get "North Pole") //対応する値が見付からないのでNoneが返される

        /*
     * ただのStringと比べてSome[String]とした方が、
     * null発生(値なし)の可能性を明示的に示せて良いとされている。
     * 
     * ※Scalaではnullの可能性がある変数をnullチェック無しで使おうとすると型エラーを起こす。
     */
    }
}
