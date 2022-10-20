package info.pmarquezh.junjo.model.sequence

//   Standard Libraries Imports
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

//   Third Party Libraries Imports


//   ns Framework Imports


//   Domain Imports


/**
 * SequenceDTO.kt<br></br><br></br>
 * Creation Date 2022-04-06 10:31<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-04-06 10:31<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-04-06 10:31
 */
data class SequenceDTO ( @field:NotBlank ( message = "{validation.sequenceName}" ) val sequenceName: String,
                         @field:NotBlank ( message = "{validation.pattern}" ) val pattern: String,
                         @field:Min ( value = 0, message = "{validation.currentNumericSequence}" ) val currentNumericSequence: Int,
                         @field:Min ( value = 0, message = "{validation.currentAlphaSequence}" ) val currentAlphaSequence: Int,
                         val currentAlphaRepresentation:String,
                         @field:NotBlank ( message = "{validation.priorityType}" ) val priorityType: String )
