package cp26_XMLの操作

/*
 * XMLの操作
 * シリアライゼーション
 */

object No26_4 extends App {

    val therm = new CCTherm {
        val description = "hot dog #5"
        val yearMade = 1952
        val dateObtained = "March 14, 2006"
        val bookPrice = 2199
        val purchasePrice = 500
        val condition = 9
    }

    println(therm)
    println(therm.toXML)
    println(<a>{{← brace zone →}}</a>) //ちなみに中括弧自体のエスケープは{}
}

abstract class CCTherm { //コカ○ーラ温度計の情報を保持する抽象クラス
    val description: String
    val yearMade: Int
    val dateObtained: String
    val bookPrice: Int //単位:米国セント
    val purchasePrice: Int //単位:米国セント
    val condition: Int //1から10まで
    override def toString = description

    def toXML = //クラスの保持情報をXMLへ変換するメソッド
        <cctherm>
            <description>{ description }</description>
            <yearMade>{ yearMade }</yearMade>
            <dateObtained>{ dateObtained }</dateObtained>
            <bookPrice>{ bookPrice }</bookPrice>
            <purchasePrice>{ purchasePrice }</purchasePrice>
            <condition>{ condition }</condition>
        </cctherm>
}

