/**
 * 
 */
package tagging.people
import tagging.*


/**
 * @author nielinjie
 *
 */
class CreatedByTag extends Tag{
	def type='tag.people.createdBy'
	UUID authorId
	String authorBid
}
