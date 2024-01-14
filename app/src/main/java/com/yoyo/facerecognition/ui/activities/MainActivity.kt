package com.yoyo.facerecognition.ui.activities

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yoyo.facerecognition.R
import com.yoyo.facerecognition.database.misc.PicSection
import com.yoyo.facerecognition.databinding.ActivityMainBinding
import com.yoyo.facerecognition.ui.activities.viewpager.VPAdapter
import com.yoyo.facerecognition.ui.fragments.content.all_pics_fragment.AllPicsFragment
import com.yoyo.facerecognition.ui.fragments.content.facePicsFragment.FacePicsFragment
import com.yoyo.facerecognition.ui.fragments.content.nonFacesPicsFragment.NonFacesPicsFragment
import com.yoyo.facerecognition.ui.viewpager.util.ZoomOutPageTransformer
import com.yoyo.facerecognition.util.SharedPrefsHelper
import com.yoyo.facerecognition.util.getSummeryText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val READ_STORAGE_REQ_CODE = 10245
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        if (checkPermission()) {
            askForPermission()
        }
        normalOperation()
    }

    private fun checkPermission() = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_REQ_CODE)
    }

    private fun setObservers() {
        mainActivityViewModel.showProgressLiveData().observe(this) { showProgress ->
            if (showProgress) {
                //block detect button
                binding.detectButton.isEnabled = false
                mainActivityViewModel.progressLiveData().observe(this) { progress ->
                    binding.detectButton.text = StringBuffer(this.getString(R.string.loading) + " $progress%")
                }
            } else {
                binding.detectButton.isEnabled = true
                binding.detectButton.text = this.getString(R.string.detect)
            }
        }
        mainActivityViewModel.workIsFinishedLiveData().observe(this) { workIsFinished ->
            if (workIsFinished) {
                val sharedPrefsHelper = SharedPrefsHelper.getInstance(this)
                if (!sharedPrefsHelper.isNotificationShowed()) {
                    showDialog(sharedPrefsHelper.getNumberOfFacePics(), sharedPrefsHelper.getNumberOfTotalPics())
                }
                sharedPrefsHelper.setNotificationShowed(false)
            }
        }
    }

    private fun showDialog(numOfFacePics: Int, total: Int) {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.general_dialog_layout)
        val okButton = dialog.findViewById(R.id.ok_button) as TextView
        val summeryText = dialog.findViewById(R.id.dialog_summery_text) as TextView

        summeryText.text = getSummeryText(this, numOfFacePics, total)

        okButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_STORAGE_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //user gave permission
//                    Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                    normalOperation()
                } else {
                    askForPermission()
                }
            }
        }
    }

    private fun normalOperation() {
        val vpAdapter = VPAdapter(supportFragmentManager, lifecycle)

        val fragment1 = AllPicsFragment()
        val fragment2 = FacePicsFragment()
        val fragment3 = NonFacesPicsFragment()

        vpAdapter.addFragment(fragment1)
        vpAdapter.addFragment(fragment2)
        vpAdapter.addFragment(fragment3)

//        binding.viewPager.offscreenPageLimit = vpAdapter.itemCount
        binding.viewPager.adapter = vpAdapter

        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = PicSection.ALL.toString()
                }
                1 -> {
                    tab.text = PicSection.FACES.toString()
                }
                2 -> {
                    tab.text = PicSection.NON_FACES.toString()
                }
            }

        }.attach()


        setObservers()

        binding.detectButton.setOnClickListener {
            //launch worker to do the face recognition job
            if (checkPermission()) {
                return@setOnClickListener
            }
            mainActivityViewModel.onDetectButtonClicked(this)
        }
    }
}