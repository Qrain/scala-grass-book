package cp05_基本型と演算子
/**
 * Scalaの基本型(プリミティブ型)たち
 */
object No5_1 {

    def main(args: Array[String]) {

        val byte: Byte = 100 //8ビット
        val short: Short = 10000 //16ビット
        val int = 1000000 //32ビット
        val long = 100000000L //64ビット
        val char = '\n' //2バイトUnicode
        val string = "XYZ" * 5 //Char型のシーケンス
        val float = 0.05f
        val double = 3.14
        val bool = false
        /**
         * Scalaコンパイラはバイトコード生成時に
         * Scalaの数値型をJavaプリミティブ型へと変換する。
         */
    }
}
