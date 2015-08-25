package cp26_XMLの操作

/*
 * XMLの操作
 * XMLを対象とするパターンマッチ
 */

object No26_8 extends App {

    //単一サブノードにだけマッチする関数
    def proc1(node: xml.Node) = node match {
        case <a>{ contents }</a> => "It's an a: "+contents
        case <b>{ contents }</b> => "It's a b: "+contents
        case _                   => "It's something else."
    }

    println(proc1(<a>apple</a>))
    println(proc1(<b>banana</b>))
    println(proc1(<c>cherry</c>))
    println(proc1(<a>a<em>red</em>apple</a>))

    //サブノードのシーケンスにマッチするよう書き換えた関数
    def proc2(node: xml.Node) = node match {
        case <a>{ contents @ _* }</a> => "It's an a: "+contents
        case <b>{ contents @ _* }</b> => "It's a b: "+contents
        case _                        => "It's something else."
    }
    println(proc2(<a>apple</a>))
    println(proc2(<b>banana</b>))
    println(proc2(<c>cherry</c>))
    println(proc2(<a>a<em>red</em>apple</a>))
    //複数の子ノードがシーケンスの要素として格納されていることが分かる

    val catalog =
        <catalog>
            <cctherm>
                <description>hot dog #5</description>
                <yearMade>1952</yearMade>
                <dateObtained>March 14, 2006</dateObtained>
                <bookPrice>2199</bookPrice>
                <purchasePrice>500</purchasePrice>
                <condition>9</condition>
            </cctherm>
            <cctherm>
                <description>Sprite Boy</description>
                <yearMade>1964</yearMade>
                <dateObtained>April 28, 2003</dateObtained>
                <bookPrice>1695</bookPrice>
                <purchasePrice>595</purchasePrice>
                <condition>5</condition>
            </cctherm>
        </catalog>

    println(catalog.child.size) //ccthermタグ間に空白要素があるため子ノード数が5となっている

    catalog match {
        case <catalog>{ therms @ _* }</catalog> =>
            for (therm @ <cctherm>{ _* }</cctherm> <- therms) //←ジェネレータのパターンマッチ式で空白除去をしてる
                println("processing: "+(therm \ "description").text)
    }

}

