package com.zy.myretrofitrxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.zy.myretrofitrxdemo.model.Repo
import com.zy.myretrofitrxdemo.screenadapter.utils.MakeUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val disposable = CompositeDisposable()
    val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//            override fun log(message: String) {
//                // 日志打印
//            }
//
//        })
//        // TODO 测试环境用BODY 发布时用HEADERS
//        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
//            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        val api = retrofit.create(Api::class.java)


        // retrofit方式请求
//        api.listRepos("NeverMore64")
//                .enqueue(object : Callback<List<Repo>> {
//                    override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//                    }
//
//                    override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//                        textView.text = response.body()?.get(0)?.name
//                        api.listRepos("google")
//                                .enqueue(object :Callback<List<Repo>>{
//                                    override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//
//                                    }
//
//                                    override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//
//                                    }
//
//                                })
//                    }
//                })

        // kotlin协程方式请求
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val repos: List<Repo> = api.listReposKt("NeverMore64")
//                textView.text = repos[0].name + "-kt"
//            } catch (e: Exception) {
//                textView.text = e.message
//            }
//        }

        GlobalScope.launch(Dispatchers.Main) {
            // async也是一个协程
            val neverMore = async { api.listReposKt("NeverMore64") }
            // await 是一个挂起函数（保证把任务结果都返回主线程）
//            neverMore.await()[0].name
            val google = async { api.listReposKt("google") }
            textView.text = "${neverMore.await()[0].name} + ${google.await()[0].name}"
        }
        // 协程取消
//        scope.launch {
//            try {
//                val repos: List<Repo> = api.listReposKt("NeverMore64")
//                textView.text = repos[0].name + "-kt"
//            } catch (e: Exception) {
//                textView.text = e.message
//            }
//        }

//        lifecycleScope.launch(Dispatchers.Main) {
//            try {
//                val repos: List<Repo> = api.listReposKt("NeverMore64")
//                textView.text = repos[0].name + "-kt"
//            } catch (e: Exception) {
//                textView.text = e.message
//            }
//        }


        // rxJava方式请求
        // rxJava 被用来：1.并发任务的处理 2. 管理事件流
//        api.listReposRx("NeverMore64").subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : SingleObserver<List<Repo>> {
//                    override fun onSuccess(repos: List<Repo>) {
//                        textView.text = repos[0].name + "-rx"
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        disposable.add(d)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        textView.text = e.message
//                    }
//
//                })

//        Single.zip<List<Repo>, List<Repo>, String>(
//                api.listReposRx("NeverMore64"),
//                api.listReposRx("google"),
//                BiFunction { repos1, repos2 -> "${repos1[0].name} - ${repos2[0].name}" }
//        ).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : SingleObserver<String> {
//                    override fun onSuccess(combined: String) {
//                        textView.text = combined
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        disposable.add(d)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        textView.text = e.message
//                    }
//
//                })


    }


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        disposable.dispose()
    }

}
