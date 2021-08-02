package com.example.webviewkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.webviewkt.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {
    private var _binding : ActivityIntentBinding? = null
    private val binding get() = _binding!!
    private val TAG : String = "로그"
    private lateinit var getResultText: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textResult.hasFocus()

        /* fire -> MainAct에서 javascript실행 */
        binding.searchGoBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            getResultText.launch(intent)
        }

        /* js result 받을 callback */
        getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                //result로 받아와서 어떻게 사용할건지
                val mString = result.data?.getStringExtra("key")
                Log.d(TAG,"IntentActivity - onCreate()/result: $mString")
                binding.textResult.text = mString
            }
        }


    }
}