import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.*
import com.andrey.owljena.Models.Category

class ExpandableListViewAdapter(
    private val mContext: Context, private val mExpandableListView: ExpandableListView,
    private val mGroupCollection: List<Category>
) : BaseExpandableListAdapter() {
    private val groupStatus: IntArray = IntArray(mGroupCollection.size)
    internal var isActive: Boolean? = false

    init {
        setListEvent()
    }

    private fun setListEvent() {
        mExpandableListView.setOnGroupExpandListener { arg0 -> groupStatus[arg0] = 1 }
        mExpandableListView.setOnGroupCollapseListener { arg0 -> groupStatus[arg0] = 0 }
    }

    override fun getChild(arg0: Int, arg1: Int): String? {
        return mGroupCollection[arg0].subcategoryArray.get(arg1).subcategoryName
    }

    override fun getChildId(arg0: Int, arg1: Int): Long {
        return arg1.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        arg2: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val childHolder: ChildHolder
        val category = mGroupCollection[groupPosition]
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item_multiple_choice, null)
            childHolder = ChildHolder()
            childHolder.name = convertView!!.findViewById(R.id.text1)
            convertView.tag = childHolder
        } else {
            childHolder = convertView.getTag() as ChildHolder
        }
        childHolder.name!!.setText(category.subcategoryArray[childPosition].subcategoryName)

        if (category.subcategoryArray.get(childPosition).selected) {
            childHolder.name!!.setCheckMarkDrawable(R.drawable.checkbox_on_background)
        } else {
            childHolder.name!!.setCheckMarkDrawable(R.drawable.checkbox_off_background)
        }
        return convertView
    }

    override fun getChildrenCount(arg0: Int): Int {
        return mGroupCollection[arg0].subcategoryArray.size
    }

    override fun getGroup(arg0: Int): Any {
        return mGroupCollection[arg0]
    }

    override fun getGroupCount(): Int {
        return mGroupCollection.size
    }

    override fun getGroupId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getGroupView(groupPosition: Int, arg1: Boolean, view: View?, parent: ViewGroup): View {
        var view = view
        val groupHolder: GroupHolder

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.simple_expandable_list_item_2, null)
            groupHolder = GroupHolder()
            groupHolder.title = view.findViewById(R.id.text1)
            view!!.setTag(groupHolder)
        } else {
            groupHolder = view.getTag() as GroupHolder
        }
        groupHolder.title!!.setText(mGroupCollection[groupPosition].categoryName)
        return view
    }

    internal inner class GroupHolder {
        var title: TextView? = null
    }

    internal inner class ChildHolder {
        var name: CheckedTextView? = null
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(arg0: Int, arg1: Int): Boolean {
        return true
    }
}