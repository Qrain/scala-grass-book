package cp05_基本型と演算子
/*
 * 演算子結合順序
 */
object No5_8 {

    def main(args: Array[String]) {
        val a = List(1, 2, 3)
        val b = List(4, 5, 6)
        println(a ::: b) //左被演算子
        println(b.:::(a))
        println(a ++ b)
        println(a.++(b))

        { val x = a; b.:::(a) } //被演算子は常に左から評価される
        //↑の様にすれば先にaが評価される

        a ::: b ::: a ::: b //実行順序は右から左へ
        b.:::(a).:::(b).:::(a) //通常表記するとこうなる
        a ::: (b ::: (a ::: b)) //()を付けると実行順序が明確になる   
        //上記全て同意
    }
}