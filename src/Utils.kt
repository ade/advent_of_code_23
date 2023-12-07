import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/** Shorthand for regex.matchEntire(this)!!.destructured */
fun String.destruct(regex: Regex) = regex.matchEntire(this)!!.destructured

/** Shorthand for regex.matchEntire(this)!!.destructured but with conversion to Int */
fun String.destructInts(regex: Regex): IntsDestructured {
    val groupVals = regex.matchEntire(this)!!.groupValues.drop(1).mapIndexed { id, it ->
        it.toIntOrNull() ?: throw Exception("position $id not parseable to Int: \"$it\"")
    }
    return IntsDestructured(groupVals)
}

/** Shorthand for regex.matchEntire(this)!!.destructured but with conversion to Long */
fun String.destructLongs(regex: Regex): LongsDestructured {
    val groupVals = regex.matchEntire(this)!!.groupValues.drop(1).mapIndexed { id, it ->
        it.toLongOrNull() ?: throw Exception("position $id not parseable to Int: \"$it\"")
    }
    return LongsDestructured(groupVals)
}

class IntsDestructured(val groupValues: List<Int>) {
    operator inline fun component1() = groupValues[0]
    operator inline fun component2() = groupValues[1]
    operator inline fun component3() = groupValues[2]
    operator inline fun component4() = groupValues[3]
    operator inline fun component5() = groupValues[4]
    operator inline fun component6() = groupValues[5]
    operator inline fun component7() = groupValues[6]
    operator inline fun component8() = groupValues[7]
    operator inline fun component9() = groupValues[8]
    operator inline fun component10() = groupValues[9]
}

class LongsDestructured(val groupValues: List<Long>) {
    operator inline fun component1() = groupValues[0]
    operator inline fun component2() = groupValues[1]
    operator inline fun component3() = groupValues[2]
    operator inline fun component4() = groupValues[3]
    operator inline fun component5() = groupValues[4]
    operator inline fun component6() = groupValues[5]
    operator inline fun component7() = groupValues[6]
    operator inline fun component8() = groupValues[7]
    operator inline fun component9() = groupValues[8]
    operator inline fun component10() = groupValues[9]
}