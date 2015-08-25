package cp26_XMLの操作

/**
 * XMLの操作
 * デシリアライゼーション
 */
object No26_6 extends App {

    //シリアライザー(内部データ構造⇒XML)の逆で
    //XML⇒内部データ構造への変換を司る

    def fromXML(node: scala.xml.Node) = new CCTherm {
        val description = (node \ "description").text
        val yearMade = (node \ "yearMade").text.toInt
        val dateObtained = (node \ "dateObtained").text
        val bookPrice = (node \ "bookPrice").text.toInt
        val purchasePrice = (node \ "purchasePrice").text.toInt
        val condition = (node \ "condition").text.toInt
    }

    println {
        fromXML(<cctherm>
                    <description>hot dog #5</description>
                    <yearMade>1952</yearMade>
                    <dateObtained>March 14, 2006</dateObtained>
                    <bookPrice>2199</bookPrice>
                    <purchasePrice>500</purchasePrice>
                    <condition>9</condition>
                </cctherm>)
    }
}

