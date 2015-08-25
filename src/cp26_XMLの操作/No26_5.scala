package cp26_XMLの操作

/*
 * XMLの操作
 * XMLの分解
 */

object No26_5 extends App {

    //テキストの抽出
    println(<a>Sounds<tab/></a>.text) //テキストノードを文字列として取り出す

    //サブ要素の抽出

    //サブ要素サーチ(直下要素のみを対象にタグ名で探す)
    println(<a><b><c>hello</c></b></a> \ "b") //タグ<a>の子要素としてタグ<b>を探す
    println(<a><b><c>hello</c></b></a> \ "c") //タグ<a>の子要素としてタグ<c>を探す
    println(<a><b><c>hello</c></b></a> \ "a") //タグ<a>の子要素としてタグ<a>を探す

    //ディープサーチ(サブのサブ要素など深い階層まで探索する)
    println(<a><b><c>hello</c></b></a> \\ "c")
    println(<a><b><c>hello</c></b></a> \\ "a")

    //属性の抽出
    val joe = <employee rank="code monkey" name="Joe" serial="123"/>
    println(joe \ "@name")
    println(joe \ "@serial")
}

