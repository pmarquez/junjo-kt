package info.pmarquezh.junjo.service

//   Standard Libraries Imports

//   Third Party Libraries Imports

//   ns Framework Imports

//   Domain Imports

import info.pmarquezh.junjo.mapper.SequenceMapper
import info.pmarquezh.junjo.model.sequence.SequenceDTO
import info.pmarquezh.junjo.model.sequence.SequenceRec
import info.pmarquezh.junjo.repository.SequenceRepository
import lombok.extern.slf4j.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern


/**
 * SequenceServiceImpl.kt<br></br><br></br>
 * Creation Date 2022-02-08 16:58<br></br><br></br>
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
 * Version Date: 2022-02-08 16:58<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-02-08 16:58
 */
@Slf4j
@Service
class SequenceServiceImpl @Autowired constructor ( private val sequenceRepository: SequenceRepository,
                                                   private val sequenceMapper: SequenceMapper ) : SequenceService {

    @Value("\${info.pmarquezh.junjo.numericPattern}")
    private val numericPatternString: String? = null

    @Value("\${info.pmarquezh.junjo.alphaPattern}")
    private val alphaPatternString: String? = null

    @Value("\${info.pmarquezh.junjo.yearPattern}")
    private val yearPatternString: String? = null

    @Value("\${info.pmarquezh.junjo.defaultNumericPadChar}")
    private val defaultNumericPadChar: String? = null

    /**
     * Persists a new Sequence [C].
     *
     * @param sequenceDTO
     * @return
     */
    override fun persistSequence ( sequenceDTO: SequenceDTO? ): String? {
        var seq = sequenceMapper.convertDtoToEntity ( sequenceDTO )
            seq.id = UUID.randomUUID().toString()

        val sr = sequenceRepository.save(seq)

        return sr.id
    }

    /**
     * Retrieves all sequences in storage [R]
     *
     * @return
     */
    override fun retrieveSequences ( ): List<SequenceRec?>? {

        var sequences: MutableList<SequenceRec?> = ArrayList ( )

        val iSequences = sequenceRepository.findAll ( )
        iSequences.forEach ( Consumer { element: SequenceRec? -> sequences.add ( element )  } )

        return sequences

    }

    /**
     * Retrieves a sequence by ID [R]
     * @param sequenceId
     * @return
     */
    @Throws(NoSuchElementException::class)
    override fun retrieveSequence(sequenceId: String?): SequenceRec? {
        val sequenceWrapper = sequenceRepository.findById(sequenceId!!)
        return sequenceWrapper.get ( )
    }

    /**
     * Updates a sequence [U]
     *
     * @param sequenceId
     * @param sequenceDTO
     * @return SequenceRec The sequence record to update.
     */
    @Throws(NoSuchElementException::class)
    override fun updateSequence(sequenceId: String?, sequenceDTO: SequenceDTO?): String? {
        val dbSequence = retrieveSequence(sequenceId)

        val seq = sequenceMapper.convertDtoToEntity(sequenceDTO)
            seq.id = dbSequence!!.id

        sequenceRepository.save(seq)

        return sequenceId

    }

    /**
     * Deletes a sequence [D]
     *
     * @param sequenceId
     * @return SequenceRec The sequence record to delete or null if not found (null is only informative).
     */
    @Throws(NoSuchElementException::class)
    override fun deleteSequence(sequenceId: String?): String? {
        val dbSequence = retrieveSequence(sequenceId)
        sequenceRepository.delete(dbSequence!!)
        return sequenceId
    }

    /*********************************************************************/
    /*********************************************************************/
    /******* ACTUAL FUNCTIONALITY - BEGIN ********************************/
    /*********************************************************************/
    /*********************************************************************/

    private var sequence: SequenceRec? = null
    private var template: String? = null
    private var numericRollover = false

    /**
     * Generates the next element in the sequence.
     *
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    @Throws(NoSuchElementException::class)
    override fun getNextInSequence ( sequenceId: String? ): String? {

        sequence = retrieveSequence(sequenceId)

        template = sequence!!.pattern

        //   YEAR PATTERN
        if (sequence!!.priorityType == PRIORITY_NUMERIC) {
            template = retrieveNumericPattern() //   NUMERIC PATTERN
            template = retrieveAlphaPattern() //   ALPHA PATTERN

        } else {
            template = retrieveAlphaPattern() //   ALPHA PATTERN
            template = retrieveNumericPattern() //   NUMERIC PATTERN
        }

        template = retrieveYearPattern() //   YEAR PATTERN
        sequenceRepository.save(sequence!!)

        return template
    }

    /**
     * Generates the next elements in the sequence [D]
     *
     * @param sequenceId
     * @param quantity
     * @return List<String> The list of generated elements from a sequence or null (if sequenceId is not valid/found).
    </String> */
    @Throws(NoSuchElementException::class)
    override fun getNextElementsInSequence(sequenceId: String?, quantity: Int): List<String?>? {

        sequence = retrieveSequence(sequenceId)

        val elements: MutableList<String?> = ArrayList ( )

        if (sequence!!.priorityType == PRIORITY_NUMERIC) {
            for (i in 0 until quantity) {
                template = sequence!!.pattern
                template = retrieveNumericPattern() //   NUMERIC PATTERN
                template = retrieveAlphaPattern() //   ALPHA PATTERN
                template = retrieveYearPattern() //   YEAR PATTERN
                elements.add(template)
                numericRollover = false
            }

        } else {
            for (i in 0 until quantity) {
                template = sequence!!.pattern
                template = retrieveAlphaPattern() //   ALPHA PATTERN
                template = retrieveNumericPattern() //   NUMERIC PATTERN
                template = retrieveYearPattern() //   YEAR PATTERN
                elements.add ( template )
                numericRollover = false
            }

        }

        sequenceRepository.save ( sequence!! )

        return elements

    }

    /*  NUMERIC GROUP - BEGIN */
    /**
     *
     * @return
     */
    private fun retrieveNumericPattern(): String? {
        val numericPattern = Pattern.compile(numericPatternString)
        val numericMatcher = numericPattern.matcher(template)
        while (numericMatcher.find()) {
            val numericGroup = numericMatcher.group()
            template = template!!.replace(numericGroup, processNumericGroup(numericGroup, true))
        }
        return template
    }

    /**
     *
     * @param numericGroup
     * @return nextNumberStr
     */
    private fun processNumericGroup(numericGroup: String, increment: Boolean): String {

//        SequenceServiceImpl.log.info("numericGroup: $numericGroup")

        val maxDigitsAllowed = numericGroup.length - 2
        var nextNumber = if (increment) sequence!!.currentNumericSequence + 1 else sequence!!.currentNumericSequence

        if (getDigitsCount(nextNumber) > maxDigitsAllowed) {
            nextNumber = 1
            numericRollover = true
        }

        var nextNumberStr = Integer.toString(nextNumber)
            nextNumberStr = StringUtils.leftPad(nextNumberStr, maxDigitsAllowed, defaultNumericPadChar)

        sequence!!.currentNumericSequence = nextNumber

        return nextNumberStr

    }

    /*  NUMERIC GROUP - END */

    /*  ALPHA GROUP - BEGIN */

    /**
     *
     * @return
     */
    private fun retrieveAlphaPattern(): String? {
        val alphaPattern = Pattern.compile(alphaPatternString)
        val alphaMatcher = alphaPattern.matcher(template)
        while (alphaMatcher.find()) {
            val alphaGroup = alphaMatcher.group()
            template = template!!.replace(alphaGroup, processAlphaGroup(alphaGroup, numericRollover))
        }
        return template
    }

    /**
     *
     * @param alphaGroup
     * @return
     */
    private fun processAlphaGroup(alphaGroup: String, increment: Boolean): String {
  //      SequenceServiceImpl.log.info("alphaGroup: $alphaGroup")

        var currentAlphaSequence = 0
            currentAlphaSequence = if (sequence!!.priorityType == PRIORITY_NUMERIC) {
            if (increment) sequence!!.currentAlphaSequence + 1 else sequence!!.currentAlphaSequence
        } else {
            sequence!!.currentAlphaSequence + 1
        }

        sequence!!.currentAlphaSequence = currentAlphaSequence

        return transformSequenceToRepresentation(currentAlphaSequence)

    }

    /*  ALPHA GROUP - END */

    /*  YEAR GROUP - BEGIN */

    /**
     * Creates the necessary matcher to "match" a YEAR pattern in a sequence template.
     * @return
     */
    private fun retrieveYearPattern(): String? {
        val yearPattern = Pattern.compile(yearPatternString)
        val yearMatcher = yearPattern.matcher(template)
        while (yearMatcher.find()) {
            val yearGroup = yearMatcher.group()
            template = template!!.replace(yearGroup, processYearGroup(yearGroup))
        }
        return template
    }

    /**
     * Returns current year as a String.
     * Either a LONG_YEAR (4 digits year, e.g.: 2022) or a SHORT_YEAR (2 digits year, e.g.: 22).
     * Defaults to LONG_YEAR.
     * @param yearGroup
     * @return
     */
    private fun processYearGroup(yearGroup: String): String {

    //    SequenceServiceImpl.log.info("yearGroup: $yearGroup")

        var yearRepresentation = LocalDate.now().year.toString()

        if (yearGroup.length - 2 == 2) {
            yearRepresentation = yearRepresentation.substring(2, 4)
        }

        return yearRepresentation

    }

    /*  YEAR GROUP - END */

    /**
     *
     * @param num
     * @return
     */
    private fun getDigitsCount(num: Int): Int {
        val numAsString = Integer.toString(num)
        return numAsString.length
    }

    /**
     *
     * @param alphaSequence
     * @return
     */
    private fun transformSequenceToRepresentation(alphaSequence: Int): String {
        return if (alphaSequence < 0) BLANK else transformSequenceToRepresentation(
            alphaSequence / NUM_CHARS_FROM_A_TO_Z - NUMBER_ONE
        ) + (CHAR_FOR_A + alphaSequence % NUM_CHARS_FROM_A_TO_Z).toChar()
    }

    /**               */
    /*****VALIDATIONS */
    /**               */

    /**
     * Validates a UUID is correctly formed.
     * @param theUuid
     * @return
     */
    private fun validateUUID ( theUuid: String? ): Boolean {

        var validUuid = true

        try {
            val uuid = UUID.fromString(theUuid)
//            SequenceServiceImpl.log.info("well formed sequenceID")
//            SequenceServiceImpl.log.info("Variant:" + uuid.variant())
//            SequenceServiceImpl.log.info("Variant:" + uuid.version())
        } catch (exception: IllegalArgumentException) {
            validUuid = false

        }

        return validUuid

    }

    companion object {
        private const val PRIORITY_NUMERIC = "numeric"
        private const val CHAR_FOR_A = 65
        private const val NUM_CHARS_FROM_A_TO_Z = 26
        private const val NUMBER_ONE = 1
        private const val BLANK = ""
    }
}