package info.pmarquezh.junjo.service

import info.pmarquezh.junjo.model.sequence.SequenceDTO
import info.pmarquezh.junjo.model.sequence.SequenceRec

interface SequenceService {

    fun persistSequence(sequenceDTO: SequenceDTO): String

    /**
     * Retrieves all sequences in storage [R]
     * @return
     */
    fun retrieveSequences(): List<SequenceRec?>?

    /**
     * Retrieves a sequence by Id [R]
     * @param sequenceId
     * @return SequenceRec The requested sequence record or null if not found.
     */
    fun retrieveSequence(sequenceId: String?): SequenceRec?

    /**
     * Updates a sequence [U]
     * @param sequenceId
     * @return SequenceRec The sequence record to update.
     */
    fun updateSequence(sequenceId: String?, sequenceDTO: SequenceDTO?): String?

    /**
     * Deletes a sequence [D]
     * @param sequenceId
     * @return SequenceRec The sequence record to update or null if not found (null is only informative).
     */
    fun deleteSequence(sequenceId: String?): String?

    /**
     * Generates the next element in the sequence [D]
     * @param sequenceId
     * @return String The generated element from a sequence or null (if sequenceId is not valid/found).
     */
    fun getNextInSequence(sequenceId: String?): String?

    /**
     * Generates the next element in the sequence [D]
     * @param sequenceId
     * @param quantity
     * @return List<String></String> The list of generated elements from a sequence or null (if sequenceId is not valid/found).
     */
    fun getNextElementsInSequence(sequenceId: String?, quantity: Int): List<String?>?
}