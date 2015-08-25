package cp30_アクターと並行プログラミング
import scala.actors._
import Actor._

import scala.collection.mutable.ListBuffer

/**
 * アクターと並行プログラミング
 * ライフゲームをアクターで実装してみよう
 * @author TAKASHI
 */
object No30_x {
    def main(args: Array[String]) {
        import simulants._
        //Demo
        //
        //  println(a diff b)
        //  println(b diff a)
        println("AA")
        val life = new LifeGameX
        //        import life._
        life.initialize()
    }
}

/**
 * さいきんさいきんさいきん！！
 */
package simulants {
    ///////////////////////// -- start of package -- /////////////////////////
    //    import framework._
    //    import javax.swing.JToggleButton

    class LifeGameX {

        //重複がないかチェック用
        //    var allCellsPoints: List[(Int, Int)] = Nil
        //↓横幅
        var w = 3
        //↓高さ
        var h = 3
        //↓DQマップモード
        var dqMapMode = false
        //↓生成済みセルの座標リスト
        //    var cellPoints: List[(Int, Int)] = Nil

        //↓生成済みセルリスト
        private var newedCellsList: List[Cell] = Nil

        //↓種の起源座標
        private val S = (1, 1)

        def initialize() {
            new Cell(S)
            newedCellsList.filter(cell =>
                cell.point == (1, 1)
                    || cell.point == (2, 1)
                    || cell.point == (3, 1)
                    || cell.point == (1, 2)
                    || cell.point == (2, 3)).foreach(_.isAlive = true)
            newedCellsList foreach (_.start())
        }

        class Cell(val point: (Int, Int)) extends Actor {
            //世代管理変数
            private var gen = 0

            //↓死亡フラグ
            //      private
            var isAlive = false
            //↓死んでるムーア近傍セル数
            private var mooreAliveCount = 0
            private var mooreDeadCount = 0
            //↓ムーア近傍セルリスト
            private var mooreCells: List[Cell] =
                if (newedCellsList.size < w * h) {
                    //生成されたら外クラスの座標リストへ追加
                    newedCellsList ::= this
                    getMooreCells(point)
                } else {
                    this.exit()
                    //                    println("Generating is over...@"+this)
                    Nil
                }

            private def getMooreCells(p: (Int, Int)): List[Cell] = {
                val needPoints = //座標pに必要なムーア近傍セル座標リスト
                    if (p == (1, 1)) //左上
                        (2, 1) ::
                            (2, 2) ::
                            (1, 2) :: Nil
                    else if (p == (w, 1)) //右上
                        (w - 1, 1) ::
                            (w - 1, 2) ::
                            (w, 2) :: Nil
                    else if (p == (1, h)) //左下
                        (1, h - 1) ::
                            (2, h - 1) ::
                            (2, h) :: Nil
                    else if (p == (w, h)) //右下
                        (w - 1, h) ::
                            (w - 1, h - 1) ::
                            (w, h - 1) :: Nil
                    else if (p._1 == 1) //左辺
                        (1, p._2 - 1) ::
                            (2, p._2 - 1) ::
                            (2, p._2) ::
                            (2, p._2 + 1) ::
                            (1, p._2 + 1) :: Nil
                    else if (p._1 == w) //右辺
                        (w, p._2 - 1) ::
                            (w - 1, p._2 - 1) ::
                            (w - 1, p._2) ::
                            (w - 1, p._2 + 1) ::
                            (w, p._2 + 1) :: Nil
                    else if (p._2 == 1) //上辺
                        (p._1 - 1, 1) ::
                            (p._1 - 1, 2) ::
                            (p._1, 2) ::
                            (p._1 + 1, 2) ::
                            (p._1 + 1, 1) :: Nil
                    else if (p._2 == h) //下辺
                        (p._1 - 1, h) ::
                            (p._1 - 1, h - 1) ::
                            (p._1, h - 1) ::
                            (p._1 + 1, h - 1) ::
                            (p._1 + 1, h) :: Nil
                    else //上記以外近傍接セル数8の時
                        (p._1 - 1, p._2 - 1) ::
                            (p._1, p._2 - 1) ::
                            (p._1 + 1, p._2 - 1) ::
                            (p._1 - 1, p._2) ::
                            (p._1 + 1, p._2) ::
                            (p._1 - 1, p._2 + 1) ::
                            (p._1, p._2 + 1) ::
                            (p._1 + 1, p._2 + 1) :: Nil

                //既成セルリストと新規セルリストの差分
                //        val newPoints = newedCellsList.map(_.point) diff needPoints
                //生成すべきポイント
                val newPoints = needPoints diff newedCellsList.map(_.point)
                //生成せず参照すべきポイント
                val linkPoints = needPoints diff newPoints
                //needPoints.fi
                //新規セルリスト
                val newCells = newPoints.map(new Cell(_))
                //参照セルリスト
                val linkCells = linkPoints.map(p => newedCellsList.find(_.point == p).get)
                newCells ++ linkCells
            }
            //連鎖的にM近傍セルの生成⇒追加を繰り返す

            private var done = false
            private var isSync = true //同期状態か否か

            //初期処理は通常のメソッド呼び出しでも可能なので
            def act() {
                loop {
                    //世代変数による同期確認
                    //          def isSynchronic = !mooreCells.exists(_.gen != gen)

                    //ムーア近傍セルへ生死状態を聴く
                    if (!done && isSync) {
                        println(gen + "\t" + point + (if (isAlive) "●" else "○"))
                        mooreCells.foreach(_ ! TellMeState(self))
                        done = true
                    }

                    react {
                        case TellMeState(sender) =>
                            //              println("TellMeState!!")
                            //              if (isAlive)
                            //                sender ! MyState(ImAlive)
                            //              else
                            //                sender ! MyState(ImDead)
                            //                            if (isAlive)
                            sender ! MyStateXx(self, isAlive, gen)
                        //                            else
                        //                                sender ! MyStateX(self, ImDead, gen)

                        //M近傍セルを受信したら死亡カウンタをインクリメント
                        case MyStateXx(sender, alive, g) =>
                            //死にたいのに、なんだ、この性欲は！！？？息子は死にたくないのか･･･
                            if (g == gen) { //送信者の世代と同じ(同期)ならば
                                if (alive) {
                                    mooreAliveCount += 1
                                } else {
                                    mooreDeadCount += 1
                                }
                            } else if (g < gen) { //同じでなければ送信者に対してメッセージ送信
                                sender ! "先行ヲ願ウ"
                            } else {
                                sender ! "待機ヲ願ウ"
                            }

                            if (mooreAliveCount + mooreDeadCount == mooreCells.size) {
                                decideDeadOrAlive()
                                mooreAliveCount = 0
                                mooreDeadCount = 0
                            }

                            if (g == gen) { //同期が必要な中枢処理

                                //世代を進める
                                if (gen < 10) gen += 1 else exit()

                                done = false
                            }
                        //M近傍セルを受信したら死亡カウンタをインクリメント
                        //                        case MyState(state) =>
                        //                            if (state == ImDead)
                        //                                mooreDeadCount += 1
                        //                            if (state == ImAlive)
                        //                                mooreAliveCount += 1
                        //                            decideDeadOrAlive()
                        case msg =>
                    }
                }

                /**
                 * 生死判断
                 */
                def decideDeadOrAlive() {
                    println("Deciding MMMMMMMMMMMMMMMmmmmmmmmmmmm");
                    if (!isAlive && mooreAliveCount == 3) {
                        //誕生
                        //    死んでいるセルに隣接する生きたセルがちょうど3つあれば、次の世代が誕生する。
                        isAlive = true
                    } else if (isAlive && mooreAliveCount == 2 || mooreAliveCount == 3) {
                        //維持
                        //    生きているセルに隣接する生きたセルが2つか3つならば、次の世代でも生存する。

                    } else if (isAlive && mooreAliveCount <= 1 || mooreAliveCount >= 4) {
                        //死滅
                        //過疎: 生きているセルに隣接する生きたセルが1つ以下ならば、過疎により死滅する。
                        //過密: 生きているセルに隣接する生きたセルが4つ以上ならば、過密により死滅する。
                        isAlive = false
                    }
                }
            }

            override def toString = "Cell" + point + "\t" * 2 + "M近傍Cell: " +
                mooreCells.map(_.point).mkString(", ")
        }
        //【セルの生死は次のルールに従う】
        //誕生
        //    死んでいるセルに隣接する生きたセルがちょうど3つあれば、次の世代が誕生する。
        //生存
        //    生きているセルに隣接する生きたセルが2つか3つならば、次の世代でも生存する。
        //過疎
        //    生きているセルに隣接する生きたセルが1つ以下ならば、過疎により死滅する。
        //過密
        //    生きているセルに隣接する生きたセルが4つ以上ならば、過密により死滅する。

        trait State
        //↓死亡を知らせるメッセージ
        case object ImDead extends State
        //↓生存を知らせるメッセージ
        case object ImAlive extends State
        //↓状態を知らせるメッセージ
        case class MyState(state: State)
        //↓状態を知らせるメッセージ
        case class MyStateX(sender: Actor, state: State, gen: Int)
        //↓状態を知らせるメッセージ
        case class MyStateXx(sender: Actor, alive: Boolean, gen: Int)

        case class TellMeState(sender: Actor)
        //↓先行可能か確認のため送るメッセージ
        case class Ping(time: Int)
        //↓Pingメッセージに対する応答メッセージ
        case class Pong(time: Int, from: Actor)
        //↓自身の登録を依頼するメッセージ
        case class AddMe(me: Cell, moore: List[Cell]) //誰に送るのか？どうやって送るのか？
        //↓追加すべきムーア近傍セルを渡す
        case class YourMoore(moore: List[Cell])
    }

    //セルの次世代状態に必要な情報はムーア近傍接セルの生死のみである
    //    class LifeGame {
    //
    //        //↓横幅
    //        var w = 10
    //        //↓高さ
    //        var h = 10
    //        //↓DQマップモード
    //        var dqMapMode = false
    //
    //        private val clock = new Clock
    //        //種の起源座標
    //        private val S = (1, 1)
    //        //↓生成済みセルの座標リスト
    //        private var cellPoints: List[(Int, Int)] = Nil
    //        //↓座標管理変数
    //        private var xx = 1
    //        private var yy = 1
    //        //↓座標を得るメソッド
    //        def getPoint: (Int, Int) = {
    //            val x = xx
    //            val y = yy
    //            if (xx < w) {
    //                xx += 1
    //            } else if (yy < h) {
    //                xx = 1
    //                yy += 1
    //            }
    //            (x, y)
    //        }
    //
    //        //GUI機能を有したセル
    //        class Cell(val point: (Int, Int)) extends JToggleButton with Simulant {
    //            val clock = LifeGame.this.clock
    //            clock.add(this)
    //
    //            //      val point = getPoint //座標定数？
    //
    //            //↓死んでるムーア近傍セル数
    //            private var isDead = true
    //            //↓死んでるムーア近傍セル数
    //            private var mooreDeadCount = 0
    //            //↓ムーア近傍セルリスト
    //            private var mooreCells: List[Cell] = Nil
    //
    //            def handleSimMessage(msg: Any) {
    //                //ムーア近傍の生死状態判断が必要
    //                msg match {
    //                    //M近傍死亡セルが7以上であれば死亡フラグを立てる
    //                    case ImDead =>
    //                        mooreDeadCount += 1
    //                        if (mooreDeadCount >= 7 && !isDead) {
    //                            isDead = true
    //                            tellDeathToMooreCells()
    //                        }
    //
    //                    //追加すべきセルリストを受信したら登録する
    //                    case YourMoore(cells) =>
    //                        mooreCells = cells
    //                }
    //            }
    //
    //            private def addMooreCells() {
    //
    //                //もしこのセルが種の起源であったら
    //                if (point == S) {
    //                    mooreCells =
    //                        new Cell(2, 1) ::
    //                            new Cell(2, 2) ::
    //                            new Cell(1, 2) :: Nil
    //                }
    //
    //            }
    //
    //            private def getMooreCellsForEntry(p: (Int, Int)): List[Cell] = {
    //                val newPoints = //オリジナルのムーア近傍セル座標リスト
    //                    if (p == (1, 1)) //左上
    //                        (2, 1) ::
    //                            (2, 2) ::
    //                            (1, 2) :: Nil
    //                    else if (p == (w, 1)) //右上
    //                        (w - 1, 1) ::
    //                            (w - 1, 2) ::
    //                            (w, 2) :: Nil
    //                    else if (p == (1, h)) //左下
    //                        (1, h - 1) ::
    //                            (2, h - 1) ::
    //                            (2, h) :: Nil
    //                    else if (p == (w, h)) //右下
    //                        (w - 1, h) ::
    //                            (w - 1, h - 1) ::
    //                            (w, h - 1) :: Nil
    //                    else if (p._1 == 1) //左辺
    //                        (1, p._2 - 1) ::
    //                            (2, p._2 - 1) ::
    //                            (2, p._2) ::
    //                            (2, p._2 + 1) ::
    //                            (1, p._2 + 1) :: Nil
    //                    else if (p._1 == w) //右辺
    //                        (w, p._2 - 1) ::
    //                            (w - 1, p._2 - 1) ::
    //                            (w - 1, p._2) ::
    //                            (w - 1, p._2 + 1) ::
    //                            (w, p._2 + 1) :: Nil
    //                    else if (p._2 == 1) //上辺
    //                        (p._1 - 1, 1) ::
    //                            (p._1 - 1, 2) ::
    //                            (p._1, 2) ::
    //                            (p._1 + 1, 2) ::
    //                            (p._1 + 1, 1) :: Nil
    //                    else if (p._2 == h) //下辺
    //                        (p._1 - 1, h) ::
    //                            (p._1 - 1, h - 1) ::
    //                            (p._1, h - 1) ::
    //                            (p._1 + 1, h - 1) ::
    //                            (p._1 + 1, h) :: Nil
    //                    else //上記以外近傍接セル数8の時
    //                        (p._1 - 1, p._2 - 1) ::
    //                            (p._1, p._2 - 1) ::
    //                            (p._1 + 1, p._2 - 1) ::
    //                            (p._1 - 1, p._2) ::
    //                            (p._1 + 1, p._2) ::
    //                            (p._1 - 1, p._2 + 1) ::
    //                            (p._1, p._2 + 1) ::
    //                            (p._1 + 1, p._2 + 1) :: Nil
    //
    //                //既成セルリストと新規セルリストの差分
    //                val diffPoints = cellPoints diff newPoints
    //                //差分を追加して既成セルリストを更新
    //                cellPoints ++= diffPoints
    //                //差分生成したセルリスト
    //                val newCells = diffPoints.map(new Cell(_))
    //                //////////////////////////////////////////////////
    //
    //                if (p == (1, 1)) { //左上
    //                    new Cell(2, 1) ::
    //                        new Cell(2, 2) ::
    //                        new Cell(1, 2) :: Nil
    //                } else if (p == (w, 1)) { //右上
    //                    new Cell(w - 1, 1) ::
    //                        new Cell(w - 1, 2) ::
    //                        new Cell(w, 2) :: Nil
    //                } else if (p == (1, h)) { //左下
    //                    new Cell(1, h - 1) ::
    //                        new Cell(2, h - 1) ::
    //                        new Cell(2, h) :: Nil
    //                } else if (p == (w, h)) { //右下
    //                    new Cell(w - 1, h) ::
    //                        new Cell(w - 1, h - 1) ::
    //                        new Cell(w, h - 1) :: Nil
    //                } else if (p._1 == 1) { //左辺
    //                    new Cell(1, p._2 - 1) ::
    //                        new Cell(2, p._2 - 1) ::
    //                        new Cell(2, p._2) ::
    //                        new Cell(2, p._2 + 1) ::
    //                        new Cell(1, p._2 + 1) :: Nil
    //                } else if (p._1 == w) { //右辺
    //                    new Cell(w, p._2 - 1) ::
    //                        new Cell(w - 1, p._2 - 1) ::
    //                        new Cell(w - 1, p._2) ::
    //                        new Cell(w - 1, p._2 + 1) ::
    //                        new Cell(w, p._2 + 1) :: Nil
    //                } else if (p._2 == 1) { //上辺
    //                    new Cell(p._1 - 1, 1) ::
    //                        new Cell(p._1 - 1, 2) ::
    //                        new Cell(p._1, 2) ::
    //                        new Cell(p._1 + 1, 2) ::
    //                        new Cell(p._1 + 1, 1) :: Nil
    //                } else if (p._2 == h) { //下辺
    //                    new Cell(p._1 - 1, h) ::
    //                        new Cell(p._1 - 1, h - 1) ::
    //                        new Cell(p._1, h - 1) ::
    //                        new Cell(p._1 + 1, h - 1) ::
    //                        new Cell(p._1 + 1, h) :: Nil
    //                } else { //上記以外近傍接セル数8の時
    //                    new Cell(p._1 - 1, p._2 - 1) ::
    //                        new Cell(p._1, p._2 - 1) ::
    //                        new Cell(p._1 + 1, p._2 - 1) ::
    //                        new Cell(p._1 - 1, p._2) ::
    //                        new Cell(p._1 + 1, p._2) ::
    //                        new Cell(p._1 - 1, p._2 + 1) ::
    //                        new Cell(p._1, p._2 + 1) ::
    //                        new Cell(p._1 + 1, p._2 + 1) :: Nil
    //                }
    //            }
    //
    //            //      def addCell(cell: Simulant) {
    //            //        //通過条件: ムーア近傍セル数 < 9
    //            //        assert(mooreCells.size < 9)
    //            //        mooreCells ::= cell
    //            //      }
    //
    //            //↓初期処理
    //            def simStarting() {
    //                if (point == S) {
    //                    mooreCells = getMooreCellsForEntry(S)
    //                    mooreCells.foreach(cell => getMooreCellsForEntry(cell.point))
    //                }
    //                if (isDead) {
    //                    tellDeathToMooreCells()
    //                }
    //            }
    //
    //            //↓ムーア近傍セルへ死状態を伝える
    //            private def tellDeathToMooreCells() {
    //                mooreCells.foreach(clock ! AfterDelay(1, ImDead, _))
    //            }
    //        }
    //
    //        //↓開始する
    //        def start() {
    //            clock ! Start
    //        }
    //
    //        //    def probe(cell: Cell) = new Simulant {
    //        //      val clock = LifeGame.this.clock
    //        //      clock.add(this)
    //        //      cell.addCell(this) //←監視対象配線へ自身(監視回路)を接続
    //        //      //↓受信メッセージの処理
    //        //      def handleSimMessage(msg: Any) {
    //        //        msg match {
    //        //          //↓入力線のシグナル値が変化した
    //        //          case ImDead =>
    //        //            //↓監視対象の配線状態を表示
    //        //            println("")
    //        //        }
    //        //      }
    //        //    }
    //        //    def setCell(dimensionCells: List[List[Cell]]) {
    //        //      new Cell(true)
    //        //    }
    //
    //        //↓生死を問うメッセージオブジェクト
    //        //↓死亡を知らせるメッセージ
    //        case object ImDead
    //        //↓自身の登録を依頼するメッセージ
    //        case class AddMe(me: Cell, moore: List[Cell]) //誰に送るのか？どうやって送るのか？
    //        //↓追加すべきムーア近傍セルを渡す
    //        case class YourMoore(moore: List[Cell])
    //    }
    //
    //    ///////////////////////// -- end of package -- /////////////////////////
    //}
    //
    ///**
    // * シミュレーションフレームワーク
    // */
    //package framework {
    //
    //    //↓部品オブジェクトが拡張するトレイト
    //    trait Simulant extends Actor {
    //
    //        //↓抽象クロック
    //        val clock: Clock
    //        //↓抽象メッセージ処理メソッド
    //        def handleSimMessage(msg: Any)
    //        //↓シミュレーション開始時の初期化メソッド
    //        def simStarting()
    //
    //        def act() {
    //            loop {
    //                react {
    //                    //↓終了メッセージを受信したらアクターを終了する
    //                    case Stop => exit()
    //                    //↓
    //                    case Ping(time) =>
    //                        if (time == 1) simStarting()
    //                        clock ! Pong(time, self)
    //                    //↓
    //                    case msg => handleSimMessage(msg)
    //                }
    //            }
    //        }
    //
    //        start() //←作成と同時にアクターを開始する
    //    }
    //
    //    //↓Clockクラスを実装する
    //    class Clock extends Actor {
    //        //↓クロック起動フラグ
    //        private var running = false
    //        //↓現在の時間
    //        private var currentTime = 0
    //        //↓作業表
    //        private var agenda: List[WorkItem] = Nil
    //        //↓全部品オブジェクトリスト
    //        private var allSimulants: List[Actor] = Nil
    //        //↓現在処理中の部品オブジェクト集合(空にならなければタイムチックは先行しない)
    //        private var busySimulants: Set[Actor] = Set.empty
    //
    //        start() //←作成と同時にアクターを開始する
    //
    //        def act() {
    //            loop {
    //                if (running && busySimulants.isEmpty)
    //                    advance()
    //                reactToOneMessage()
    //            }
    //        }
    //
    //        //↓タイムチックを先に進める
    //        def advance() {
    //            if (agenda.isEmpty && currentTime > 0) {
    //                println("/作業項目数: 空　/動作: クロック終了　/時間: "+currentTime)
    //                self ! Stop //←自身にStopメッセージを送信し終了させる
    //                return
    //            }
    //            currentTime += 1
    //            println("/作業項目数: "+agenda.size+"　/動作: 次へ進める　/時間: "+currentTime)
    //            processCurrentEvents() //←現在のイベントを部品オブジェに処理させる
    //            for (sim <- allSimulants)
    //                sim ! Ping(currentTime)
    //            busySimulants = allSimulants.toSet
    //        }
    //
    //        private def processCurrentEvents() {
    //            //↓作業表から処理すべき作業を選択する
    //            val todoNow = agenda.takeWhile(_.time <= currentTime)
    //            //↓処理すべき作業を取り除いた作業表を再設定する
    //            agenda = agenda.drop(todoNow.length)
    //            //↓各作業を対象の部品オブジェクトに処理させる(メッセージ送信)
    //            for (WorkItem(time, msg, target) <- todoNow) {
    //                assert(time == currentTime) //←ロジックチェック用
    //                target ! msg
    //            }
    //        }
    //
    //        def reactToOneMessage() {
    //            react {
    //                //↓新しい作業を作業表へ追加する
    //                case AfterDelay(delay, msg, target) =>
    //                    val item = WorkItem(currentTime + delay, msg, target)
    //                    agenda = insert(agenda, item)
    //
    //                //↓作業終了を伝える部品オブジェクトからの応答
    //                case Pong(time, sim) =>
    //                    //通過条件: 応答時刻 = 現在時刻
    //                    assert(time == currentTime)
    //                    //通過条件: 作業中オブジェクトリストが応答元オブジェクトを含んでいる
    //                    assert(busySimulants contains sim)
    //                    //↓作業中の部品オブジェクト集合から処理終了オブジェクトを消す
    //                    busySimulants -= sim
    //
    //                //↓クロックをスタートさせる
    //                case Start =>
    //                    running = true
    //                    println("*** Clock状態: 開始 ***")
    //
    //                //↓クロックをストップさせる
    //                case Stop =>
    //                    for (sim <- allSimulants)
    //                        sim ! Stop //←部品オブジェに終了メッセージを送信
    //                    println("*** Clock状態: 停止 ***")
    //                    exit() //←アクター終了
    //
    //            }
    //        }
    //
    //        //↓作業表のソート状態(時間がキー)を保つように作業項目を追加する
    //        private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] =
    //            if (ag.isEmpty || item.time < ag.head.time) //
    //                item :: ag
    //            else
    //                ag.head :: insert(ag.tail, item) //←末尾再帰？？
    //
    //        //↓部品オブジェクトを追加する
    //        def add(sim: Simulant) {
    //            allSimulants ::= sim
    //        }
    //    }
    //
    //    //↓作業項目
    //    case class WorkItem(time: Int, msg: Any, target: Actor)
    //    //↓delay時間後に実行されるべき新しい作業項目
    //    case class AfterDelay(delay: Int, msg: Any, target: Actor)
    //    //↓先行可能か確認のため送るメッセージ
    //    case class Ping(time: Int)
    //    //↓Pingメッセージに対する応答メッセージ
    //    case class Pong(time: Int, from: Actor)
    //    //↓開始メッセージオブジェクト
    //    case object Start
    //    //↓終了メッセージオブジェクト
    //    case object Stop
}
