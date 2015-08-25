package cp12_トレイト

/*
 * トレイト
 * トレイトの仕組み
 * ※トレイトの２大用途とは、
 * シンインターフェース⇒リッチインターフェース化と
 * クラスへの積み重ね可能な変更である。
 */
object No12_1 {
    def main(args: Array[String]) {
        val frog = new Frog
        val phil: Philosophical = new Frog //Javaのインターフェース的に型としても利用できる

        val xxx = new Philosophical {}
        xxx.philosophize
    }
}

class Frog extends Philosophical {
    /*
     * クラスFrogはPhilosophicalをミックスインしている
     * と同時にPhilosophicalが自動継承しているAnyRefを継承している。
     */
    override def toString = "green"
}

trait Philosophical {
    /*
   * traitの定義
   * traitもclass同様デフォルトでAnyRefを継承する
   */
    def philosophize() = println("I consume memory, therefore I am!")
}
