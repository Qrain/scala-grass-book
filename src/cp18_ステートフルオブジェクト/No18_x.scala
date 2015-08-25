package cp18_ステートフルオブジェクト {
    /*
 * ステートフルオブジェクト(状態的な、つまり変遷していくオブジェクト)
 * ケーススタディ：つまり、離散イベントシミュレーション
 */

    object No18_x extends App {

        /*
     * 実際にデジタル回路シミュレータを使ってみる！
     */
        import MySimulation._ //オブジェクトメンバをインポート
        val input1, input2, sum, carry = new Wire
        probe("sum", sum) //↓探測器を追加してく
        probe("carry", carry)
        halfAdder(input1, input2, sum, carry)
        input1 setSignal true
        run()
        input2 setSignal true
        run()
    }

    object MySimulation extends simulation.CircuitSimulation {
        def InverterDelay = 1 //論理演算の遅延時間をそれぞれ定義する
        def AndGateDelay = 3
        def OrGateDelay = 5
    }

    package simulation {
        /*
	 * シミュレーションAPI
	 * 予定表に登録されている作業項目を実行するだけの単純
	 * かつ興味深いシミュレーションフレームワーク
	 */
        abstract class Simulation {
            type Action = () => Unit //空パラメータリストをとりUnitを返す手続きにActionという別名を付与する
            case class WorkItem(time: Int, action: Action) //指定された時間に実行されるアクション(作業項目)
            private var curtime = 0 //アクションが実行されるタイミングを管理する(現在時)
            def currentTime = curtime //curtimeの公開アクセッサーメソッド
            private var agenda = List.empty[WorkItem] //未実行の作業項目を管理する予定表(WorkItem.time順にソートされてる)
            private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = //agendaのソート状態を保つように作業項目を追加する
                if (ag.isEmpty || item.time < ag.head.time)
                    item :: ag
                else
                    ag.head :: insert(ag.tail, item)

            def afterDelay(delay: Int)(block: => Unit) { //予定表に作業項目を追加する唯一の手段
                val item = WorkItem(currentTime + delay, () => block) //現在時刻のdelay時間後にアクション(block)を実行する作業項目
                agenda = insert(agenda, item) //↑の作業項目を予定表に追加する
            }

            def run() { //予定表の全作業項目を実行する
                afterDelay(0) {
                    println("*** simulation started, time = " + currentTime + " ***")
                }
                while (!agenda.isEmpty) next()
            }

            private def next() {
                (agenda: @unchecked) match {
                    case item :: rest => //先頭要素と残存に分離
                        agenda = rest //予定表を残存部分で更新
                        curtime = item.time //実行する作業項目の時間で現在時を更新
                        item.action() //作業項目実行
                }
            }
        }
        /*
	 * デジタル回路のシミュレーション
	 * 上記のシミュレーションフレームワークを利用してデジタル回路DSLを実装する
	 */
        abstract class BasicCircuitSimulation extends Simulation {
            //以下の抽象メソッドは定数を表すため頭文字が大文字になっている
            def InverterDelay: Int //インバーター(否定)のディレイ
            def AndGateDelay: Int //ANDゲート(論理積)のディレイ
            def OrGateDelay: Int //ORゲート(論理和)のディレイ

            class Wire { //各ゲートをつなぐ配線を表すクラス
                private var sigVal = false
                private var actions = List.empty[Action]
                def getSignal = sigVal //配線の信号を返す
                def setSignal(s: Boolean) { //配線に信号を設定する
                    if (s != sigVal) { //信号が違うものならば
                        sigVal = s
                        actions foreach (_()) //配線上のアクション全てを()に適用、つまり実行する。
                    }
                }
                def addAction(a: Action) = {
                    //配線に指定したアクションを付属する
                    //配線の信号が変わるたびに配線の全アクションが実行される
                    //付属させるアクションは追加時に1度は実行される
                    actions = a :: actions //アクションを追加
                    //actions ::= a
                    a() //空パラメータを渡すことでアクションを実行する
                }
            }

            def inverter(input: Wire, output: Wire) = {
                def invertAction() {
                    val inputSig = input.getSignal
                    afterDelay(InverterDelay) { output setSignal !inputSig } //指定ディレイ後にoutput配線の信号を反転させる
                }
                input addAction invertAction //inputにinvertActionをインストールする(処理自体はすぐに実行する)
            }

            def andGate(a1: Wire, a2: Wire, output: Wire) = {
                def andAction() = {
                    val a1Sig = a1.getSignal
                    val a2Sig = a2.getSignal
                    afterDelay(AndGateDelay) {
                        output setSignal (a1Sig & a2Sig)
                    }
                }
                //a1,a2それぞれにアクションを追加(入力線の信号が変わることを考慮して)
                a1 addAction andAction
                a2 addAction andAction
            }

            def orGate(o1: Wire, o2: Wire, output: Wire) = {
                def orAction() = {
                    val o1Sig = o1.getSignal
                    val o2Sig = o2.getSignal
                    afterDelay(OrGateDelay) {
                        output setSignal (o1Sig | o2Sig)
                    }
                }
                o1 addAction orAction
                o2 addAction orAction
            }

            def probe(name: String, wire: Wire) { //配線に探測器を挿入する(配線上の信号の変化をチェックする)
                def probeAction() {
                    println(name + " " + currentTime + " new-value = " + wire.getSignal) //配線名、シミュレート時間、配線の信号を表示する
                }
                wire addAction probeAction //配線に探測器を追加し信号が変わるたびに実行される
            }
        }

        abstract class CircuitSimulation extends BasicCircuitSimulation {
            def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) { //半加算器
                val d, e = new Wire
                orGate(a, b, d)
                andGate(a, b, c)
                inverter(c, e)
                andGate(d, e, s)
            }

            def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) { //全加算器
                val s, c1, c2 = new Wire
                halfAdder(a, cin, s, c1) //半加算器を利用する
                halfAdder(b, s, sum, c2)
                orGate(c1, c2, cout)
            }

        }
    }
}
