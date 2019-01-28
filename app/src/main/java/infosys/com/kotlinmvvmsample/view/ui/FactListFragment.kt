package infosys.com.kotlinmvvmsample.view.ui

import android.support.v4.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import infosys.com.kotlinmvvmsample.R
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.view.adapter.FactAdapter
import infosys.com.kotlinmvvmsample.databinding.FragmentFactListBinding
import infosys.com.kotlinmvvmsample.helpers.Injector
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.utils.ConnectivityUtils
import infosys.com.kotlinmvvmsample.viewmodel.FactListViewModel
import kotlinx.android.synthetic.main.fragment_fact_list.view.*

class FactListFragment : Fragment() {
    private var factAdapter: FactAdapter? = null
    private var binding: FragmentFactListBinding? = null
    private var factList = mutableListOf<Fact>()
    private var rootView: View? = null
    private lateinit var viewModel: FactListViewModel

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fact_list, container, false)

        // Recycler Adapter
        factAdapter = FactAdapter(factList)
        binding?.factList?.adapter = factAdapter
        showLoadingStatus()

        // Swipe refresh listener
        binding?.root?.simpleSwipeRefreshLayout?.setOnRefreshListener {
            // observe data from viewModel
            showLoadingStatus()
            binding?.isLoading = true
            observeViewModel(viewModel)

        }

        rootView = binding?.root
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FactListViewModel::class.java)
        observeViewModel(viewModel)

    }

    private fun observeViewModel(viewModel: FactListViewModel) {
        // Update the list when the data changes
        viewModel.getFactListObservable(activity!!, Injector.provideCache(activity!!)).observe(this, Observer<FactResponse> { fact ->
            hideLoading()
            if (fact != null) {
                //get title & rows from factResponse
                val title = fact.title
                (activity as AppCompatActivity).supportActionBar!!.title = title
                binding?.factList?.setItemViewCacheSize(fact.rows.size)
                binding?.isLoading = false
                binding?.isRecyclerShowing = true

                val mutableList = fact.rows.toMutableList()
                for (iCount in fact.rows.indices) {
                    // if title, description and imageHrel is null then remove item from list
                    if (fact.rows[iCount].imageHref.isNullOrEmpty() && (fact.rows[iCount].title).isNullOrEmpty() && (fact.rows[iCount].description).isNullOrEmpty())
                        mutableList.removeAt(iCount)
                }

                // clear list, add new items in list and refresh it using notifyDataSetChanged
                factList.clear()
                factList.addAll(mutableList)
                factAdapter?.notifyDataSetChanged()
            } else {
                if (ConnectivityUtils.isNetworkAvailable(activity!!)) {
                    binding?.loadingStatus = getString(R.string.unable_fect_facts)
                } else {
                    showNetworkError()
                }
            }
        })
    }

    /**
     * Hide SwipeRefreshLayout loader
     */
    private fun hideLoading() {
        rootView?.simpleSwipeRefreshLayout?.isRefreshing = false
    }

    /**
     * show loading fact status before network or database call
     */
    private fun showLoadingStatus() {
        binding?.loadingStatus = getString(R.string.loading_facts)
        binding?.isLoading = true
        binding?.isRecyclerShowing = false
    }

    /**
     * show network status is network is not available
     */
    private fun showNetworkError() {
        binding?.loadingStatus = getString(R.string.network_error)
    }

}