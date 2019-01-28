package infosys.com.kotlinmvvmsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import infosys.com.kotlinmvvmsample.database.DatabaseCache
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.service.repository.FactRepository


class FactListViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Facts query so the UI can observe it.
     */
    fun getFactListObservable(context: Context, provideCache: DatabaseCache): LiveData<FactResponse> {
        //return factListObservable
        return FactRepository.getInstance().getFactList(context, provideCache)
    }
}