package cp07_組み込みの制御構造
/*
 * 組み込み制御構造
 * try-catch-finally文編
 */
object No7_4 {

    import java.net.{ URL, MalformedURLException }

    def main(args: Array[String]) {

        println(urlFor("http://www.youtube.com/watch?v=FAxas_C1Wyw"))

        println(try { return 1 } finally { return 2 })

        println(try { 1 } finally { 2 })
    }

    def urlFor(path: String) = try {
        new java.net.URL(path)
    } catch {
        case e: MalformedURLException => new URL("http://www.scala-lang.org")
    }

}
