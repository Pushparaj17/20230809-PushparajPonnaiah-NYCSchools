package com.pushparaj.assignment.nycschools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pushparaj.assignment.nycschools.adapter.SchoolListAdapter
import com.pushparaj.assignment.nycschools.databinding.FragmentFirstBinding
import com.pushparaj.assignment.nycschools.repository.SchoolResourceData
import com.pushparaj.assignment.nycschools.repository.SchoolResourceLoading
import com.pushparaj.assignment.nycschools.ui.OnItemClickListener
import com.pushparaj.assignment.nycschools.ui.SchoolListViewModel
import com.pushparaj.assignment.nycschools.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * A Fragment displays the school list.
 */
@AndroidEntryPoint
class SchoolListFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val schoolListViewModel: SchoolListViewModel by viewModels()

    private lateinit var schoolListAdapter: SchoolListAdapter
    private lateinit var schoolListView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        schoolListView = this._binding!!.schoolList
        schoolListAdapter = SchoolListAdapter(arrayListOf(), this)
        schoolListView!!.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schoolListView?.adapter = schoolListAdapter

        schoolListViewModel?.schools?.observe(viewLifecycleOwner, Observer { schoolResource ->
            if (schoolResource is SchoolResourceLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else if (schoolResource is SchoolResourceData) {
                binding.progressBar.visibility = View.GONE
                schoolListAdapter?.setSchoolList(schoolResource.schools)
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireActivity(), "", Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val school = schoolListAdapter.schools.get(position)
        val bundle = Bundle()
        bundle.putString(Constants.SCHOOL_DBN, school.dbn)
        bundle.putString(Constants.SCHOOL_NAME, school.school_name)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}

