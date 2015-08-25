package cp30_アクターと並行プログラミング

/*
 * アクターと並行プログラミング
 * スレッドの再利用によるパフォーマンスの向上
 */

object No30_4 extends App {

  import scala.actors._, Actor._

  object NameResolver extends Actor {
    import java.net.{ InetAddress, UnknownHostException }
    def act() {
      loop {
        react {
          case (name: String, actor: Actor) =>
            actor ! getIp(name)
          //            act()
          //          case "EXIT" => //終了
          //            println("Name resolver exiting.")
          case msg =>
            println("Unhandled message: " + msg)
          //            act()
        }
      }
    }

    def getIp(name: String) = try {
      Some(InetAddress.getByName(name))
    } catch {
      case _: UnknownHostException => None
    }
  }

  NameResolver.start()
  NameResolver ! ("www.yahoo.jp", self)
  println(self.receive { case x => x })
  NameResolver ! ("yahooOO!!", self)
  println(self.receive { case x => x })

}
