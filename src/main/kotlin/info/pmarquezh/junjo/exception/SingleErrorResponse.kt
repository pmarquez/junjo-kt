package info.pmarquezh.junjo.exception

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Data
@NoArgsConstructor
@AllArgsConstructor
class SingleErrorResponse ( val timestamp: LocalDateTime? = null,
                            val httpStatusName: String? = null,
                            val httpStatusCode: Int = 0,
                            val uri: String? = null,
                            val message: String? = null ) { }