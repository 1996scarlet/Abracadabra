package ac.ict.humanmotion.abracadabra

import ac.ict.humanmotion.abracadabra.Bean.Operation
import ac.ict.humanmotion.abracadabra.HTTPAround.MyTemplateObserver
import ac.ict.humanmotion.abracadabra.Lpms.ConnectionFragment
import ac.ict.humanmotion.abracadabra.Lpms.ImuStatus
import ac.ict.humanmotion.abracadabra.Lpms.LpmsBData
import ac.ict.humanmotion.abracadabra.Lpms.LpmsBThread
import ac.ict.humanmotion.abracadabra.OCR.OCRActivity
import ac.ict.humanmotion.abracadabra.WorkList.MineFragment
import ac.ict.humanmotion.abracadabra.WorkList.WorkListOperationFragment
import android.Manifest
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.thread

class MainActivity : BaseActivity(), ConnectionFragment.OnConnectListener {


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun init() {
        getStorageAccessPermissions()
        initToolBar()
        initViewPager()
        initBottomNav()

        initLpms()

        initRxJava()
        initSimple()
    }

    // retrofit
    private fun initSimple() {
        cloudAPI.getOperation(offset = 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MyTemplateObserver<List<Operation>>() {
                    override fun onNext(t: List<Operation>) {
                        Log.e("RETROFIT", "OJBK")
                        println(t.toString())//自己解析
                    }
                })
    }

    private fun initLpms() {
        btAdapter = BluetoothAdapter.getDefaultAdapter()
        thread {
            while (!stopPollThread) {
                synchronized(lpmsList) {
                    for (e in lpmsList) {
                        var d: LpmsBData
                        while (e.hasNewData()) {
                            d = e.lpmsBData!!
                            if (lpmsB.address.equals(e.address))
                                imuData = LpmsBData(d)
                        }
                    }
                }
            }
        }
    }

    @TargetApi(23)
    private fun getStorageAccessPermissions() {
        requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION), OCRActivity.RES_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        println("${OCRActivity.PERMISSION_TAG}:onRequestPermissionsResult: ${grantResults[0]}")
        when (requestCode) {
            OCRActivity.RES_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) println("${OCRActivity.PERMISSION_TAG}:permission get!") else {
                println("${OCRActivity.PERMISSION_TAG}:permission denied! ")
                finish()
            }
        }
    }

    private fun initToolBar() {
        main_title.setOnClickListener {
            //            startActivity(Intent(this, MainActivity::class.java).putExtra("Swagger", 100))
        }
    }

    lateinit var connectionFragment: ConnectionFragment

    private fun initViewPager() {

        connectionFragment = ConnectionFragment()

        main_view_pager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                var fragment: Fragment? = null
                when (position) {
                    0 -> fragment = WorkListOperationFragment()
                    1 -> fragment = MineFragment()
                    2 -> fragment = connectionFragment
                    3 -> fragment = MineFragment()
                }
                return fragment!!
            }

            override fun getCount(): Int = 4
        }

        main_view_pager.offscreenPageLimit = 4
    }

    private fun initBottomNav() {
        navigation.material()
                .addItem(android.R.drawable.ic_menu_compass, "F0")
                .addItem(android.R.drawable.ic_menu_report_image, "F1")
                .addItem(android.R.drawable.ic_menu_search, "F2")
                .addItem(android.R.drawable.ic_menu_call, "F3")
                .build().setupWithViewPager(main_view_pager)
    }

    override fun onBackPressed() {
        //back->home
        val i = Intent(Intent.ACTION_MAIN)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        i.addCategory(Intent.CATEGORY_HOME)
        startActivity(i)
    }

    // C++ C++ C++ C++ C++ C++ C++

    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        const val FRAGMENTTAG = "ConnectionFragment"

        var globalData = LpmsBData()
    }

    //LMPS==========================================>

    lateinit var mTimer: Timer
    lateinit var lpmsB: LpmsBThread
    lateinit var btAdapter: BluetoothAdapter

    var isLpmsBConnected = false
    var imuStatus = ImuStatus()
    var handler = Handler()
    var updateFragmentsHandler = Handler()
    var imuData = LpmsBData()

    private val updateRate = 25

    val lpmsList: MutableList<LpmsBThread> = ArrayList()

    var stopPollThread = false

    private val mUpdateFragmentsTask = object : Runnable {
        override fun run() {
            synchronized(imuData) {
                updateFragment(imuData, imuStatus)
                globalData = imuData
            }
            updateFragmentsHandler.postDelayed(this, updateRate.toLong())
        }
    }

    override fun onDestroy() {
        stopPollThread = true

        synchronized(lpmsList) {
            for (e in lpmsList) {
                e.close()
            }
        }

        super.onDestroy()
    }

    override fun onStart() {
        mTimer = Timer()
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { }
            }
        }, 25, 25)

        super.onStart()
    }

    override fun onStop() {
        mTimer.cancel()

        super.onStop()
    }

    override fun onResume() {
        startUpdateFragments()

        super.onResume()
    }

    override fun onPause() {
        stopUpdateFragments()

        super.onPause()
    }


    fun startUpdateFragments() {
        updateFragmentsHandler.removeCallbacks(mUpdateFragmentsTask)
        updateFragmentsHandler.postDelayed(mUpdateFragmentsTask, 100)
    }

    fun updateFragment(d: LpmsBData, s: ImuStatus) {
        connectionFragment.updateView(d, s)
    }

    fun stopUpdateFragments() {
        updateFragmentsHandler.removeCallbacks(mUpdateFragmentsTask)
    }

//    inner class DataAnalysisThread : Runnable {
//        override fun run() {
//            Log.e("Main", "New Data")
//
//        }
//    }

    override fun onConnect(address: String) {
        var id = 0

        synchronized(lpmsList) {
            for (aLpmsList in lpmsList) {
                if (address == aLpmsList.address) {
                    Toast.makeText(baseContext, "$address is already connected.", Toast.LENGTH_SHORT).show()
                    return
                }
                id++
            }

            lpmsB = LpmsBThread(btAdapter)

            lpmsB.setAcquisitionParameters(true, true, false, false, false, true, false)
            if (lpmsB.connect(address, id)) {
                lpmsList.add(lpmsB)

                isLpmsBConnected = true
                imuStatus.measurementStarted = true

                Toast.makeText(baseContext, "Connected to $address", Toast.LENGTH_SHORT).show()

                connectionFragment.confirmConnected(lpmsB.device)
            } else {
                Toast.makeText(baseContext, "Connection to $address failed. Please reconnect.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }


    fun onSensorSelectionChanged(address: String) {
        synchronized(lpmsList) {
            for (e in lpmsList) {
                if (address == e.address) {
                    lpmsB = e
                    Log.e("lpms", "[LpmsBMainActivity] In main activity: " + lpmsB.address)
                    return
                }
            }
        }
    }

    override fun onDisconnect() {
        synchronized(lpmsList) {
            for (e in lpmsList) {
                if (lpmsB.address.equals(e.address)) {
                    Toast.makeText(baseContext, "Disconnected " + e.address, Toast.LENGTH_SHORT).show()
                    e.close()
                    lpmsList.remove(e)
                    if (lpmsList.size == 0) imuStatus.measurementStarted = true
                    return
                }
            }
        }
    }
}
