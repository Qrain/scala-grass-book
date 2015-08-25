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

/**
 * for式の再説
 * N女王問題
 */
object No23_2 extends App {

  def queens(n: Int) = {
    def placeQueens(k: Int): List[List[(Int, Int)]] =
      if (k == 0)
        Nil :: Nil
      else
        for {
          queens <- placeQueens(k - 1)
          column <- 1 to n
          queen = (k, column)
          if isSafe(queen, queens)
        } yield queen :: queens

    def isSafe(queen: (Int, Int), queens: List[(Int, Int)]) = {
      def inCheck(q1: (Int, Int), q2: (Int, Int)) = //他の女王に取られる位置ならTRUEを返す
        q1._1 == q2._1 || //同段
          q1._2 == q2._2 || //同行
          (q1._1 - q2._1).abs == (q1._2 - q2._2).abs //同筋(対角上)
      queens forall (!inCheck(queen, _)) //定量比較、つまりinCheckメソッドが全てFalseを示せば全体としてTrueを示す
    }
    placeQueens(n)
  }
  //10 -> 724  9 352
  val n = 7 //チェス盤一辺のサイズ
  val temp = queens(n)
  1 to temp.size foreach (i => println(i + ":\t" + temp(i - 1)))
  val placeQueens = temp.head //とりあえず候補のうち1つを選択
  val icon = new ImageIcon(ImageIO.read(new File("src/cp23_for式の再説/chess-queen.jpg"))) //クイーンアイコン
  val frame = new JFrame
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setResizable(false)
  frame.setLayout(new GridLayout(n + 1, n + 1))

  for (col <- 1 to n + 1; row <- 1 to n + 1)
    if (row == 1 && col > 1)
      frame.add(new JLabel(col - 1 + "", SwingConstants.CENTER))
    else if (col == 1 && row > 1)
      frame.add(new JLabel(row - 1 + "", SwingConstants.CENTER))
    else if (row == 1 && col == 1)
      frame.add(new JLabel)
    else {
      val lb = new JLabel()
      lb.setBorder(new LineBorder(Color.BLACK))
      if (placeQueens.exists(_ == (col - 1, row - 1))) lb.setIcon(icon)
      frame.add(lb)
    }
  frame.pack()
  frame.setVisible(true)

}

