package cp18_ステートフルオブジェクト

/*
 * ステートフルオブジェクト(状態的な、つまり変遷していくオブジェクト)
 * どんなオブジェクトがステートフルなの？？
 */

object No18_1 extends App {

    //BankAccount(ステートフル)
    val ba = new BankAccount
    ba deposit 100
    println(ba withdraw 80) //引き出し成功
    println(ba withdraw 80) //残高不足のため失敗

    //varを持っていても純粋関数型クラスになり得る！？
    val keyed = new Keyed
    println("Keyed")
    (1 to 10).foreach(i => println(i+": "+keyed.computeKey)) //全部表示するまで時間が掛かる
    val memokeyed = new MemoKeyed //キャッシュのためにvarを使っているが親のKeyedが純粋関数型なので、子も純粋関数型であるといえる
    println("MemoKeyed")
    (1 to 10).foreach(i => println(i+": "+memokeyed.computeKey)) //キャッシュを利用しているのですぐ表示可能

}

class BankAccount {
    private var bal = 0
    def balance = bal
    def deposit(amount: Int) {
        require(amount > 0)
        bal += amount
    }
    def withdraw(amount: Int) =
        if (amount > bal)
            false
        else {
            bal -= amount
            true
        }
}

class Keyed {
    def computeKey: Int = { //重い処理を想定する
        java.lang.Thread.sleep(1000) //1秒停止
        1000
    }
}

class MemoKeyed extends Keyed {
    private var keyCache: Option[Int] = None
    override def computeKey = {
        if (!keyCache.isDefined) //中がNoneなら
            keyCache = Some(super.computeKey)
        keyCache.get //重い操作を何度もしなくてすむ
    }
}