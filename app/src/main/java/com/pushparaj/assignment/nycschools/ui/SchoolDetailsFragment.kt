package com.pushparaj.assignment.nycschools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pushparaj.assignment.nycschools.databinding.FragmentSecondBinding
import com.pushparaj.assignment.nycschools.db.DAOAccess
import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.model.SchoolDetails
import com.pushparaj.assignment.nycschools.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A Fragment display the school details.
 */

@AndroidEntryPoint
class SchoolDetailsFragment : Fragment() {

    @Inject
    lateinit var daoAccess: DAOAccess

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbn = arguments?.getString(Constants.SCHOOL_DBN)
        val name = arguments?.getString(Constants.SCHOOL_NAME)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = name
        val school = daoAccess.getSchool(dbn)
        var schoolDetails = daoAccess.getSchoolDetails(dbn)

        /**If the details is not available for the selected school, will launch the first school details from the table*/
        if(schoolDetails == null) {
            schoolDetails = daoAccess.getSchoolDetails(1)
        }

        initView(school, schoolDetails)
    }

    private fun initView(school: School, schoolDetail: SchoolDetails) {
        val schoolName = binding.tvName;
        val location = binding.tvLocation;
        val cellPhone = binding.tvCellNumber;
        val email = binding.tvEmail;
        val callIcon = binding.ivDial;

        callIcon.setOnClickListener { view: View? ->
            // Make a phone call
            if (school.phone_number != null) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(
                    "tel: " + school.phone_number.replace("-", "")
                )
                startActivity(intent)
            }
        }

        val emailIcon = binding.ivEmail
        emailIcon.setOnClickListener { view: View? ->
            // Compose the email
            if (school.school_email != null) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:" + school.school_email)
                startActivity(Intent.createChooser(intent, getString(R.string.msg_send_email)))
            }
        }

        schoolName.setText(school.school_name)
        if (school.location != null) {
            val gpsIndex: Int = school.location!!.indexOf(" (")
            location.setText(school.location!!.substring(0, gpsIndex))
        }
        cellPhone.text = school.phone_number
        email.text = school.school_email


        // Update test taker
        val totalLayout = binding.slTotal
        val title = totalLayout.tvExamName
        title.text = getString(R.string.test_take)
        val tlTotalTestTaken = totalLayout.tvScore


        // To update Reading
        val readingLayout = binding.slReading
        val readTitle = readingLayout.tvExamName
        readTitle.text = getString(R.string.avg_reading)
        val tlAvgReading = readingLayout.tvScore

        // To update writing
        val writingLayout = binding.slWriting
        val writeTitle = writingLayout.tvExamName
        writeTitle.text = getString(R.string.avg_writing)
        val tlAvgWriting = writingLayout.tvScore

        // To update writing
        val mathLayout = binding.slMath
        val mathTitle = mathLayout.tvExamName
        mathTitle.text = getString(R.string.avg_math)
        val tlAvgMath = mathLayout.tvScore

        setScores(tlTotalTestTaken, schoolDetail.num_of_sat_test_takers)
        setScores(tlAvgReading, schoolDetail.sat_critical_reading_avg_score)
        setScores(tlAvgWriting, schoolDetail.sat_writing_avg_score)
        setScores(tlAvgMath, schoolDetail.sat_math_avg_score)
    }

    fun setScores(textView: TextView, value: String?) {
        val NA = getString(R.string.na)
        if (value == null || value.isEmpty()) {
            textView.text = NA
        } else {
            textView.text = value
        }
    }
    override fun onResume() {
        super.onResume()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}