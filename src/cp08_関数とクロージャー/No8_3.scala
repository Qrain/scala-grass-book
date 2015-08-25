package cp08_関数とクロージャー
/*
 * ファーストクラス関数(first-class functions)
 */
object No8_3 {

    def main(args: Array[String]) {

        //関数リテラルは変数へ格納することができる。
        val func1 = (x: Int) => x % 10 //1の位を返す関数
        val list1 = List(103, 7, 0, 18, 85682, 6356, 35)

        println {
            list1.map(func1(_))
        }

        println {
            list1.map(n => n % 10) //引数に関数リテラルを指定してる
        }

    }
}
