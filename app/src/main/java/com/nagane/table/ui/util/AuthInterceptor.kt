import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nagane.table.R
import okhttp3.Interceptor
import okhttp3.Response

// 401 오류 발생 시 인터셉트
class AuthInterceptor(private val context: Context) : Interceptor {

    // 인터셉트는 뷰모델이나 ui 클래스 직접 참조 x
    // 따라서 뷰 모델이 응답 관찰하게 함(전역변수 선언)
    companion object {
        val unauthorizedEvent = MutableLiveData<Boolean>()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            // 401 응답을 받으면 Toast 메시지를 띄우고 3초 후에 앱 종료
            unauthorizedEvent.postValue(true)
            showToastAndExit()
        }

        return response
    }

    private fun showToastAndExit() {
        // 메인 스레드에서 Toast 메시지 띄우기
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.unauthorization_error, Toast.LENGTH_SHORT).show()
        }

        // 3초 후에 앱 종료
        Handler(Looper.getMainLooper()).postDelayed({
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }, 3000) // 3000ms = 3초
    }
}
