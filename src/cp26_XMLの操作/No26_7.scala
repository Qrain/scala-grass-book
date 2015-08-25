package cp26_XMLの操作

/*
 * XMLの操作
 * ロードと保存
 */

object No26_7 extends App {

    val therm = new CCTherm {
        val description = "hot dog #5"
        val yearMade = 1952
        val dateObtained = "March 14, 2006"
        val bookPrice = 2199
        val purchasePrice = 500
        val condition = 9
    }

    //XMLをファイルとして保存する
    xml.XML.save("C:/Users/TAKASHI/Downloads/test.xml", therm.toXML, "UTF-8", true, null)

    //XMLファイルを読み込む
    println(xml.XML.loadFile("C:/Users/TAKASHI/Downloads/test.xml"))
}

