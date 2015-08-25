package cp20_抽象メンバ

/*
 * 抽象メンバ
 * ケーススタディ:通貨計算
 */

object No20_9 extends App {

    //Currencyクラスの初期設計(誤コード)
    abstract class Currency {
        val amount: Long //金額
        def designation: String //通貨単位
        override def toString = amount + " " + designation
        def +(that: Currency): Currency = {
            error("Undefined")
        }
        def *(x: Double): Currency = {
            error("Undefined")
        }
    }

    //Currencyクラスの第2設計(不完全コード)
    abstract class AbstractCurrency {
        type Currency <: AbstractCurrency //抽象型を追加した
        val amount: Long //金額
        def designation: String //通貨単位
        override def toString = amount + " " + designation
        def +(that: Currency): Currency = {
            error("Undefined")
        }
        def *(x: Double): Currency = {
            error("Undefined")
        }
    }
    //上のクラスを拡張したDollarクラス
    abstract class Dollar extends AbstractCurrency {
        type Currency = Dollar
        def designation = "USD"
    }

    //Currencyクラスの第3設計(完全コード)
    abstract class CurrencyZone { //外部クラス
        type Currency <: AbstractCurrency //抽象型を外部に出す
        val CurrencyUnit: Currency //通貨基準
        def make(x: Long): Currency

        abstract class AbstractCurrency { //内部クラス
            val amount: Long //金額
            def designation: String //通貨単位
            //足した結果をCurrencyとして返す
            def +(that: Currency) = make(this.amount + that.amount)
            def *(x: Double) = make((this.amount * x).toLong)
            def -(that: Currency) = make(this.amount - that.amount)
            def /(that: Currency) = this.amount.toDouble / that.amount
            def /(that: Double) = make((this.amount / that).toLong)
            def from(other: CurrencyZone#AbstractCurrency): Currency =
                make(Math.round(other.amount.toDouble * Converter.exchangeRate(other.designation)(this.designation)))

            override def toString =
                (amount.toDouble / CurrencyUnit.amount.toDouble).formatted("%." + decimals(CurrencyUnit.amount) + "f") + " " + designation
            private def decimals(n: Long): Int = if (n == 1) 0 else 1 + decimals(n / 10) //整数の桁数から1引いた値を返す
        }
    }

    object US extends CurrencyZone { //USドル
        type Currency = Dollar //抽象型を具象定義
        abstract class Dollar extends AbstractCurrency {
            def designation = "USD"
        }
        val Cent = make(1) //meaning 1cent
        val Dollar = make(100) //meaning 1dollar
        val CurrencyUnit = Dollar //標準通貨単位
        def make(cents: Long) = new Dollar { //金額ｘを保持したDollarを生成
            val amount = cents
        }
    }

    object Europe extends CurrencyZone { //ユーロ
        type Currency = Euro
        abstract class Euro extends AbstractCurrency {
            def designation = "EUR"
        }
        def make(cents: Long) = new Euro { val amount = cents }
        val Cent = make(1)
        val Euro = make(100)
        val CurrencyUnit = Euro
    }

    object Japan extends CurrencyZone { //円
        type Currency = Yen
        abstract class Yen extends AbstractCurrency {
            def designation = "JPY"
        }
        def make(yen: Long) = new Yen { val amount = yen }
        val Yen = make(1)
        val CurrencyUnit = Yen
    }

    object Converter {
        var exchangeRate = Map(
            "USD" -> Map("USD" -> 1.0, "EUR" -> 0.7596, "JPY" -> 1.211, "CHF" -> 1.223),
            "EUR" -> Map("USD" -> 1.316, "EUR" -> 1.0, "JPY" -> 1.594, "CHF" -> 1.623),
            "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272, "JPY" -> 1.0, "CHF" -> 1.018),
            "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160, "JPY" -> 0.982, "CHF" -> 1.0))
    }

    println(Japan.Yen from US.Dollar)
    println(Japan.Yen from Europe.Euro * 50)

}

