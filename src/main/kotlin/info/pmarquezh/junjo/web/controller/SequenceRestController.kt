package info.pmarquezh.junjo.web.controller

import javax.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import info.pmarquezh.junjo.model.sequence.SequenceDTO
import info.pmarquezh.junjo.model.sequence.SequenceRec
import info.pmarquezh.junjo.service.SequenceService
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min

@RestController
@RequestMapping ( "/junjoAPI/1.0/sequences" )
@Validated
class SequenceRestController ( private val sequenceService: SequenceService ) {

    @PostMapping ( path = [""] )
    fun persistSequence ( @Valid @RequestBody sequenceDTO: SequenceDTO ): ResponseEntity<Void> {
        val newSequenceId = sequenceService.persistSequence ( sequenceDTO )
        val headers = HttpHeaders ( )
            headers.add ( "Location", newSequenceId )
        return ResponseEntity ( headers, HttpStatus.CREATED )
    }

    @GetMapping ( path = [""] )
    fun retrieveSequences ( ): ResponseEntity<List<SequenceRec?>?> {
        return ResponseEntity(sequenceService.retrieveSequences(), HttpStatus.OK)
    }

    @GetMapping( path = ["/{sequenceId}"])
    fun retrieveSequence(@PathVariable("sequenceId") sequenceId: String?): ResponseEntity<SequenceRec> {
        return ResponseEntity(sequenceService.retrieveSequence(sequenceId), HttpStatus.OK)
    }

    @PutMapping ( path = ["/{sequenceId}" ] )
    fun updateSequence ( @PathVariable("sequenceId") sequenceId: String?, @RequestBody sequenceDTO: SequenceDTO? ): ResponseEntity<Void> {
        sequenceService.updateSequence(sequenceId, sequenceDTO)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping( path = ["/{sequenceId}"])
    fun deleteSequence(@PathVariable("sequenceId") sequenceId: String?): ResponseEntity<Void> {
        sequenceService.deleteSequence(sequenceId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping( path = ["/{sequenceId}/generate"])
    fun generateNextElementInSequence ( @PathVariable ( "sequenceId" ) sequenceId: String? ): ResponseEntity<String> {
        return ResponseEntity(sequenceService.getNextInSequence(sequenceId), HttpStatus.OK)
    }

    @GetMapping ( path = ["/{sequenceId}/generate/{quantity}"] )
  fun generateNextElementsInSequence ( @PathVariable("sequenceId") sequenceId: String, @PathVariable ( "quantity" ) @Valid @Min( 1 ) quantity: Int ): ResponseEntity<List<String?>?> {
        return ResponseEntity (sequenceService.getNextElementsInSequence ( sequenceId, quantity ), HttpStatus.OK)
    }

}