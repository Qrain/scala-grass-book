package cp30_アクターと並行プログラミング

/*
 * アクターと並行プログラミング
 * アクターとメッセージ交換
 */

object No30_2 extends App {

    import scala.actors._

    //単純なActorの実装
    object SillyActor extends Actor { //Actorクラスを継承する
        def act() {
            for (i <- 1 to 5) {
                println("1: I'm acting!")
                Thread.sleep(1000)
            }
        }
    }

    object SeriousActor extends Actor { //第二Actor
        def act() {
            for (i <- 1 to 5) {
                println("2: To be or not to be.")
                Thread.sleep(1000)
            }
        }
    }
    SillyActor.start() //←でアクターを起動
    SeriousActor.start() //↑アクターと同時並行で実行
    for (i <- 1 to 10) {
        println("sending: "+i)
        SeriousActor ! i
    }

    import Actor._
    //別の方法でActorを実装
    val seriousActor2 = actor {
        for (i <- 1 to 5) {
            println("3: That is the question.")
            Thread.sleep(1000)
        }
    }
    //上記方法での実装はstartメソッド呼ばなくても実行される

    val echoActor = actor {
        while (true) { //強制ループ
            receive { //def receive[A](f: PartialFunction[Any,A]): A
                //↓部分関数 (処理型範囲:Any 処理:オブジェクトのtoStringを表示 )
                case msg => println("received message: "+msg)
            }
        }
    }

    val partial: AnyVal => Unit = {
        case v => println("Value is "+v)
    }

    echoActor ! "Hello, Actor!!!" //メッセージ送信
    echoActor ! 1192 //メッセージ送信
    echoActor ! echoActor.getClass //メッセージ送信

    val intActor = actor { //Int型のみ処理するActor
        receive { //def receive[A](f: PartialFunction[Any,A]): A
            //↓部分関数 (処理型範囲:Int 処理:整数を表示 )
            case x: Int => println("Got an Int: "+x)
        }
    }

    intActor ! "hello"
    intActor ! System.currentTimeMillis
    intActor ! 1192

}
