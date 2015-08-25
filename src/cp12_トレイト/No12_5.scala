package cp12_トレイト

/*
 * トレイト
 * 積み重ね可能な変更をそれぞれのトレイトで表現する
 * ※クラスに定義されているメソッドに積み重ね的に変更を加える。
 */
object No12_5 {

    def main(args: Array[String]) {
        /**
         * 整数のキューについて考える。
         * クラスには、整数を追加するputと整数を取り出すgetメソッドが定義されている。
         * このクラスに対して以下の変更を加えるトレイトを定義できる。
         * ↓
         * ●double : 待ち行列に追加される全整数を２倍する
         * ●increment : 待ち行列に追加される全整数に１を加える
         * ●filter : 待ち行列に追加される負数を排除する
         */

        println("↓new MyQueue")
        val mq = new MyQueue
        mq.put(15)
        mq.put(9)
        println(mq.get()) //値が追加時の倍になっている
        println(mq.get())

        println("↓new BasicIntQueue with Doubling")
        val queue1 = new BasicIntQueue with Doubling //←こうすることでより直感的にトレイトをミックスインできる
        queue1.put(15)
        queue1.put(9)
        println(queue1.get()) //結果はMyqueue1と同じである
        println(queue1.get())

        println("↓new BasicIntQueue with Incrementing with Filtering")
        val queue2 = new BasicIntQueue with Incrementing with Filtering //負数を排除し１加えるキュー
        //みっくすインされたクラスメソッドを呼び出すと、右から順にトレイトのメソッドが呼び出される。
        //上記の例では、まず負数を排除してから追加要素に１を加える。。
        queue2.put(-1)
        queue2.put(0)
        queue2.put(1)
        println(queue2.get())
        println(queue2.get())
    }
    /*
   * BasicIntQueueを継承しDoublingをミックスインする
   * この時、Doublingのsuper.put(x)呼び出しはBasicIntQueueのput(x)メソッドに動的束縛される。
   */
    class MyQueue extends BasicIntQueue with Doubling
}

abstract class IntQueue { //キューの基礎をなす抽象クラス
    def get(): Int
    def put(x: Int)
}

class BasicIntQueue extends IntQueue { //基本的なキューの実装
    private val buf = new scala.collection.mutable.ArrayBuffer[Int]
    def get() = buf.remove(0) //先頭要素を削除し値を得る(副作用が在るため空括弧を忘れずに…)
    def put(x: Int) = buf += x //整数xを末尾に加える
}

trait Doubling extends IntQueue {
    /*
   * extends IntQueueとすることでミックスインできるクラスを
   * IntQueueのサブクラスに限定している
   */
    //※トレイト内でのsuperは、メソッドを具象定義している他のクラス及びトレイトに動的束縛される。
    //↓の修飾子の組み合わせはトレイトでのみ認められる
    abstract override def put(x: Int) = super.put(x * 2) //このsuperは被ミックスインクラスを示している
}

trait Incrementing extends IntQueue {
    abstract override def put(x: Int) = super.put(x + 1)
}

trait Filtering extends IntQueue {
    abstract override def put(x: Int) = if (x >= 0) super.put(x)
}

