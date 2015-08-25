package cp11_Scalaの階層構造

/*
 * Scalaの階層構造
 * 最下位の2つの型
 * ※
 */
object No11_3 {
    def main(args: Array[String]) {
        /*
     * 正確には、AnyRefを継承する全型のサブクラスとしてscala.Nullがあり
     * Scalaの型階層の最下位としてscala.Nothingが定義されている。
     */
        val n: Int = "null".toInt //null //IntはAnyRefではなくAnyValを継承しているためnullで初期化できない
        val l: List[Int] = null //ListはAnyRefを継承しているのでサブ型であるnullで初期化できる
    }

    //errorの戻り値Nothingは全クラスのサブクラスなのでInt型に入れることが出来る
    def aaa(x: Int, y: Int): Int =
        if (y != 0)
            x / y
        else
            error("0で割ることは出来ません...")
}

