package cp05_基本型と演算子
/**
 * 演算子はメソッドなのだ！
 */
object No5_3 {

    def main(args: Array[String]) {

        //以下同意
        println(1 + 2)
        println((1).+(2))

        //中置演算子について
        val str = "Scala is Heaven..."
        println(str indexOf 'H')

        //前置演算子について
        val m1 = -1.05
        val m2 = (1.05).unary_-
        assert(m1 == m2) //同じ意味
        assert(~0x5F == 0x5F.unary_~) //同じ意味(ビット反転)
        //前置演算子として使える記号は、+、-、!、~のみである。

        //後置演算子について
        println("SaMpLe".toLowerCase())
        println("SaMpLe" toLowerCase) //ドットを付けないことで後置演算子と化す
        /**
         * 一般に戻り値がUnit型(副作用アリ)の場合は、メソッド名()のように空括弧をつけ
         * それ以外の副作用なしの時は、メソッド名のみで表記する。
         */
    }
}
