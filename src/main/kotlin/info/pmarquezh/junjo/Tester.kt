package info.pmarquezh.junjo


/**
 * Tester.java<br><br>
 * Creation Date 2022-10-18 17:40<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-10-18 17:40<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-10-18 17:40
 */
fun main(args: Array<String>)
{
    //declaring a list of elements
    val list = listOf("geeks","for","geeks","hello","world")

    //filtering all words with length > 4
    val longerThan4 = list.filter { it.length == 5 }
    println(longerThan4)

    //declaring a map of string to integers
    val numbersMap = mapOf("key13" to 10, "key25" to 20,
        "key34" to 30, "key45" to 40, "key55" to 50 )

    //filtering the map with some predicates
    val filteredMap = numbersMap.filter { (key, value) -> key.contains ("3" ) && value > 1 }
    println ( filteredMap )
}