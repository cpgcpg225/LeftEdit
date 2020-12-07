package com.demo.left

object DataUtil{
    /**
     * 获取默认数据
     */
    fun getDefaultList(): List<ShortcutOperate> {
        val list = ArrayList<ShortcutOperate>()
        list.add(ShortcutOperate(type = 1, desc = "已选（0/7）"))

        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 2))
        list.add(ShortcutOperate(type = 3))

        list.add(ShortcutOperate(type = 1, desc = "我的快捷操作"))

        list.add(ShortcutOperate(src =R.mipmap.icon_1, type = 4, desc = "楼房"))
        list.add(ShortcutOperate(src =R.mipmap.icon_2, type = 4, desc = "草房"))
        list.add(ShortcutOperate(src =R.mipmap.icon_3, type = 4, desc = "钥匙"))
        list.add(ShortcutOperate(src =R.mipmap.icon_4, type = 4, desc = "公寓"))
        list.add(ShortcutOperate(src =R.mipmap.icon_5, type = 4, desc = "大厦"))
        list.add(ShortcutOperate(src =R.mipmap.icon_6, type = 4, desc = "蘑菇"))
        list.add(ShortcutOperate(src =R.mipmap.icon_7, type = 4, desc = "小人"))
        list.add(ShortcutOperate(src =R.mipmap.icon_8, type = 4, desc = "蜗牛"))

        return list
    }
}