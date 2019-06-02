package org.evomaster.core.parser.visitor

import org.evomaster.core.search.gene.Gene
import org.evomaster.core.search.gene.regex.CharacterClassEscapeRxGene
import org.evomaster.core.search.gene.regex.PatternCharacterBlock
import org.evomaster.core.search.gene.regex.RegexGene
import org.evomaster.core.search.service.Randomness
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Ecma262HandlerTest{

    private fun checkRegex(regex: String) : RegexGene{

        val randomness = Randomness()

        val gene = Ecma262Handler.createGene(regex)

        for(seed in 1..100L) {
            randomness.updateSeed(seed)

            gene.randomize(randomness, false, listOf())

            val instance = gene.getValueAsRawString()

            /*
            Ecma262 and Java regex are not exactly the same.
            But for the base types we test in this class, they
            should be equivalent.
            */
            assertTrue(instance.matches(Regex(regex)),
                    "String not matching regex:\n$regex\n$instance")
        }

        return gene
    }

    @Test
    fun testEmpty(){
        checkRegex("")
    }

    @Test
    fun testBaseStringSingleChar(){

        val regex = "a"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }

    @Test
    fun testBaseStringMultiChar(){

        val regex = "abc"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }

    @Test
    fun testSingleDigit(){

        val regex = "1"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }

    @Test
    fun testMultiDigits(){

        val regex = "123"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }

    @Test
    fun testLetterDigits(){

        val regex = "abc123"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }


    @Test
    fun testIssueWithB(){

        val regex = "B123"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }

    @Test
    fun testUpperCaseString(){

        val regex = "ABCD"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is PatternCharacterBlock })
    }


    @Test
    fun testDigitEscape(){

        val regex = "\\d"
        val gene = checkRegex(regex)

        assertTrue(gene.flatView().any { it is CharacterClassEscapeRxGene })
    }

    @Test
    fun testYearPattern(){

        val regex = "\\d\\d\\d\\d-\\d\\d-\\d\\d"
        checkRegex(regex)
    }


    @Test
    fun testQuantifierSingle(){
        checkRegex("a{2}")
    }


    @Test
    fun testQuantifierRange(){
        checkRegex("a{3,5}")
    }

    @Test
    fun testQuantifierOnlyMin(){

        val regex = "a{2,}"
        val gene = checkRegex(regex)

        val s = gene.getValueAsRawString()
        //even if unbound, not going to create billion-long strings
        assertTrue(s.length < 10)
    }

    @Test
    fun testQuantifierStar(){
        checkRegex("a*")
    }

    @Test
    fun testQuantifierPlus(){
        checkRegex("a+")
    }

    @Test
    fun testQuantifierOptional(){
        checkRegex("a?")
    }

    @Test
    fun testQuantifierCombined(){
        checkRegex("a*b+c{1}d{2,}e{2,100}")
    }

    @Test
    fun testYearWithQuantifier(){

        val regex = "\\d{4}-\\d{1,2}-\\d{1,2}"
        checkRegex(regex)
    }


    @Test
    fun testAnyChar(){
        checkRegex(".")
    }

    @Test
    fun testAnyCharMulti(){
        checkRegex("...")
    }

    @Test
    fun testAnyCharMixed(){
        checkRegex(".a.b.c.")
    }

    @Test
    fun testParentheses(){
        checkRegex("()")
    }

    @Test
    fun testParenthesesWithText(){
        checkRegex("(hello)")
    }

    @Test
    fun testParenthesesSequence(){
        checkRegex("(a)(b)(c)")
    }

    @Test
    fun testParenthesesNested(){
        checkRegex("(a(bc)(d))")
    }

    @Test
    fun testParenthesesWithQuantifiers(){
        checkRegex("(a1)*(bc)+(d2)?")
    }

    @Test
    fun testDisjunction(){
        checkRegex("a|b")
    }

    @Test
    fun testDisjunctionSequence(){
        checkRegex("a|b|c|def|gh")
    }

    @Test
    fun testDisjunctionNested(){
        checkRegex("(a(b|c))d")
    }


}