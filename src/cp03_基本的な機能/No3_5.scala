package cp03_基本的な機能

/**
 * 命令型と関数型の違いは？
 */
object No3_5 {

    def main(args: Array[String]): Unit = {
        /**
         * varばっか使われていれば、恐らく命令型。
         * valのみであれば、それは関数型スタイル。
         *
         * そして戻り値がUnit(戻り値なし)だと、何らかの副作用があるので
         * 関数型スタイルで書く場合は最小限にしなければならない。
         *
         */

        val tmp = List(1, 64, 24, 4, 37, 77, 9)

        println("副作用アリ")
        sideEffect(tmp) //メソッドで表示も行う

        println("\n副作用なし")
        nonSideEffect(tmp) foreach println //結果を戻り地として返す

        //上記は表示結果としてはまったく同じだが、簡潔性や参照透明性を考えると後者の方がより賢明だ。
    }

    //副作用アリ
    def sideEffect(ls: List[Int]): Unit = {
        var i = 0
        while (i < ls.size) {
            println(ls(i) * ls(i))
            i += 1
        }
    }

    //副作用なし
    def nonSideEffect(ls: List[Int]): List[Int] = ls.map(n => n * n)

}
