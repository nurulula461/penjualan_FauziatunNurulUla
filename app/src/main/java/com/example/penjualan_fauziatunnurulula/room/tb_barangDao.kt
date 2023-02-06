package com.example.penjualan_fauziatunnurulula.room

import androidx.room.*

@Dao
interface tb_barangDao {
    @Insert
    fun addBarang (tbBarang: tb_barang)

    @Update
    fun updateBarang (tbBarang: tb_barang)

    @Delete
    fun deleteBarang (tbBarang: tb_barang)

    @Query("SELECT * FROM tb_barang")
    fun dataBarang (): List<tb_barang>

    @Query("SELECT * FROM tb_barang WHERE id_brg =:tbbarang_id")
    fun tampilBarang (tbbarang_id: Int): List<tb_barang>
}