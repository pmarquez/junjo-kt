package info.pmarquezh.junjo.exception

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorDetails (
    private val date: LocalDate? = null,
    private val message: String? = null,
    private val errorDescription: String? = null )
{ }