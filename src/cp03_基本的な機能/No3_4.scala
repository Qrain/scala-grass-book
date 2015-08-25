package cp03_基本的な機能
/**
 * 集合とマップについて
 */
object No3_4 {
    def main(args: Array[String]): Unit = {
        import scala.collection.mutable

        val set_im = Set("A", "B", "C")
        println(set_im+"D"+"E"+"F")

        val set_m = mutable.Set("A", "B", "C")
        set_m += "D"
        set_m += "E"
        set_m += "F"
        println(set_m)

        val map_im = Map(0.1 -> "A", 0.2 -> "B", 0.3 -> "C")
        println(map_im + (0.4 -> "D") + (0.5 -> "E") + (0.6 -> "F"))

        val map_m = mutable.Map(0.1 -> "A", 0.2 -> "B", 0.3 -> "C")
        map_m += 0.4 -> "D"
        map_m += 0.5 -> "E"
        map_m += 0.6 -> "F"
        println(map_m) //どちらも結果順序はバラバラになってしまう

        //以下はどちらもタプル型
        val a = 1 -> "a"
        val b = (1, "a")
    }
}
