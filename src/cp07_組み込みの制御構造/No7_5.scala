package cp07_組み込みの制御構造
/*
 * 組み込み制御構造
 * match式編
 */
object No7_5 {

    def main(args: Array[String]) {

        //超簡単なmatch式サンプル(文字列を返す)
        val friend = (if (args.nonEmpty) args.head else " ") match {
            case "salt"  => "pepper"
            case "chips" => "salsa"
            case "eggs"  => "bacon"
            case _       => "huh?"
        }

        println(friend)

    }
}