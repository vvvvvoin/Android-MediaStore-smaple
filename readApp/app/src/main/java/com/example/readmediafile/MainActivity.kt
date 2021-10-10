package com.example.readmediafile

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.readmediafile.databinding.ActivityMainBinding
import com.tbruyelle.rxpermissions2.RxPermissions
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = MediaImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerMain.apply {
            this.layoutManager = GridLayoutManager(context, 3)
            adapter = this@MainActivity.adapter
        }

        val disposable = RxPermissions(this).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe {
                if (it.granted) {
                    adapter.setItem(queryImages())
                }
            }

    }

    private fun queryImages(): List<MediaStoreImage> {
        val images = mutableListOf<MediaStoreImage>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )

        // date_taken 은 long 값으로 시간값이 저장되어 있다.
        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(
            dateToTimestamp(2021, 5, 10).toString()
        )

        // sortedlist 에서 처리할 수 있도록 함
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.also { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateTakenColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val dateTaken = Date(cursor.getLong(dateTakenColumn))
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                Log.d(
                    "MainActivity_Log", "id: $id, display_name: $displayName, date_taken: " +
                            "$dateTaken, content_uri: $contentUri"
                )
                val image = MediaStoreImage(id, displayName, dateTaken, contentUri)
                images += image
            }

        }

        cursor?.close()

        return images
    }

    private fun dateToTimestamp(year: Int, month: Int, day: Int): Long {
        return SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).parse("$year.$month.$day")?.time ?: 0
    }
}
