package infosys.com.kotlinmvvmsample.view.ui

import org.junit.Test
import org.mockito.Mockito

class UnitTestFact{
    var factListFragment = Mockito.mock(FactListFragment::class.java)

    @Test
    fun testShowLoadingStatusCall() {
        Mockito.verify(factListFragment, Mockito.times(1))?.showLoadingStatus()
    }

}