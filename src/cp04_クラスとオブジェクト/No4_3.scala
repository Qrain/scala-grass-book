package cp04_クラスとオブジェクト
/**
 * シングルトンオブジェクトと
 * コンパニオンオブジェクトについて
 */
object No4_3 {
    def main(args: Array[String]) {
        println(ChecksumAccumulator.calculate("Yes, fallin' Love!!"))
        println(ChecksumAccumulator.calculate("No, gettin' Love..."))
        println(ChecksumAccumulator.calculate("Yes, fallin' Love!!"))
    }
}

/**
 * クラスChecksumAccumulatorのコンパニオンオブジェクト
 */
object ChecksumAccumulator {
    /*
     * シングルトンオブジェクト内のメンバは静的である。
     * シングルトンオブジェクトはパラメータ(クラスでいうコンストラクタ引数)を受け取らない。
     * というより、newキーワードでインスタンス化しないのでパラメータを指定できない。
     * シングルトンオブジェクトには型が定義されておらず、コンパニオンクラスがあって初めて型が定義される。
     */
    private val cache = scala.collection.mutable.Map[String, Int]()

    def calculate(s: String): Int = {
        if (cache.contains(s))
            cache(s)
        else {
            val acc = new ChecksumAccumulator
            for (c <- s) //文字列を逐次Byteへ変換しaddメソッドの引数とする
                acc.add(c.toByte)
            val cs = acc.checksum()
            cache += s -> cs //キャッシュへキーと値を追加する
            cs
        }
    }
}

/**
 * コンパニオンクラス
 */
class ChecksumAccumulator {
    private var sum = 0 //インスタンス変数(各インスタンス固有の変数)
    def add(b: Byte) = sum += b
    def checksum() = ~(sum & 0xFF) + 1
}
