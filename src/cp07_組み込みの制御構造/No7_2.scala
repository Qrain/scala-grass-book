package cp07_組み込みの制御構造
/**
 * 組み込み制御構造
 * while文編
 */
object No7_2 {

    def main(args: Array[String]) {

        //基本的にはJavaのwhileと一緒。

        println(gcdLoop(24, 72))
        println(gcd(24, 72))
        //上記の戻り値は一緒だが、後者はvarを使っておらず再帰的に処理をしている。

        //Scalaのwhile文はUnitを返すので、何らかの副作用が在ると考えられる。
        //ゆえに、できる限り使用は避けた方がいい。Scalaで()はUnit値を表す。
        println((while (false) 1) == ())
    }

    //命令型スタイル(最大公約数)
    def gcdLoop(x: Long, y: Long) = {
        var a = x
        var b = y
        while (a != 0) {
            val tmp = a
            a = b % a
            b = tmp
        }
        b
    }
    //関数型スタイル(最大公約数)
    def gcd(x: Long, y: Long): Long =
        if (y == 0) x else gcd(y, x % y)
}
