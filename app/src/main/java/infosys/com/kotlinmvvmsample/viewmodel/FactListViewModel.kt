package infosys.com.kotlinmvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.service.repository.FactRepository


class FactListViewModel(application: Application): AndroidViewModel(application) {
    //private val factListObservable= FactRepository.getInstance().getFactList()

    /**
     * Expose the LiveData Facts query so the UI can observe it.
     */
    fun getFactListObservable(): LiveData<FactResponse> {
        //return factListObservable
        return FactRepository.getInstance().getFactList();
    }
}