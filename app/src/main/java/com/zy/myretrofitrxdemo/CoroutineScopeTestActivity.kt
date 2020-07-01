package com.zy.myretrofitrxdemo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_coroutine_scope_test.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.Runnable
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

/**
 * create by zy on 2020/6/22
 * </p>
 */
fun startTestActivity(activity: Activity) {
    activity.apply {
        startActivity(Intent(activity, CoroutineScopeTestActivity::class.java))
    }
}
class CoroutineScopeTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_scope_test)
//        GlobalScope.launch(Dispatchers.Main) {
//            val bitmap1 = getBitmap("http://swapp-test-images.oss-cn-hangzhou.aliyuncs.com/dynamic-img/20200601/1f04d6a85eb50948a4f578f44942cf60.jpg")
//            val bitmap2 = getBitmap("http://swapp-images.oss-cn-hangzhou.aliyuncs.com/dynamic-img/20180615/48954b03c5cf5ae76ba73a5dd8a9d191.jpg")
//            iv1.setImageBitmap(bitmap1)
//            iv2.setImageBitmap(bitmap2)
//        }
//        test("http://swapp-test-images.oss-cn-hangzhou.aliyuncs.com/dynamic-img/20200601/1f04d6a85eb50948a4f578f44942cf60.jpg")

//        classicIoCode1 {
//            uiCode1()
//        }
//
//        classicIoCode1(false) {
//            uiCode1()
//        }
//
//        GlobalScope.launch(Dispatchers.Main) {
//            ioCode1()
//            uiCode1()
//            ioCode2()
//            uiCode2()
//            ioCode3()
//            uiCode3()
//        }

        GlobalScope.launch(Dispatchers.Main) {
            val data = getData()
            val processData = processData(data)
            textView.text = processData
        }

    }

//    private fun test(url: String) {
//        val param = RequestParams(url)
//        x.http().get(param, object : Callback.CommonCallback<File> {
//            override fun onSuccess(result: File) {
//                Log.d("AAA", "onSuccess")
//                val inputStream = FileInputStream(result)
//                Log.d("AAA", "isMainThread1:${Looper.getMainLooper() == Looper.myLooper()}")
//                val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)
//                Log.d("AAA", "isMainThread2:${Looper.getMainLooper() == Looper.myLooper()}")
////                Thread {
////                    Log.d("AAA", "isMainThread2:${Looper.getMainLooper() == Looper.myLooper()}")
////                    bitmap = BitmapFactory.decodeStream(result)
////                }.start()
//                Log.d("AAA", "isMainThread3:${Looper.getMainLooper() == Looper.myLooper()}")
//                iv1.setImageBitmap(bitmap)
//            }
//
//            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
//            }
//
//            override fun onCancelled(cex: Callback.CancelledException?) {
//            }
//
//            override fun onFinished() {
//            }
//
//        })
//    }
//
//    private suspend fun getBitmap(url: String): Bitmap = withContext(Dispatchers.IO) {
//        val param = RequestParams(url)
//        val bitmapStream = x.http().getSync(param, File::class.java)
//        val stream = FileInputStream(bitmapStream)
//        return@withContext BitmapFactory.decodeStream(stream)
//
////        val urlParam = URL(url)
////        val openConnection = urlParam.openConnection() as HttpURLConnection
////        openConnection.requestMethod = "GET"
////        openConnection.connect()
////        val stream = openConnection.inputStream
////        return@withContext BitmapFactory.decodeStream(stream)
//
//    }


    private val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingDeque())

    private fun classicIoCode1(uiThread: Boolean = true, block: () -> Unit) {
        executor.execute {
//            thread {
            println("Coroutines camp classic io1${Thread.currentThread().name}")
            if (uiThread) {
                runOnUiThread {
                    block()
                }
            } else {
                block()
            }
//            }
        }

    }

    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            println("Coroutines camp ioCode1${Thread.currentThread().name}")
        }
    }

    private fun uiCode1() {
        println("Coroutines camp uiCode1${Thread.currentThread().name}")
    }

    private suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            println("Coroutines camp ioCode2${Thread.currentThread().name}")
        }
    }

    private fun uiCode2() {
        println("Coroutines camp uiCode2${Thread.currentThread().name}")
    }

    private suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            println("Coroutines camp ioCode3${Thread.currentThread().name}")
        }
    }

    private fun uiCode3() {
        println("Coroutines camp uiCode3${Thread.currentThread().name}")
    }

    private suspend fun getData(): String =
            withContext(Dispatchers.IO) {
                return@withContext "hen_coder"
            }

    private suspend fun processData(data: String): String =
            withContext(Dispatchers.IO) {
                return@withContext data.split("_")              // 把"hen_coder" 拆成["hen","coder"]
                        .map { it.capitalize() }                            // 吧["hen","coder"] 改成["Hen","Coder"]
                        .reduce { acc, s -> acc + s }                       // 吧["Hen","Coder"] 改成"HenCoder"
            }


}