package cp04_クラスとオブジェクト
/**
 * Scalaアプリケーション
 */
object No4_4 {
    //このmainメソッドがプログラムのエントリーポイントになるのです。
    def main(args: Array[String]) {
        //↓全部同じ
        println("Hi, Jhon!")
        Predef.println("Hi, Jhon!")
        Console.println("Hi, Jhon!")

        Console.err.println("Error??") //コンソール上で赤文字になる
        Predef.error("Error??") //例外を投げる
    }
}
