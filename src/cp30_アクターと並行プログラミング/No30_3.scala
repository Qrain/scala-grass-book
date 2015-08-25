package cp30_アクターと並行プログラミング

/*
 * アクターと並行プログラミング
 * ネイティブスレッドをアクターとして扱う
 */

object No30_3 extends App {

    import scala.actors._, Actor._

    //アクターは自分自身のために1つ以上のネイティブスレッドを管理している

    println(Thread.currentThread) //自分自身？
    println(self) //scala.actors.Actor 自分自身？

    self ! "Hello, Myself..." //mainスレッド？にメッセージ送信

    println {
        self.receive { //自分宛のメッセージを自分で受信
            case x => x
        }
    }

    self ! "???"
    //↑Stringメッセージのため↓で処理されずに永遠ロック状態に･･･
    println { //そのため永遠でなく1000msだけ待つように指示する
        self.receiveWithin(1000) { //部分関数の定義域を狭めると･･･
            case x: Int => x
        }
    }

}
