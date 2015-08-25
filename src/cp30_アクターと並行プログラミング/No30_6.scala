package cp30_アクターと並行プログラミング
import scala.actors._, Actor._
/**
 * アクターと並行プログラミング
 * 大規模なサンプル：並列離散イベントシミュレーション
 */
object No30_6 {
    def main(args: Array[String]) {
        import _6_3._
        //Demo
        val circuit = new Circuit with Adders
        import circuit._
        val ain = new Wire("入力a", true)
        val bin = new Wire("入力b", false)
        val cin = new Wire("入力c", true)
        val sout = new Wire("和")
        val cout = new Wire("桁上")
        //↓各配線の変化を監視する
        probe(ain)
        probe(bin)
        probe(cin)
        probe(sout)
        probe(cout)

        //↓全加算器をシミュレートする
        fullAdder(ain, bin, cin, sout, cout)
        //  andGate(ain, bin, cin)
        circuit.start()
    }
}
/**
 * 30.6.3
 * 回路シミュレーションの実装
 */
package _6_3 {
    ///////////////////////// -- start of package -- /////////////////////////
    import _6_2._

    //↓回路を拡張した加算器トレイト
    trait Adders extends Circuit {
        //↓半加算器
        def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) {
            val d, e = new Wire
            orGate(a, b, d)
            andGate(a, b, c)
            inverter(c, e)
            andGate(d, e, s)
        }
        //↓全加算器
        def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) {
            val s, c1, c2 = new Wire
            halfAdder(a, cin, s, c1)
            halfAdder(b, s, sum, c2)
            orGate(c1, c2, cout)
        }
    }

    //↓回路を表すクラス
    class Circuit {
        //↓シミュレーション管理クロック
        val clock = new Clock
        //↓シミュレーションメッセージ

        //↓ディレイ定数(信号伝播による遅延)
        val WireDelay = 1
        val InverterDelay = 2
        val OrGateDelay = 3
        val AndGateDelay = 3

        //↓Wire(配線),Gate(論理回路)クラスとメソッド
        class Wire(name: String, init: Boolean) extends Simulant {
            //↓コンストラクタのオーバーロード
            def this(name: String) = this(name, false)
            def this() = this("unnamed")

            //↓外部クラス(Circuit)のクロックを利用
            val clock = Circuit.this.clock
            clock.add(this)
            //↓シグナル値
            private var sigVal = init
            //↓接続中ゲートリスト
            private var observers: List[Actor] = Nil
            //↓受信メッセージの処理
            def handleSimMessage(msg: Any) {
                msg match {
                    //↓接続ゲートの出力状態が変化した
                    case SetSignal(s) =>
                        if (s != sigVal) { //←現在の信号と違うなら設定⇒伝播
                            sigVal = s
                            signalObservers()
                        }
                }
            }
            //↓信号変化を接続ゲートへ伝播する
            def signalObservers() {
                for (obs <- observers)
                    clock ! AfterDelay(WireDelay, SignalChanged(this, sigVal), obs)
            }
            //↓ゲート追加
            def addObserver(obs: Actor) {
                observers = obs :: observers
            }
            //↓初期化メソッドの内容定義
            override def simStarting() = signalObservers()
            //↓toString()再定義
            override def toString() = "Wire(" + name + ")"

        }

        //↓And,Or,Notゲートの共通機能を定義した抽象クラス
        abstract class Gate(in1: Wire, in2: Wire, out: Wire) extends Simulant {
            //↑クラスパラメータを取るという点でトレイトではなく抽象クラス
            //↓サブクラスで具象定義すべき抽象メンバ
            val delay: Int
            def computeOutput(s1: Boolean, s2: Boolean): Boolean
            //↓外部クラス(Circuit)のクロックを利用
            val clock = Circuit.this.clock
            clock.add(this)
            in1.addObserver(this) //←配線1にゲートを接続する
            in2.addObserver(this) //←配線2にゲートを接続する

            //↓各配線のシグナル値保存用
            var s1, s2 = false

            //↓受信メッセージの処理
            def handleSimMessage(msg: Any) {
                msg match {
                    //↓入力線のシグナル値が変化した
                    case SignalChanged(w, s) =>
                        if (w == in1)
                            s1 = s
                        if (w == in2)
                            s2 = s
                        //遅延時間後に出力線へ再計算値を伝播する
                        clock ! AfterDelay(delay, SetSignal(computeOutput(s1, s2)), out)
                }
            }
        }

        private object DummyWire extends Wire("dummy") //←Notゲート用のダミー入力線

        //↓その他ユーティリティメソッド
        def orGate(in1: Wire, in2: Wire, out: Wire) =
            new Gate(in1, in2, out) {
                val delay = OrGateDelay
                def computeOutput(s1: Boolean, s2: Boolean) = s1 || s2
            }

        def andGate(in1: Wire, in2: Wire, out: Wire) =
            new Gate(in1, in2, out) {
                val delay = AndGateDelay
                def computeOutput(s1: Boolean, s2: Boolean) = s1 && s2
            }

        def inverter(in: Wire, out: Wire) =
            new Gate(in, DummyWire, out) {
                val delay = InverterDelay
                def computeOutput(s1: Boolean, ignored: Boolean) = !s1
            }

        def probe(wire: Wire) = new Simulant {
            val clock = Circuit.this.clock
            clock.add(this)
            wire.addObserver(this) //←監視対象配線へ自身(監視回路)を接続
            //↓受信メッセージの処理
            def handleSimMessage(msg: Any) {
                msg match {
                    //↓入力線のシグナル値が変化した
                    case SignalChanged(w, s) =>
                        //↓監視対象の配線状態を表示
                        println(w + "の信号が変わりました: " + (if (s) "1" else "0"))
                }
            }
        }

        //↓開始する
        def start() {
            clock ! Start
        }

        //↓（ゲート出力状態(変化) ⇒ 出力線）用のメッセージ
        case class SetSignal(sig: Boolean)
        //↓（入力線状態(変化) ⇒ ゲート入力）用のメッセージ
        case class SignalChanged(wire: Wire, sig: Boolean)
    }
    ///////////////////////// -- end of package -- /////////////////////////
}

