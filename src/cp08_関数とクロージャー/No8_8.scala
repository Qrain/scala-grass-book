package cp08_関数とクロージャー
/*
 * 連続パラメーター
 * ※関数への最後の引数を繰り返したい時に可変長引数として連続パラメータを指定できる。
 */
object No8_8 {

    def main(args: Array[String]) {

        val ary = Array("a", "bb", "ccc", "dddd")

        echo("a", "bb", "ccc", "dddd") //任意の個数の引数に関数を適用できる
        echo(ary: _*) //aryの各要素を引数として渡す構文
    }

    //連続パラメータを指定できる関数
    def echo(args: String*) = args foreach println

}
