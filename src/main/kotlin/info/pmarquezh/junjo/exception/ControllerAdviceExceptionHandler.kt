package info.pmarquezh.junjo.exception


//   Standard Libraries Imports
import java.time.LocalDateTime

//   Third Party Libraries Imports
import mu.KotlinLogging
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

//   ns Framework Imports

//   Domain Imports


/**
 * ControllerAdviceExceptionHandler.kt<br></br><br></br>
 * Creation Date 2022-06-04 12:50<br></br><br></br>
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
 * Version Date: 2022-06-04 12:50<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-06-04 12:50
 */
@RestControllerAdvice
class ControllerAdviceExceptionHandler : ResponseEntityExceptionHandler ( ) {

    private val logger = KotlinLogging.logger { }
    @ExceptionHandler ( NoSuchElementException::class )
    fun handleNoSuchElementException ( ex: NoSuchElementException, request: WebRequest ): ResponseEntity<SingleErrorResponse> {
        logger.debug ( "Request  : $request" )
        logger.debug ( "Exception: $ex" )
        val response = handleFourOhFours(request)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultSetException(
        ex: EmptyResultDataAccessException,
        request: WebRequest
    ): ResponseEntity<SingleErrorResponse> {
        logger.debug("Request  : $request")
        logger.debug("Exception: $ex")
        val response = handleFourOhFours(request)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    private fun handleFourOhFours(request: WebRequest): SingleErrorResponse {
        return SingleErrorResponse(
            LocalDateTime.now(),
            "Not Found",
            404,
            (request as ServletWebRequest).request.requestURI,
            "Resource not found."
        )
    }

    @ExceptionHandler ( Exception::class, RuntimeException::class, DataAccessException::class, DataIntegrityViolationException::class )
    fun handleExceptions(ex: Exception, request: WebRequest): ResponseEntity<SingleErrorResponse> {
        logger.debug("Request  : $request")
        logger.debug("Exception: $ex")

        val response = SingleErrorResponse ( LocalDateTime.now ( ),
                                "Internal Server Error",
                                 500,
                                             ( request as ServletWebRequest ).request.requestURI,
                                     "A generic internal exception has occurred, the support team has been notified, please try again later."
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

//    @ExceptionHandler ( RuntimeException::class )
//    fun handleRuntimeExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<SingleErrorResponse> {
//        logger.debug("Request  : $request")
//        logger.debug("Exception: $ex")
//        val response = SingleErrorResponse(
//            LocalDateTime.now(),
//            "Internal Server Error",
//            500,
//            (request as ServletWebRequest).request.requestURI,
//            "A generic internal runtime exception has occurred, the support team has been notified, please try again later."
//        )
//        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
//    }
//
//    /**
//     * EXPERIMENTAL
//     * @param ex
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(DataAccessException::class, DataIntegrityViolationException::class)
//    fun handleDatabaseErrors(ex: Exception, request: WebRequest): ResponseEntity<SingleErrorResponse> {
//        logger.debug("Request  : $request")
//        logger.debug("Exception: $ex")
//        val response = SingleErrorResponse(
//            LocalDateTime.now(),
//            "Internal Server Error",
//            500,
//            (request as ServletWebRequest).request.requestURI,
//            "An internal database error has occurred, the support team has been notified, please try again later."
//        )
//        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
//    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val fieldErrorDetails = ex.bindingResult
                                  .fieldErrors
//                                  .stream ( )
                                  .map { error: FieldError -> ErrorMessageDto ( error.objectName,
                                                                                error.field,
                                                                                error.defaultMessage!!,
                                                                                ObjectUtils.nullSafeToString ( error.rejectedValue ) )
                                       }
                                  .toList ( )

        val globalErrorDetails = ex.bindingResult
                                   .globalErrors
//                                   .stream()
                                   .map { error: ObjectError -> ErrorMessageDto ( error.objectName,
                                                                                  error.defaultMessage!! )
                                        }
                                   .toList ( )

        val validationErrorDetails: MutableList<ErrorMessageDto> = ArrayList ( )
            validationErrorDetails.addAll ( fieldErrorDetails )
            validationErrorDetails.addAll ( globalErrorDetails )

        val response = ErrorResponse ( LocalDateTime.now ( ),
                                       status.name,
                                       status.value ( ),
                                       ( request as ServletWebRequest ).request.requestURI,
                                       validationErrorDetails )

        return ResponseEntity ( response, status )

    }

}