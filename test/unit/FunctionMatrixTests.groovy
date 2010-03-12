/**
 *
 * @author nielinjie
 */
import org.junit.*
import tagging.*
import tagging.util.*
import static org.junit.Assert.*
import static org.junit.Assert.assertThat as at
import static org.hamcrest.CoreMatchers.*

class FunctionMatrixTests {
    @Test void functionMatrix(){
        FunctionMatrix.registerFuctionStub(new FunctionStub(keys:['brief','display'],memo:'displayed in list view, should return a object has getPanel() method'))
        def all=FunctionMatrix.getAllFunctionStubs()
        at(all.size,is(1))

    }
}

