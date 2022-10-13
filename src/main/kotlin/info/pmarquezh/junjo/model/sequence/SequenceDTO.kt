package info.pmarquezh.junjo.model.sequence

//   Standard Libraries Imports
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

//   Third Party Libraries Imports
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import lombok.extern.slf4j.Slf4j

//   ns Framework Imports

//   Domain Imports

/**
 * SequenceDTO.kt<br></br><br></br>
 * Creation Date 2022-04-06 10:31<br></br><br></br>
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
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
class SequenceDTO ( var sequenceName: @NotBlank ( message = "{validation.sequenceName}" ) String? = null,
                    var pattern: @NotBlank ( message = "{validation.pattern}" ) String? = null,
                    var currentNumericSequence: @Min ( value = 0, message = "{validation.currentNumericSequence}" ) Int = 0,
                    var currentAlphaSequence: @Min ( value = 0, message = "{validation.currentAlphaSequence}" ) Int = 0,
                    var currentAlphaRepresentation:String? = "",
                    var priorityType: @NotBlank ( message = "{validation.priorityType}" ) String? = null )
{ }