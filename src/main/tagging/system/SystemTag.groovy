/**
 * defined some tag those use didnot care about
 */
package tagging.system

import tagging.*

/**
 * @author nielinjie
 *
 */
public class SystemTag extends Tag{

}
class UpdatedTag extends SystemTag{
	String type='tag.system.updated'
	Date updatedTime
}
class UnreadTag extends SystemTag{
	String type='tag.system.unread'
}
class ImporterByTag extends SystemTag{
	String type='tag.system.importedBy'
	String importId//the id of importer
}