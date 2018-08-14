package ac.ict.humanmotion.abracadabra.Adapter

import ac.ict.humanmotion.abracadabra.Bean.OperationDetail
import ac.ict.humanmotion.abracadabra.R
import android.content.Context
import android.widget.TextView

class ExampleAdapter(context: Context?) : BaseAdapter<OperationDetail>(context) {

    override fun getLayoutId(): Int = R.layout.operation_item

    override fun onBindViewHolder(holder: BaseAdapter<OperationDetail>.BaseViewHolder, position: Int) {
        val operation = myData[position]

        // ADD others like this

        (holder.getView(R.id.accept_station) as TextView).text = operation.detail
    }
}