package info.pmarquezh.junjo.web.controller


//   Standard Libraries Imports
import javax.validation.Valid
import javax.validation.constraints.Min

//   Third Party Libraries Imports
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import mu.KotlinLogging

//   FENIX Framework Imports

//   Domain Imports
import info.pmarquezh.junjo.model.sequence.SequenceDTO
import info.pmarquezh.junjo.model.sequence.SequenceRec
import info.pmarquezh.junjo.service.SequenceService

/**
 * SequenceRestController.kt<br></br><br></br>
 * Creation Date 2022-02-08 16:55<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr>
 * <th colspan="2">
 * History
</th> *
</tr> *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-02-08 16:55<br></br>
 * @author pmarquezh
</td> *
 * <td width="80%">
 *
 * Creation
</td> *
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:55
 */
@RestController
@RequestMapping("/junjoAPI/1.0/sequences")
class SequenceRestController {

    private val logger = KotlinLogging.logger { }

    private var sequenceService: SequenceService? = null
    @Autowired
    fun setSequenceRestService(sequenceService: SequenceService?) {
        this.sequenceService = sequenceService
    }

    /**
     * Persists a new Sequence [C].
     * @return
     */
    @PostMapping( path = [""])
    fun persistSequence(@RequestBody sequenceDTO: @Valid SequenceDTO?): ResponseEntity<Void> {
        val newSequenceId = sequenceService!!.persistSequence(sequenceDTO)
        val headers = HttpHeaders()
        headers.add("Location", newSequenceId)
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    /**
     * Retrieves all Sequences [R].
     * @return
     */
    @GetMapping( path = [""])
    fun retrieveSequences(): ResponseEntity<List<SequenceRec?>?> {
        val sequences = sequenceService!!.retrieveSequences()
        return ResponseEntity(sequences, HttpStatus.OK)
    }

    /**
     * Retrieves a Sequence [R].
     * @return
     */
    @GetMapping( path = ["/{sequenceId}"])
    fun retrieveSequence(@PathVariable("sequenceId") sequenceId: String?): ResponseEntity<SequenceRec> {
        val sequence = sequenceService!!.retrieveSequence(sequenceId)
        return ResponseEntity(sequence, HttpStatus.OK)
    }

    /**
     * Updates a Sequence [U].
     * @return
     */
    @PutMapping ( path = ["/{sequenceId}" ] )
    fun updateSequence ( @PathVariable("sequenceId") sequenceId: String?, @RequestBody sequenceDTO: SequenceDTO? ): ResponseEntity<Void> {
        sequenceService!!.updateSequence(sequenceId, sequenceDTO)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /**
     * Deletes a Sequence [D].
     * @return
     */
    @DeleteMapping( path = ["/{sequenceId}"])
    fun deleteSequence(@PathVariable("sequenceId") sequenceId: String?): ResponseEntity<Void> {
        sequenceService!!.deleteSequence(sequenceId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /**
     * Generate next element in the sequence.
     * @return
     */
    @GetMapping( path = ["/{sequenceId}/generate"])
    fun generateNextElementInSequence ( @PathVariable ( "sequenceId" ) sequenceId: String? ): ResponseEntity<String> {
        val generatedElement = sequenceService!!.getNextInSequence(sequenceId)
        return ResponseEntity(generatedElement, HttpStatus.OK)
    }

    /**
     * Generate next "n" elements in the sequence.
     * @return
     */
    @GetMapping ( path = ["/{sequenceId}/generate/{quantity}"] )
    fun generateNextElementsInSequence ( @PathVariable("sequenceId") sequenceId: String?, @Min ( 1 ) @PathVariable ( "quantity" ) quantity: Int ): ResponseEntity<List<String?>?> {

        //   JACK SPARROW WAS HERE BIG TIME!!! @PathVariable validations NOT working. OPEN A TICKET WITH THIS ISSUE RIGHT AWAY
        if ( quantity < 0 ) { return ResponseEntity ( HttpStatus.BAD_REQUEST ) }

        val generatedElements = sequenceService!!.getNextElementsInSequence ( sequenceId, quantity )

        return ResponseEntity ( generatedElements, HttpStatus.OK )

    }

}