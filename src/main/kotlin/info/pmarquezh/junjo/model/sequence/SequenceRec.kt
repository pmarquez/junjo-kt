package info.pmarquezh.junjo.model.sequence

//   Standard Libraries Imports

//   Third Party Libraries Imports
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

//   FENIX Framework Imports

//   Domain Imports

/**
 * SequenceRec.kt<br></br><br></br>
 * Creation Date 2022-02-08 16:47<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 *
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-02-08 16:47<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:47
 */
@Document ( collection = "sequences" )
class SequenceRec ( @Id var id: String? = null,
                    var sequenceName: String? = null,
                    var pattern: String? = null,
                    var currentNumericSequence: Int = 0,
                    var currentAlphaSequence: Int = 0,
                    var currentAlphaRepresentation: String? = null,
                    var priorityType: String? = null ) {

    constructor ( id: String ): this ( id, "", "", 0, 0, "", "" )
    constructor ( id: String, sequenceName: String, pattern: String, currentNumericSequence: Int, currentAlphaSequence: Int, priorityType: String? ): this ( id, sequenceName, pattern, currentNumericSequence, currentAlphaSequence, "", priorityType )

}