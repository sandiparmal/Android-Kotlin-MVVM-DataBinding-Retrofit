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
import infosys.com.kotlinmvvmsample.viewmodel.FactListViewModel

class FactListFragment: Fragment() {
    private var factAdapter: FactAdapter? = null
    private var binding: FragmentFactListBinding? = null
    private var factList= mutableListOf<Fact>()
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fact_list, container, false)
        factAdapter = FactAdapter(factList)
        binding?.factList?.adapter = factAdapter
        binding?.isLoading = true
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(FactListViewModel::class.java)

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: FactListViewModel) {
        // Update the list when the data changes
        viewModel.getFactListObservable().observe(this, Observer<FactResponse> { fact ->
            if (fact != null) {

                //get title & rows from factResponse
                val title = fact.title
                (activity as AppCompatActivity).supportActionBar!!.title = title
                binding?.factList?.setItemViewCacheSize(fact.rows.size)
                binding?.isLoading = false
                factList.clear()
                factList.addAll(fact.rows)
                factAdapter?.notifyDataSetChanged()
            }
        })
    }
    companion object {
        val TAG = "FactListFragment"
    }
}