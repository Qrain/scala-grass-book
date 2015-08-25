package cp09_制御と抽象化
/*
 * クライアントコードの単純化
 * ※APIに高階関数を使用してコードを単純化する方法。()
 */
object No9_2 {

    def main(args: Array[String]) {

    }

    //以下2つのメソッドは引数Listの要素に負数が在るかチェックする
    def containsNeg1(nums: List[Int]) = { //←通常コード
        var exists = false
        for (num <- nums)
            if (num < 0)
                exists = true
        exists
    }
    //↓Listをレシーバーとした高階関数existsを呼び出した例
    def containsNeg2(nums: List[Int]) = nums.exists(_ < 0) //関数リテラル(n: Int) => n < 0を引数として指定するよ

    //以下2つのメソッドは引数Listの要素に奇数が在るかチェックする
    def containsOdd1(nums: List[Int]) = { //←containsNeg1とif条件式以外は重複している
        var exists = false
        for (num <- nums)
            if (num < 0)
                exists = true
        exists
    }
    //↓高階関数へ渡す関数を変えるだけなので、コードは大幅に削減できる。
    def containsOdd2(nums: List[Int]) = nums.exists(_ % 2 == 1)
}

