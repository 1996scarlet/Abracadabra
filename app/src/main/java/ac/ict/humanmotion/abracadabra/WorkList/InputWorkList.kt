package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.R
import android.widget.Toast
import kotlinx.android.synthetic.main.inputworklist.*

class InputWorkList : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.inputworklist

    override fun init() {
        btn_Confirm.setOnClickListener {
            Toast.makeText(applicationContext, "确认数据并传送到数据库", Toast.LENGTH_LONG).show()
        }

        btn_Cancel.setOnClickListener {
            Toast.makeText(applicationContext, "取消", Toast.LENGTH_LONG).show()
        }
    }
}
