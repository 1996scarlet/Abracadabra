package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.Fragment.BaseFragment
import ac.ict.humanmotion.abracadabra.R
import android.content.Intent
import kotlinx.android.synthetic.main.worklistoperation.*

class WorkListOperationFragment() : BaseFragment() {
    override fun init() {
        select_work_list.setOnClickListener {
            startActivity(Intent(activity, SelectWorkListFragment::class.java))
        }

        new_work_list.setOnClickListener {
            startActivity(Intent(activity, NewWorkList::class.java))
        }
    }

    override val layoutId: Int = R.layout.worklistoperation
}


