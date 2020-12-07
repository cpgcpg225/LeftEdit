package com.demo.left

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.activity_shortcut_operate.*

class ShortcutOperateActivity : BaseActivity(), OperateAdapter.OperatorListener {
    private lateinit var list: List<ShortcutOperate>
    private lateinit var adapter: OperateAdapter
    private var temp = ShortcutOperate()

    override fun getLayoutId(): Int {
        return R.layout.activity_shortcut_operate
    }

    override fun init() {
        list = DataUtil.getDefaultList()
        iv_edit_back.setOnClickListener { finish() }
        adapter = OperateAdapter(this, list, this)
        val manager = GridLayoutManager(this, 4)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(i: Int): Int {
                return if (list[i].type == 1) 4 else 1
            }
        }
        selectedItemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private val selectedItemTouchHelper = ItemTouchHelper(object : Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
            if (recyclerView.layoutManager is GridLayoutManager && viewHolder.adapterPosition in 1..9 && list[viewHolder.adapterPosition].src != -1) {
                val dragFlags =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }
            return 0
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            if (fromPosition in 1..9 && toPosition in 0..9 && list[fromPosition].src != -1 && list[toPosition].src != -1) {
                //交换 中缀表达式
                list[fromPosition] swap (list[toPosition])

                adapter.notifyDataSetChanged()
                return true
            }

            return false
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {}
    })

    /**
     * 【扩展函数】infix关键字，一个参数
     * 交换内容
     */
    infix fun ShortcutOperate.swap(to: ShortcutOperate) {
        temp.src = this.src
        temp.desc = this.desc

        this.src = to.src
        this.desc = to.desc

        to.src = temp.src
        to.desc = temp.desc
    }

    /**
     * 【扩展函数】
     * 数据置空
     */
    private fun ShortcutOperate.setNull() {
        /*    this.desc = ""
            this.src = -1
            this.selected = false*/

        //★内联函数★ let用法1 在函数体内使用it 代替object去访问其公有属性和方法
        this.let {
            desc = ""
            src = -1
            selected = false
        }
    }

    /**
     * 【扩展函数】
     * 数据克隆
     */
    private infix fun ShortcutOperate.copyFrom(from: ShortcutOperate) {
        this.desc = from.desc
        this.src = from.src
        this.selected = from.selected
    }

    /**
     *
     * 移位
     */
    private fun transSeat() {
        for (index in 1..8) {
            val src = list[index].src
            val type = list[index].type

            if (isTransAble(src, type)) {
                list[index] copyFrom list[index + 1]
                list[index + 1].setNull()
            }
        }
    }

    /**【匿名函数】 【Lambda表达式】*/
    val isTransAble = { x: Int, y: Int -> x == -1 && y == 2 }

    /**Lambda 柯里化 函数*/
    val isTransAble1 = { x: Int -> { y: Int -> x == 1 && y == 2 } }

    /**
     * 【高阶函数】
     *
     */
    private fun List<ShortcutOperate>.transSeat(predicate: (ShortcutOperate) -> Boolean) {
        for (index in 1..8) {
            if (predicate(this[index]) && this[index + 1].type == 2) {
                this[index] copyFrom this[index + 1]
                this[index + 1].setNull()
            }
        }
    }

    /**
     * 是否图片为空
     */
    private fun isSrcNull(operate: ShortcutOperate): Boolean {
        return operate.src == -1
    }

    /**【单边表达式】*/
    private fun isSrcNull1(operate: ShortcutOperate) = operate.src == -1

    //★内联函数★ apply 返回对象本身
    private fun testApply1(operate: ShortcutOperate?) {
        //简化对象引用
        operate?.apply {
            selected = true
            src = R.mipmap.icon_add
            type = 1
        }
    }

    private fun testApply2(operate: ShortcutOperate?) {
        /*if (operate !=null &&operate.desc ！= null){
            //操作
       }*/
        //apply 使用方法2 多级判空
        logD("-----------$operate------------")
        operate?.apply {
            logD("operate=$operate")
        }?.desc?.apply {
            logD("operate.src=${operate.desc}")
        }

        //简化
        operate?.desc?.apply {
            logD("简化 operate.src=${operate.desc}")
        }
    }

    //★内联函数★ also 返回对象本身
    private fun testAlso(operate: ShortcutOperate): ShortcutOperate {
        return operate.also {
            it.type = 1
        }.also {
            it.selected = true
        }
    }

    override fun onSelectedClick(operate: ShortcutOperate) {
        if (operate.selected) {
            /**循环*/
            list.forEach {
                if (it.src == operate.src) {
                    it.selected = false
                }
            }

            operate.setNull()
            //list.transSeat { isSrcNull(it) }
            list.transSeat(::isSrcNull)//高阶函数引用
        }
        adapter.notifyDataSetChanged()
    }

    override fun onSourceClick(operate: ShortcutOperate) {
        if (operate.selected) {//选中则删除
            //置空
            for (index in 1..8) {
                if (list[index].src == operate.src) {
                    list[index].setNull()
                    break
                }
            }
            transSeat()
        } else {//未选中则添加
            for (index in 1..9) {
                if (list[index].src == -1) {
                    if (index == 8) {
                        showToast("最多添加7项")
                        return
                    }
                    list[index] copyFrom operate
                    list[index].selected = true
                    break
                }
            }
        }
        operate.selected = !operate.selected
        adapter.notifyDataSetChanged()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@ShortcutOperateActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