/**
 * 30.6.1
 * 全体設計
 */
package _6_1 {
    //部品オブジェクトが拡張するトレイト
    trait Simulant extends Actor

    class Wire extends Simulant { //配線
        def act() {}
    }
    case class Ping(time: Int) //先行可能か確認のため送るメッセージ
    case class Pong(time: Int, from: Actor) //Pingメッセージに対する応答メッセージ

    //作業表及び進捗を管理するClockアクター
    class Clock extends Actor {
        private var running = false
        private var currentTime = 0
        private var agenda: List[WorkItem] = Nil
        def act() {}
    }

    //作業項目
    case class WorkItem(time: Int, msg: Any, target: Actor)
    //次の項目？
    case class AfterDelay(delay: Int, msg: Any, target: Actor)
    case object Start //開始メッセージオブジェクト
    case object Stop //終了メッセージオブジェクト
}

/**
 * 30.6.2
 * シミュレーションフレームワークの実装
 */
package _6_2 {
    //↓部品オブジェクトが拡張するトレイト
    trait Simulant extends Actor {

        //↓抽象クロック
        val clock: Clock
        //↓抽象メッセージ処理メソッド
        def handleSimMessage(msg: Any)
        //↓シミュレーション開始時の初期化メソッド
        def simStarting() {}

        def act() {
            loop {
                react {
                    //↓終了メッセージを受信したらアクターを終了する
                    case Stop => exit()
                    //↓
                    case Ping(time) =>
                        if (time == 1) simStarting()
                        clock ! Pong(time, self)
                    //↓
                    case msg => handleSimMessage(msg)
                }
            }
        }

        start() //←作成と同時にアクターを開始する
    }

    //↓Clockクラスを実装する
    class Clock extends Actor {
        //↓クロック起動フラグ
        private var running = false
        //↓現在の時間
        private var currentTime = 0
        //↓作業表
        private var agenda: List[WorkItem] = Nil
        //↓全部品オブジェクトリスト
        private var allSimulants: List[Actor] = Nil
        //↓現在処理中の部品オブジェクト集合(空にならなければタイムチックは先行しない)
        private var busySimulants: Set[Actor] = Set.empty

        start() //←作成と同時にアクターを開始する

        def act() {
            loop {
                if (running && busySimulants.isEmpty)
                    advance()
                reactToOneMessage()
            }
        }

        //↓タイムチックを先に進める
        def advance() {
            if (agenda.isEmpty && currentTime > 0) {
                println("/作業表状態: 空　/動作: クロック終了　/時間: " + currentTime)
                self ! Stop //←自身にStopメッセージを送信し終了させる
                return
            }
            currentTime += 1
            println("/動作: 次へ進める　/時間: " + currentTime)
            processCurrentEvents() //←現在のイベントを部品オブジェに処理させる
            for (sim <- allSimulants)
                sim ! Ping(currentTime)
            busySimulants = Set.empty ++ allSimulants
        }

        private def processCurrentEvents() {
            //↓作業表から処理すべき作業を選択する
            val todoNow = agenda.takeWhile(_.time <= currentTime)
            //↓処理すべき作業を取り除いた作業表を再設定する
            agenda = agenda.drop(todoNow.length)
            //↓各作業を対象の部品オブジェクトに処理させる(メッセージ送信)
            for (WorkItem(time, msg, target) <- todoNow) {
                assert(time == currentTime) //←ロジックチェック用
                target ! msg
            }
        }

        def reactToOneMessage() {
            react {
                //↓新しい作業を作業表へ追加する
                case AfterDelay(delay, msg, target) =>
                    val item = WorkItem(currentTime + delay, msg, target)
                    agenda = insert(agenda, item)

                //↓作業終了を伝える部品オブジェクトからの応答
                case Pong(time, sim) =>
                    assert(time == currentTime)
                    assert(busySimulants contains sim) //←作業中の部品オブジェクトからの応答だったら
                    //↓作業中の部品オブジェクト集合から処理終了オブジェクトを消す
                    busySimulants -= sim

                //↓クロックをスタートさせる
                case Start =>
                    println("シミュレーション開始")
                    running = true

                //↓クロックをストップさせる
                case Stop =>
                    for (sim <- allSimulants)
                        sim ! Stop //←部品オブジェに終了メッセージを送信
                    exit() //←アクター終了
            }
        }

        //↓作業表のソート状態(時間がキー)を保つように作業項目を追加する
        private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] =
            if (ag.isEmpty || item.time < ag.head.time) //
                item :: ag
            else
                ag.head :: insert(ag.tail, item) //←末尾再帰？？

        //↓部品オブジェクトを追加する
        def add(sim: Simulant) {
            allSimulants = sim :: allSimulants
            //      allSimulants ::= sim
        }
    }

    //↓作業項目
    case class WorkItem(time: Int, msg: Any, target: Actor)
    //↓delay時間後に実行されるべき新しい作業項目
    case class AfterDelay(delay: Int, msg: Any, target: Actor)
    //↓先行可能か確認のため送るメッセージ
    case class Ping(time: Int)
    //↓Pingメッセージに対する応答メッセージ
    case class Pong(time: Int, from: Actor)
    //↓開始メッセージオブジェクト
    case object Start
    //↓終了メッセージオブジェクト
    case object Stop
}
