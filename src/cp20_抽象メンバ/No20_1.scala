package cp20_抽象メンバ

/*
 * 抽象メンバ
 * 抽象メンバの弾丸ツアー
 */

object No20_1 extends App {

    /*
   *
   */

    trait Abstract { //型、メソッド、val、varの4つの抽象メンバを宣言しているトレイト
        type T
        def transform(x: T): T
        val initial: T
        var current: T
    }

    class Concrete extends Abstract { //Abstractトレイトの具象定義
        type T = String
        def transform(x: T) = x * x.size
        val initial = "hi"
        var current = initial
    }

}

