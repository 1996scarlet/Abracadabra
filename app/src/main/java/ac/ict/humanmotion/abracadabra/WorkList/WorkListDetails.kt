package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.R
import android.content.Intent
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.worklistdetail.*


class WorkListDetails : BaseActivity() {
    override fun init() {
        startOperate.setOnClickListener {
            startActivity(Intent(this@WorkListDetails, RealTimeDataFitting::class.java))
        }
        setSupportActionBar(toolbar)
    }

    override val layoutId: Int
        get() = R.layout.worklistdetail
}
