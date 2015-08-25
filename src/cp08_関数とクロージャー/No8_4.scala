package cp08_関数とクロージャー
/*
 * 関数リテラルの短縮形
 */
object No8_4 {

    def main(args: Array[String]) {

        //関数リテラルをより簡潔にすることで、コードの可読性が向上する。
        val list1 = List(103, 7, 0, 18, 85682, 6356, 35)

        //↓は両方とも偶数のみを表示する
        println {
            list1.filter((n: Int) => n % 2 == 0) //関数の引数に明示的に型を指定している
        }

        println {
            list1.filter(n => n % 2 == 0) //型を省略してコンパイラに推論させている。(ターゲットによる型付け)
        }

    }
}
