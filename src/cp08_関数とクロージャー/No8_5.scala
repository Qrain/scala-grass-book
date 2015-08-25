package cp08_関数とクロージャー
/*
 * プレースホルダー構文
 */
object No8_5 {

    def main(args: Array[String]) {

        //最大公約数算出ローカル関数
        def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

        //プレースホルダー(用意された場所)構文で、さらにコードの可読性が向上する。
        val list = List(103, 7, 0, 18, 85682, 6356, 35)

        //↓は両方とも偶数のみを表示する
        println {
            list.filter(_ % 2 == 0) //listの各要素が_に展開される。
        }

        val func1 = gcd(_, _) //_にそれぞれ引数が展開される(この場合はローカル関数を使用しているので型は推論される)
        val func2 = (x: Int, y: Int) => gcd(x, y) //↑と同じ
        val func3 = (_: Int) max (_: Int) //この場合は型が推論できないので明示的に指定する必要がある。

        println(func1(24, 68))
        println(func2(24, 68))
        println(func3(24, 68)) //2引数の内大きい方を返す

    }
}
