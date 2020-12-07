package com.demo.left

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_operate.view.*
import kotlinx.android.synthetic.main.item_title.view.*

class OperateAdapter(
    private var mContext: Context,
    private var list: List<ShortcutOperate>,
    private var listener: OperatorListener
) :
    RecyclerView.Adapter<OperateAdapter.GirdViewHolder>() {
    private var layoutId: Int = 0

    class GirdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView? = itemView.tv_title
        var relativeLayout: RelativeLayout? = itemView.rl_item
        var shortIcon: ImageView? = itemView.iv_shortcut_icon
        var shortName: TextView? = itemView.tv_shortcut_name
        var shortOperator: ImageView? = itemView.iv_shortcut_operate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirdViewHolder {
        when (viewType) {
            1 -> layoutId = R.layout.item_title
            2, 3, 4 -> layoutId = R.layout.item_operate
        }
        return GirdViewHolder(
            itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GirdViewHolder, position: Int) {
        val operate: ShortcutOperate? = list[position]

        when (operate?.type) {
            1 -> {
                //holder.tvTitle?.text = operate.desc
                //★内联函数★ with使用方法 operate属性直接映射到UI上
                with(operate) {
                    holder.tvTitle?.text = desc
                }

                //★内联函数★ run使用方法 let函数和with结合体 ,闭包的方式，返回最后一行代码的值
                holder.tvTitle?.run {
                    text = operate.desc
                }
            }
            3 -> holder.relativeLayout?.visibility = View.INVISIBLE
            2 -> {
                holder.shortIcon?.setImageResource(if (operate.src != -1) operate.src else R.mipmap.icon_default)
                holder.shortName?.text = operate.desc
                if (operate.selected) holder.shortOperator?.setImageResource(R.mipmap.icon_delete)
                holder.shortOperator?.setOnClickListener {
                    listener.onSelectedClick(operate)

                }
            }
            4 -> {
                holder.shortIcon?.setImageResource(if (operate.src != -1) operate.src else R.mipmap.icon_default)
                holder.shortName?.text = operate.desc

                //★内联函数★ let方法2 用来非空判断的统一处理
                holder.shortOperator?.let {
                    it.setImageResource(if (operate.selected) R.mipmap.icon_delete else R.mipmap.icon_add)
                    it.setOnClickListener {
                        listener.onSourceClick(operate)
                    }
                }

            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    interface OperatorListener {
        /**
         * 选中项 点击
         */
        fun onSelectedClick(operate: ShortcutOperate)

        /**
         * 源项 点击
         */
        fun onSourceClick(operate: ShortcutOperate)
    }
}