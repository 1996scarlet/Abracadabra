package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.Adapter.ExampleAdapter
import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.Bean.OperationDetail
import ac.ict.humanmotion.abracadabra.R
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.realtimedatafitting.*


class RealTimeDataFitting : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.realtimedatafitting

    private fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    override fun init() {

//        thread {
//            while (true) {
//                // =========== VERY IMPORTANT ============
//                // MainActivity.globalData is the data which you need to handle!!!
//                // Use this to update chat
//                println(MainActivity.globalData.acc[0])


        // UPDATE CHAT use MainActivity.globalData
        // see uApplication-DataFragment for more information
        // Don't forget MainActivity's LifeCircle (especially OnResume/OnPause)
//            }
//        }

        initRecyclerView()

        initAdapter()

        initData()


    }

    private lateinit var mTraceList: MutableList<OperationDetail>

    // 8-14 ADD
    private lateinit var operationAdapter: ExampleAdapter

    fun initAdapter() {
        operationAdapter = ExampleAdapter(this)
        operationAdapter.setItemOnclickListener { v, pos ->
            val s = operationAdapter.getMyDataAt(pos)

            // ADD click handler in 'when' block

            when (v.id) {
                R.id.accept_station -> showToast("accept_station SELECTED, replace with your action")
                // R.id.XXXXX -> println("XXXXX SELECTED")
            }
        }

        recycler_view.adapter = operationAdapter
    }


    //这里是模拟一些假数据，加载数据
    private fun initData() {
        mTraceList = mutableListOf()

        val op = OperationDetail()
        op.type = 1
        op.order = 1
        op.detail = "悬挂检修标牌"
        mTraceList.add(op)

        val op2 = OperationDetail()
        op2.type = 1
        op2.order = 2
        op2.detail = "闭合旁路开关"
        mTraceList.add(op2)

        val op3 = OperationDetail()
        op3.order = 3
        op3.type = 1
        op3.detail = "关闭柜门"
        mTraceList.add(op3)

        operationAdapter.setMyData(mTraceList)

    }

    //初始化显示物流追踪的RecyclerView
    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

}
