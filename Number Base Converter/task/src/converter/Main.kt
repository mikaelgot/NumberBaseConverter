package converter

import java.math.RoundingMode
import kotlin.math.pow
import kotlin.system.exitProcess

// Do not delete this line
val symbols = (('0'..'9').toList() + ('a'..'z').toList())

fun main() {
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val input = readln().trim()
        if (input == "/exit") exitProcess(0)
        else {
            val (source, target) = input.split(" ").map { it.toInt() }
            while (true) {
                println("Enter number in base $source to convert to base $target (To go back type /back)")
                val num = readln().trim()
                if (num == "/back") break
                else {
                    val res = convert(num, source, target)
                    println("Conversion result: $res\n")
                }
            }
        }
    }
}

fun fractionalToDec(fract: String, base: Int): String {
    //println("starting fractional num = $fract")
    var sum: Double = 0.0
    for (i in fract.indices) {
        val part = symbols.indexOf(fract[i]) * base.toDouble().pow(-(i+1).toDouble())
        //println("fract = ${fract[i]}, part = $part, symbols.indexOf(fract[i]) = ${symbols.indexOf(fract[i])}")
        sum += part
    }
    val res = sum.toBigDecimal()//.setScale(5, RoundingMode.HALF_DOWN)
    return res.toString()
}

fun fractionalFromDec(num: String, base: Int): String {
    //println("fractionalFromDec: starting num = $num")
    var rem = num.toDouble()
    var digit = 0
    var string = ""
    for (times in 0..4) {
        rem *= base
        digit = rem.toInt()
        rem -= digit
        //println("rem = $rem, digit = $digit")
        string += symbols[digit]
        if (rem <= 0) break
    }
    return string.padEnd(5,'0')
}

fun convert(num: String, source: Int, target: Int): String {
    if (num.contains('.')) {
    val (integer, fractional)  = num.split('.')
    val decInteger = toDec(integer, source)
    val intResult = fromDec(decInteger, target)
    val decFractional = fractionalToDec(fractional, source)
    val fracResult = fractionalFromDec("$decFractional", target)
    return "$intResult.$fracResult"
    }
    else {
        val integer = num
        val decInteger = toDec(integer, source)
        val intResult = fromDec(decInteger, target)
        return intResult
    }
}

fun toDec(num: String, base: Int): String {
    return num.toLong(base).toString()
}

fun fromDec(long: String, base: Int): String {
    return long.toBigInteger().toString(base)
}