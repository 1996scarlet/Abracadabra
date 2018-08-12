package ac.ict.humanmotion.abracadabra.Fragment

import ac.ict.humanmotion.abracadabra.HTTPAround.MyTemplateObserver
import ac.ict.humanmotion.abracadabra.R
import android.view.View
import android.widget.Toast
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView
import kotlinx.android.synthetic.main.fragment_main_list.*

/**
 * Project AndroidCA.
 * Created by 旭 on 2017/6/13.
 */

abstract class ListInfoFragment : BaseFragment(), View.OnClickListener {

    protected lateinit var stringObserver: MyTemplateObserver<String>

    override val layoutId: Int
        get() = R.layout.fragment_main_list

    override fun onStop() {
        super.onStop()
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
    }

    override fun init() {
        //initCloudRxJava();
        initRecyclerView()
        initStringObserver()
        initAdapter()
        initData()

        fab.setOnClickListener { onFabClick() }
    }

    private fun initStringObserver() {
        stringObserver = object : MyTemplateObserver<String>() {
            override fun onNext(t: String) {
                Toast.makeText(mActivity, t, Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                pullLoadMoreRecyclerView.refresh()
            }

            override fun onComplete() {
                pullLoadMoreRecyclerView.refresh()
            }
        }
    }

    private fun initRecyclerView() {

        pullLoadMoreRecyclerView.setLinearLayout()

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(object : PullLoadMoreRecyclerView.PullLoadMoreListener {
            override fun onRefresh() {
                initData()
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
            }

            override fun onLoadMore() {
                updateData()
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted()
            }
        })
    }

    protected abstract fun updateData()

    protected abstract fun initAdapter()

    protected abstract fun initData()

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.fab -> onFabClick()
        }
    }

    protected abstract fun onFabClick()
}
