/**
 * defined some tag those use didnot care about
 */
package tagging.system



/**
 * @author nielinjie
 *
 */
public class SystemTag extends SystemTag{

}
class UpdatedTag extends SystemTag{
	Date updatedTime
}
class UnreadTag extends SystemTag{
	
}
class ImporterByTag extends SystemTag{
	String importId//the id of importer
}