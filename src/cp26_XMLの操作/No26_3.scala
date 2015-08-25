package cp26_XMLの操作

/*
 * XMLの操作
 * XMLリテラル
 */

object No26_3 {
  def main(args: Array[String]): Unit = {
    /*
   * ●Nodeクラスは全XMLノードクラスの抽象スーパークラスである
   * ●Textクラスはテキストだけを保持しているノードである(<a>string</a>の"string"部分)
   * ●NodeSeqはNodeのシーケンスを保持する。Node自体はNodeSeqの拡張なので
   *    Nodeは１要素のNodeSeqと考えることができる。
   */

    //XMLリテラルの基本形
    val xml1 =
      <a>
        This is  some XML.
    Here is a tag:<atag/>
      </a>

    //リテラル内の{}内はエスケープとなりScala構文が使える
    println(<a>{ "He" + xml1 }</a>) //xml1の<>等は自動的にエスケープされる　

    //{}内ではif文も使用できる
    val yearMade = 1955
    println {
      <a>{
        if (yearMade < 2000)
          <old>{ yearMade }</old>
        else
          xml.NodeSeq.Empty
      }</a>
    }

    println {
      <elem>
        <a> { "</a>potential security hole<a>" }</a>
        <b>{ 2 + 10 }</b>
      </elem>
    }

  }
}

