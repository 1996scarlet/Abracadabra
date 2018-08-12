package ac.ict.humanmotion.abracadabra.WorkList

import ac.ict.humanmotion.abracadabra.BaseActivity
import ac.ict.humanmotion.abracadabra.R
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.worklist.*


class SelectWorkListFragment : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.worklist

    override fun init() {
        workListView.apply {
            setOnItemClickListener { _, _, _, _ ->
                startActivity(Intent(this@SelectWorkListFragment, WorkListDetails::class.java))
            }
            adapter = MyAdapter()
        }
    }

    private val images = intArrayOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)
    private val names = arrayOf("工单01号", "工单02号", "工单03号", "工单04号", "工单05号", "工单06号", "工单07号", "工单08号")


    internal inner class MyAdapter : BaseAdapter() {
        override fun getCount(): Int = names.size

        override fun getItem(position: Int): Any = names[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            println("position=$position")
            println(convertView)
            println("------------------------")
            var viewHolder = ViewHolder()
            //通过下面的条件判断语句，来循环利用。如果convertView = null ，表示屏幕上没有可以被重复利用的对象。
            if (convertView == null) {
                //创建View
                convertView = layoutInflater.inflate(R.layout.worklist_items, null)
                viewHolder.iv = convertView!!.findViewById<View>(R.id.worklistlogo) as ImageView
                viewHolder.tv = convertView.findViewById<View>(R.id.workListContent) as TextView
                convertView.tag = viewHolder
            } else {
                viewHolder = convertView.tag as ViewHolder
            }
            viewHolder.iv!!.setImageResource(images[position])
            viewHolder.tv!!.text = names[position]
            return convertView
        }
    }

    internal class ViewHolder {
        var iv: ImageView? = null
        var tv: TextView? = null
    }
}
