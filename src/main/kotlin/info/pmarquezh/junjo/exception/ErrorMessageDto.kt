package info.pmarquezh.junjo.exception

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorMessageDto ( val `object`: String, val field: String, val message: String, val rejectedValue: Any? = null ) {
    constructor ( `object`: String, message: String ): this ( `object`, "", message, "" )
}