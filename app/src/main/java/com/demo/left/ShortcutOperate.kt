package com.demo.left

/**
 * 数据类
 */
data class ShortcutOperate(
    var src: Int = -1,
    var selected: Boolean = false,
    var type: Int =1,
    var desc: String? = ""
)