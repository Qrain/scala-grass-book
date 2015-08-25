package cp09_制御と抽象化
/*
 * 新しい制御構造を作る
 * ※前回のカリー化を利用する
 */
object No9_4 {

    import java.io.{ File, PrintWriter }
    import java.util.Scanner

    def main(args: Array[String]) {

        println(twice(_ * 2, 3)) //2回倍にする

        //withPrintWriter1の使用
        withPrintWriter1(
            new File("date.txt"), //書き込み対象のファイルオブジェクトを生成
            _.println(new java.util.Date)) //PrintWriterに行わせる具体的な処理を関数リテラルで記述

        //引数の数と括弧の規則
        println { //printlnの引数は x:Any の1つなので中括弧を使用できる
            "Hello, World!!"
        }
        printf("Hello, %dWorld!!", 8) //↑に対してprintfの引数は複数なので通常の括弧で囲う必要がある
        //1引数にのみ中括弧が使用できるのは、クライアントプログラマにそこに関数リテラルを記述させたいためである。

        //withPrintWriter2の使用
        withPrintWriter2(new File("curried_date.txt")) { //カリー化してあるため、より制御構造らしくなっている。
            _.println(new java.util.Date)
        }

        val result = query("jdbc:mysql://localhost/world", "root", "1272385731") {
            _.executeQuery {
                "show databases;"
            }
        }

        println(result)
        while (result.next()) {
            println(result.getString(0)); //???
        }
    }

    //同じ操作を2度行う制御構造
    def twice(op: Double => Double, x: Double) = op(op(x))

    //リソースのオープン・操作・クローズを行う制御構造
    def withPrintWriter1(file: File, op: PrintWriter => Unit) { //カリー前
        val writer = new PrintWriter(file) //ファイルへの出力準備
        try {
            /*
       * writer(リソース)を関数opへ貸し出しているので
       * このようなパターンの制御構造はローンパターンと呼ばれている。
       * 関数opによって具体的な処理が行われる。
       */
            op(writer)
        } finally {
            writer.close()
        }
    }

    def withPrintWriter2(file: File)(op: PrintWriter => Unit) { //カリー後
        val writer = new PrintWriter(file) //ファイルへの出力準備
        try {
            op(writer)
        } finally {
            writer.close()
        }
    }
    import java.sql.{ //MySQLアクセス用
        Connection,
        DriverManager,
        ResultSet,
        PreparedStatement,
        SQLException,
        Statement
    }
    //わたくしオリジナル制御構造(超限定的) Query to JDBC
    def query(connect: String, user: String, pass: String)(op: Statement => ResultSet) = {

        val ps = Runtime.getRuntime.exec("net start mysql") //MySQLへの接続準備
        Class.forName("com.mysql.jdbc.Driver") // ドライバクラスをロード
        val con = DriverManager.getConnection(connect, user, pass) // データベースへ接続
        val st = con.createStatement // ステートメントオブジェクトを生成

        try {
            op(st)
        } finally {
            con.close()
            st.close()
            ps.destroy()
        }

    }

}

