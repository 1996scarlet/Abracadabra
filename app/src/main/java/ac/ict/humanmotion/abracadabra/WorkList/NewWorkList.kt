package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.OCR.OCRActivity
import ac.ict.humanmotion.abracadabra.R
import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import kotlinx.android.synthetic.main.buttommenue_layout.*


class NewWorkList() : BaseActivity(), OnClickListener {
    override fun init() {
        pop_layout.setOnClickListener {
            Toast.makeText(applicationContext, "提示：点击窗口外部关闭窗口！",
                    Toast.LENGTH_SHORT).show()
        }

        btn_inputByOCR.setOnClickListener(this)
        btn_inputByEntry.setOnClickListener(this)
        btn_Cancel.setOnClickListener(this)
    }

    override val layoutId: Int = R.layout.buttommenue_layout

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_inputByOCR -> startActivity(Intent(this@NewWorkList, OCRActivity::class.java))
            R.id.btn_inputByEntry -> startActivity(Intent(this@NewWorkList, InputWorkList::class.java))
            R.id.btn_Cancel -> finish()
        }
    }

}