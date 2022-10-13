package info.pmarquezh.junjo.mapper

//   Standard Libraries Imports

//   Third Party Libraries Imports
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

//   ns Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceDTO
import info.pmarquezh.junjo.model.sequence.SequenceRec

/**
 * SequenceMapper.kt<br></br><br></br>
 * Creation Date 2022-04-06 10:39<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-04-06 10:39<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-04-06 10:39
 */
@Slf4j
@Component
class SequenceMapper @Autowired constructor(private val modelMapper: ModelMapper) {
    /**
     * Converts a validated DTO to an entity.
     * @param registrationDto
     * @return
     */
    fun convertDtoToEntity(registrationDto: SequenceDTO?): SequenceRec {
        return modelMapper.map(registrationDto, SequenceRec::class.java)
    }

    /**
     * Converts an entity to a DTO.
     * @param sequence
     * @return
     */
    fun convertEntityToDTO(sequence: SequenceRec?): SequenceDTO {
        return modelMapper.map(sequence, SequenceDTO::class.java)
    }
}