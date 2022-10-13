package info.pmarquezh.junjo.config

import lombok.extern.slf4j.Slf4j
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//   Standard Libraries Imports
//   Third Party Libraries Imports
//   ns Framework Imports
//   Domain Imports
/**
 * JunjoConfig.kt<br></br><br></br>
 * Creation Date 2022-09-02 17:08<br></br><br></br>
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
 * Version Date: 2022-09-02 17:08<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-09-02 17:08
 */
@Slf4j
@Configuration
class JunjoConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}