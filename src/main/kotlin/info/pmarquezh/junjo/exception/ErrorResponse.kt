package info.pmarquezh.junjo.exception

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse (   val timestamp: LocalDateTime? = null,
                        val httpStatusName: String? = null,
                        val httpStatusCode: Int = 0,
                        val uri: String = "",
                        val errors: List<ErrorMessageDto>? = null ) { }