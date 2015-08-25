package cp11_Scalaの階層構造

/*
 * Scalaの階層構造
 * プリミティブ型の実装方法
 * ※
 */
object No11_2 {

    def main(args: Array[String]) {

        /*
     * ボクシングとはBoxing...つまり値型を参照型へ自動変換する仕組みである
     * プリミティブ型である"1"という整数を、箱に入れる(ラップする)ことで
     * 参照型へと変換する。
     */

        //==はJavaと違って参照等価ではなく内容比較をするようオーバーライドされてることが多い
        println(List(1) == List(1))
        //一方のeqメソッドは、純粋にJavaの==と同じ参照等価として働く。
        println(List(1) eq List(1))
    }

}

