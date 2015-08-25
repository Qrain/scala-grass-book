package cp05_基本型と演算子
/**
 * オブジェクトの等価性について
 */
object No5_7 {

    def main(args: Array[String]) {

        //異なる型同士でも比較できる
        println(1 == 1.0f) //Int型とFloat型の比較
        println(List("Hello") == "Hello") //リストと文字列の比較

        println("Hel"+"lo" == "Hello")

    }
}
