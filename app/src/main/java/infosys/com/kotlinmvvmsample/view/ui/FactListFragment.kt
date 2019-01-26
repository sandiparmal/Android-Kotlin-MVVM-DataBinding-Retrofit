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
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.utils.ConnectivityUtils
import infosys.com.kotlinmvvmsample.viewmodel.FactListViewModel
import kotlinx.android.synthetic.main.fragment_fact_list.view.*

class FactListFragment : Fragment() {
    private var factAdapter: FactAdapter? = null
    private var binding: FragmentFactListBinding? = null
    private var factList = mutableListOf<Fact>()
    private var rootView: View? = null
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fact_list, container, false)
        factAdapter = FactAdapter(factList)
        binding?.factList?.adapter = factAdapter
        showLoadingStatus()

        binding?.root?.simpleSwipeRefreshLayout?.setOnRefreshListener {
            if (ConnectivityUtils.isNetworkAvailable(activity!!)) {
                showLoadingStatus()
                binding?.isLoading = true
                observeViewModel(viewModel)
            } else {
                showNetworkError()
                binding?.isLoading = true
                binding?.isRecyclerShowing = false
                hideLoading()
            }
        }

        rootView = binding?.root
        return rootView
    }

    private lateinit var viewModel: FactListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FactListViewModel::class.java)
        if (ConnectivityUtils.isNetworkAvailable(activity!!)) {
            observeViewModel(viewModel)
        } else {
            binding?.isLoading = true
            hideLoading()
        }

    }

    private fun observeViewModel(viewModel: FactListViewModel) {
        // Update the list when the data changes
        viewModel.getFactListObservable().observe(this, Observer<FactResponse> { fact ->
            hideLoading()
            if (fact != null) {
                //get title & rows from factResponse
                val title = fact.title
                (activity as AppCompatActivity).supportActionBar!!.title = title
                binding?.factList?.setItemViewCacheSize(fact.rows.size)
                binding?.isLoading = false
                binding?.isRecyclerShowing = true

                val mutableList = fact.rows.toMutableList()
                for (i in fact.rows.indices) {
                    //println(list[i])
                    if (fact.rows[i].imageHref.isNullOrEmpty() && (fact.rows[i].title).isNullOrEmpty() && (fact.rows[i].description).isNullOrEmpty())
                        mutableList.removeAt(i)
                }

                factList.clear()
                factList.addAll(mutableList)
                factAdapter?.notifyDataSetChanged()
            }
        })
    }

    companion object {
        val TAG = "FactListFragment"
    }

    private fun hideLoading() {
        rootView?.simpleSwipeRefreshLayout?.isRefreshing = false
    }

    private fun showLoadingStatus() {
        binding?.loadingStatus = getString(R.string.loading_facts)
        binding?.isLoading = true
        binding?.isRecyclerShowing = false
    }

    private fun showNetworkError() {
        binding?.loadingStatus = getString(R.string.network_error)
    }

}