package info.pmarquezh.junjo.repository

import info.pmarquezh.junjo.model.sequence.SequenceRec
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

//   Third party Imports
//   Domain Imports
/**
 * SequenceRepository.kt<br></br><br></br>
 * Creation Date 2022-02-08 17:11<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 * Class for Database Configuration, this class must be removed and use a safer way to implement DB connections.
 *
 * Useful for prototyping and initial testing, when deciding what the final server will be, you know what to do..
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-02-08 17:11<br></br>
 * @author Paulo Márquez Herrero</td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author Paulo Márquez Herrero
 * @version 1.0 - 2022-02-08 17:11
 */
@Repository
interface SequenceRepository : CrudRepository<SequenceRec?, String?>