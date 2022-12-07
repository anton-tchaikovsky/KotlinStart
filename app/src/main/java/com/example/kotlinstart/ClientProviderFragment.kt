package com.example.kotlinstart

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.kotlinstart.ClientProviderFragment.CityMapper.toContentValues



class ClientProviderFragment : Fragment() {

    private fun logInfo(historyEntity: HistoryEntity){
        Log.v("@@@", historyEntity.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val historySource = HistorySource(requireContext().contentResolver)

        activity?.run{
            findViewById<Button>(R.id.insert).setOnClickListener {
                historySource.insert(HistoryEntity(0, "Багамы", 35.0, "rain"))
            }
            findViewById<Button>(R.id.delete).setOnClickListener {
                historySource.delete(HistoryEntity(9))
            }
            findViewById<Button>(R.id.get).setOnClickListener {
                historySource.getHistory().let {
                    for(i in it.indices){
                        logInfo(it[i])
                }
            }
            }
            findViewById<Button>(R.id.getByPosition).setOnClickListener {
               logInfo( historySource.getCityByPosition(9))
            }
            findViewById<Button>(R.id.update).setOnClickListener {
                historySource.update(HistoryEntity(10,  "Гонолулу", 33.0, "rain"))
            }
        }
    }

    companion object {
        fun newInstance() =
            ClientProviderFragment()
            }

    data class HistoryEntity(
        val id: Long = 0,
        val cityName: String = "",
        val temperature: Double = 0.0,
        val condition: String = "",
    )
    object CityMapper {
        private const val ID = "id"
        private const val CITY = "cityName"
        private const val TEMPERATURE = "temperature"
        private const val CONDITION = "condition"
        @SuppressLint("Range")
        fun toEntity(cursor: Cursor): HistoryEntity {
            return HistoryEntity(
                cursor.getLong(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(CITY)),
                cursor.getDouble(cursor.getColumnIndex(TEMPERATURE)),
                cursor.getString(cursor.getColumnIndex(CONDITION))
            )
        }
        fun toContentValues(student: HistoryEntity): ContentValues {
            return ContentValues().apply {
                put(ID, student.id)
                put(CITY, student.cityName)
                put(TEMPERATURE, student.temperature)
                put(CONDITION, student.condition)
            }
        }
    }

    class HistorySource(
        private val contentResolver: ContentResolver // Работаем с Content Provider через этот класс
    ) {
        private var cursor: Cursor? = null

        // Получаем запрос
        private fun query() {
            cursor = contentResolver.query(HISTORY_URI, null, null, null, null)
        }

        fun getHistory():List<HistoryEntity> {
        query()
            val listHistoryEntity:MutableList<HistoryEntity> = mutableListOf()
// Отправляем запрос на получение таблицы с историей запросов и получаем ответ в виде Cursor
            cursor?.let { cursor ->
                for (i in 0..cursor.count) {
// Переходим на позицию в Cursor
                    if (cursor.moveToPosition(i)) {
// Берём из Cursor строку
                       listHistoryEntity.add( CityMapper.toEntity(cursor))
                    }
                }
            }
            cursor?.close()
            return listHistoryEntity.toList()
        }

        // Получаем данные о запросе по позиции
        fun getCityByPosition(position: Int): HistoryEntity {
            query()
            var historyEntity =  HistoryEntity()
            cursor?.let{
                if(it.moveToPosition(position))
                historyEntity = CityMapper.toEntity(it)
            }
            cursor?.close()
            return historyEntity
        }

        // Добавляем новый город
        fun insert(entity: HistoryEntity) {
            contentResolver.insert(HISTORY_URI, toContentValues(entity))
        }

        // Редактируем данные
        fun update(entity: HistoryEntity) {
            val uri: Uri = ContentUris.withAppendedId(HISTORY_URI, entity.id)
            contentResolver.update(uri, toContentValues(entity), null, null)
        }

        // Удалить запись в истории запросов
        fun delete(entity: HistoryEntity) {
            val uri: Uri = ContentUris.withAppendedId(HISTORY_URI, entity.id)
            contentResolver.delete(uri, null, null)
        }

        companion object {
            // URI для доступа к Content Provider
            private val HISTORY_URI: Uri =
                Uri.parse("content://weather.provider/HistoryEntity")
        }

    }

}