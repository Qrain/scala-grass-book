package cp10_合成と継承

/*
 * 合成と継承
 * 2Dレイアウトライブラリー
 * ※文字列を短型として描画するライブラリを構築するよ。
 */
object No10_x {

    def main(args: Array[String]) {

        //いろいろ定義するおｂ
        //println("AVMN" + "COCOMO")
        //println(List(1, 1, 2, 3) ::: List(5, 8, 13))
        /*
     * ↑のようにレシーバー[T]がパラメータ[T]をとり戻り値[T]を返すメソッドを
     * コンビネーターというらしいよ。
     */

        //文字列の２Dレイアウトクラス使用テスト
        import Element.elem

        val e1 = elem(Array("aaa", "bbb", "ccc", "?", "??"))
        val e2 = elem("This is an only single LINE...")
        val e3 = elem('X', 5, 3)
        //sprintln((e1 above e2)) //高さと幅の自動調整くらい自分で考えてみよっか？
        println(e1 beside e3) //高さと幅の自動調整くらい自分で考えてみよっか？
    }
}

