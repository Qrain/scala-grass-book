package cp08_関数とクロージャー
/*
 * 末尾再帰
 * ※関数の最後の処理として自分自身を直接的に呼び出す関数のこと。
 */
object No8_9 {

    def main(args: Array[String]) {

        //↓は末尾再帰なのでScalaコンパイラによって最適化される。
        tailRecursion(10)

        //↓これらは末尾再帰ではないため、コンパイラによって最適化されない。
        println(isEven(11))
        println(isOdd(11))

        //↓の関数はオブジェクトを介して間接的に自身を呼び出してるため最適化されない。
        nestedFun(20)

    }

    val funValue = nestedFun _ //部分適用された関数オブジェクト
    def nestedFun(x: Int) {
        if (x > 0) {
            println(x)
            funValue(x - 1)
        }
    }

    //互いに呼び出し合う関数(奇数偶数判定)
    def isEven(x: Int): Boolean = if (x == 0) true else isOdd(x - 1)
    def isOdd(x: Int): Boolean = if (x == 0) false else isEven(x - 1)

    //純粋な末尾再帰関数
    def tailRecursion(x: Int) {
        if (x > 0) {
            println(x)
            tailRecursion(x - 1) //←末尾再帰部分
        }
    }
}
