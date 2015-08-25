package cp03_基本的な機能

/*
 * リストについて
 */
object No3_2 {

    def main(args: Array[String]): Unit = {

        //:: 末尾がコロンのメソッドは右被演算子であり↓の様になる
        val ls1 = "凛" :: "描" :: "灯" :: Nil
        val ls2 = "凛" :: "描" :: "灯" :: List()
        val ls3 = Nil.::("灯").::("描").::("凛")

        //結果を比較する(結果は全て同じ！)
        ls1 foreach println
        ls2 foreach println
        ls3 foreach println

    }

}
