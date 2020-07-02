package com.yishi.yszf.screenadapter.generator

import com.zy.myretrofitrxdemo.screenadapter.constants.DimenTypes
import com.zy.myretrofitrxdemo.screenadapter.utils.MakeUtils
import java.io.File

object DimenGenerator {
    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)保证和设计一致
     */
    private const val DESIGN_WIDTH = 375
    /**
     * 设计稿的高度  （将自己设计师的设计稿的高度填入）保证和设计一致
     */
    private const val DESIGN_HEIGHT = 667

    @JvmStatic
    fun main(args: Array<String>) {
        val smallest = if (DESIGN_WIDTH > DESIGN_HEIGHT) DESIGN_HEIGHT else DESIGN_WIDTH //     求得最小宽度
        val values: Array<DimenTypes> = DimenTypes.values()
        for (value in values) {
            val directory = File("app/src/main/res") //为了方便，设定为当前文件夹，dimens文件将会生成项目所在文件夹中，用户可自行更改
            MakeUtils.makeAll(smallest, value, directory.absolutePath)
        }
    }
}