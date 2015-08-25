package cp08_関数とクロージャー
/*
 * クロージャー
 * ※自由変数を束縛することで、関数リテラルから作成される値をクロージャーという。
 */
object No8_7 {

    def main(args: Array[String]) {

        /*
     * クロージャー ⇒ スコープ外での自由変数への変更
     */
        var more = 10 //束縛変数に対して自由変数という
        //クロージャー生成
        val addMore = (_: Int) + more //この時、プレースホルダーの_を束縛変数という

        println(addMore(15))

        more = 100 //ここで変更
        println(addMore(15)) //自由変数moreに対する変更が反映されている

        /*
     * クロージャーのスコープ外 ⇒ クロージャーによる自由変数への変更
     */
        val numbers = List(-12, 5, 23, 7, -5, -30, 9)
        var sum = 0

        numbers.foreach(sum += _) //sumへ変更を加える関数リテラル
        println(sum) //変更が反映されている

        //moreが生きてるのは当たり前の気がするが・・・何なんだ？
        val inc1 = makeIncreaser(1)
        val inc100 = makeIncreaser(100)
        println(inc1(11))
        println(inc100(11))

    }

    //クロージャーを生成する関数
    def makeIncreaser(more: Int) = (_: Int) + more
}
