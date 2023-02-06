package com.example.penjualan_fauziatunnurulula.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [tb_barang::class],
    version = 1
)
abstract class db_penjualan : RoomDatabase() {
    abstract fun tbBarangDao() : tb_barangDao

    companion object{
        @Volatile
        private  var instance : db_penjualan? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(Lock){
            instance ?: buildDatabase(context).also{
               instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            db_penjualan::class.java,
            "tbBarang12345.db"
        ).build()
    }
}