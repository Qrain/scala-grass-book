package cp30_アクターと並行プログラミング

/*
 * アクターと並行プログラミング
 * アクターコードのスタイル
 */

object No30_5 extends App {

    import scala.actors._, Actor._

    /**
     * アクターはブロックしてはならない
     */
    val sillyActor2 = actor {
        def emoteLater() {
            val mainActor = self //メインアクター
            actor { //ヘルパーアクター
                Thread.sleep(1000) //メインの代わりにブロックする
                mainActor ! "Emote"
            }
        }

        var emoted = 0
        emoteLater()

        loop {
            react {
                case "Emote" =>
                    println("I'm acting!")
                    emoted += 1
                    if (emoted < 5)
                        emoteLater()

                case msg =>
                    println("Received: "+msg)
            }
        }
    }

    sillyActor2 ! "hi there"
    for (i <- 1 to 10) sillyActor2 ! i //処理できている？？

    /**
     * アクターとはメッセージだけを使って通信する
     * アクターから別のアクターへ自身の参照を渡すようなことはしない
     * Ex)
     * act2 ! ("hi", act1)
     *
     * case (msg:String, act:Actor) => act.method1(msg)×
     */

    /**
     * イミュータブルなメッセージを使うようにする
     */

    /**
     * メッセージを自己完結的にする
     * つまりメッセージ送信後の戻り結果を見て、何のメッセージに対する
     * 結果か分からなくなるのではなく、結果メッセージだけで有益(自己完結的)でなければならない。
     */

    import java.net.{ InetAddress, UnknownHostException }
    case class LookupIP(hostname: String, requester: Actor) //メッセージ送信用ケースクラス
    case class LookupResult(name: String, addr: Option[InetAddress]) //メッセージ受信用ケースクラス

    object NameResolver extends Actor {
        def act() {
            loop {
                react {
                    case LookupIP(name: String, actor: Actor) => //自己完結的な返信
                        actor ! LookupResult(name, getIp(name))
                    case (name: String, actor: Actor) => //従来の返信
                        actor ! getIp(name)
                }
            }
        }
        private def getIp(name: String) = try {
            Some(InetAddress.getByName(name))
        } catch {
            case _: UnknownHostException => None
        }
    }
    NameResolver.start()
    NameResolver ! LookupIP("www.yahoo.jp", self)
    println(self.receive { case x => x })
    NameResolver ! LookupIP("yahooOO!!", self)
    println(self.receive { case x => x })
}
