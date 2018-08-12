package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.Bean.OperationDetail
import ac.ict.humanmotion.abracadabra.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.*


class RealTimeDataFitting : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.realtimedatafitting

    override fun init() {
        initData()
        initRecyclerView()
    }

    private lateinit var mTraceList: MutableList<OperationDetail>


    //这里是模拟一些假数据，加载数据
    private fun initData() {
        mTraceList = ArrayList()
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

    }

    //初始化显示物流追踪的RecyclerView
    private fun initRecyclerView() {
        val traceRv = findViewById<View>(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)
        //        OperationAdapter mAdapter = new OperationAdapter(this, mTraceList);
        //        traceRv.setLayoutManager(layoutManager);
        //        traceRv.setAdapter(mAdapter);
    }

}
