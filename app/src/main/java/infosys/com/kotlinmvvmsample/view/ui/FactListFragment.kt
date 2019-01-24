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
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.view.adapter.FactAdapter
import infosys.com.kotlinmvvmsample.databinding.FragmentFactListBinding
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
        binding?.factsList?.adapter = factAdapter
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
        viewModel.getProjectListObservable().observe(this, Observer<List<Fact>> { fact ->
            if (fact != null) {
                binding?.isLoading=false
                factList.clear()
                factList.addAll(fact.toMutableList())
                factAdapter?.notifyDataSetChanged()
            }
        })
    }
    companion object {
        val TAG = "FactListFragment"
    }
}