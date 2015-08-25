package cp23_for式の再説
import javax.swing.JFrame
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.border.LineBorder
import java.awt.Color
import java.awt.Dimension
import javax.imageio.ImageIO
import java.io.File
import javax.swing.ImageIcon
import javax.swing.SwingConstants

/*
 * for式の再説
 * for式の変換
 */

object No23_4 extends App {
  case class Book(title: String, authors: String*)

  val books = //書籍を表現するデータベース
    Book(
      "Structure and Interpretation of Computer Programs",
      "Abelson, Harold", "Sussman, Gerald J.") ::
      Book(
        "Principles of Compiler Design",
        "Aho, Alfred", "Ullman, Jeffrey") ::
        Book(
          "Programming in Modula-2",
          "Wirth, Nikaus") ::
          Book(
            "Elements of ML Programming",
            "Ullman, Jeffrey") ::
            Book(
              "The Java Language Specification",
              "Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad") :: Nil

  //for式によるクエリー
  println {
    for { //著者の姓が"Gosling"で始まる書籍のタイトルを取得する
      b <- books
      a <- b.authors
      if a startsWith "Gosling"
    } yield b.title
  }

  println {
    for { //タイトルに"Program"が含まれている書籍のタイトルを取得する
      b <- books
      if b.title.indexOf("Program") >= 0
    } yield b.title
  }

  println {
    (for { //データベースに2つ以上の著書を持っている著者名を取得する
      b1 <- books
      b2 <- books
      if b1 != b2 //同じ書籍はフィルタリング
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1).distinct //重複除去メソッドがあるではないか。
  }

}

