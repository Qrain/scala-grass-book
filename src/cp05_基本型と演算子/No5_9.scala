package cp05_基本型と演算子
/*
 * リッチラッパー
 */
object No5_9 {

    def main(args: Array[String]) {

        //リッチラッパークラスが基本型に提供するメソッド例
        println(1 max 7) //左右のオペランドの内大きいほうを返す
        println(0.54 min 0.56) //左右のオペランドの内小さいほうを返す
        println(-10 abs) //絶対値を浮動小数点数で返す
        println(2.5 round) //値を四捨五入する
        println(2D.isInfinity) //
        println((2D / 0).isInfinity) //
        println(5 to 15) //5から15までのRangeオブジェクトを返す
        println("orange" capitalize) //頭文字を大文字に変換した文字列を返す
        println("Think" drop 2 capitalize) //Inkにする

    }

}