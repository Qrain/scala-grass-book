package cp09_制御と抽象化
/*
 * カリー化
 * ※複数のパラメータリストをとる関数
 */
object No9_3 {

    def main(args: Array[String]) {

        //戻り値は同じだが引数の渡し方が異なる
        println(oldSum(5, 10))
        println(curriedSum(5)(10))

        //カリー化された関数から実際に↓のsecondにあたる第二関数を得る方法
        val onePlus = curriedSum(1)(_) //お馴染みのプレースホルダーを使う(※_を囲う括弧は省略できる)
        val twoPlus = curriedSum(2)_ //ね？省略できるでしょ・・・？

        println(onePlus(5))
        println(twoPlus(5))
    }
    //2整数の加算関数
    def oldSum(x: Int, y: Int) = x + y //従来のヤツ
    def curriedSum(x: Int)(y: Int) = x + y //カリー化したヤツ

    //カリー化した関数の実行プロセスを表現したもの

    def first(x: Int) = //curriedSumの呼び出しが最初に行うであろう処理
        (y: Int) => x + y //1整数をとるクロージャーを返す
    val second = first(5) //次に行う処理
    val result = second(10) //最後に結果値が生成される

}

