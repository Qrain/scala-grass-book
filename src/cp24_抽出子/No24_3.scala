package cp24_抽出子

/*
 * 抽出子
 * 変数が1個以下のパターン
 */

object No24_3 extends App {

    object Twice { //unapplyはタプルでなく文字列をそのままSomeでラップして返している
        def apply(s: String) = s + s
        def unapply(s: String) = {
            val len = s.length / 2
            val half = s.substring(0, len)
            if (half == s.substring(len)) Some(half) else None
        }
    }

    println(Twice("abc"))
    println(Twice.unapply("abcabcabcabc"))

    object UpperCase { //抽出子パターンで変数を全く束縛せず真偽値を返す
        def unapply(s: String) = s.toUpperCase == s //大文字ならばTRUE
    }

    def userTwiceUpper(s: String) = s match { //今までの抽出子を組合わせたメソッド
        case EMail(Twice(x @ UpperCase()), domain) =>
            "match: "+x+" in domain "+domain
        case _ => "no match"
    }

    println(userTwiceUpper("DIDDID@def.com"))
    println(userTwiceUpper("DIDDOD@def.com"))
    println(userTwiceUpper("DidDid@def.com"))
}

