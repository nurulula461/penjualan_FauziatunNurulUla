package com.example.penjualan_fauziatunnurulula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_fauziatunnurulula.room.constant
import com.example.penjualan_fauziatunnurulula.room.db_penjualan
import com.example.penjualan_fauziatunnurulula.room.tb_barang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    lateinit var adapterActivity: Adapter_Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pindahBarang()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBarangDao().dataBarang()
            Log.d("MainActivity", "dbResponce : $barang")
            withContext(Dispatchers.Main) {
                adapterActivity.setData(barang)
            }
        }
    }

    fun pindahBarang() {
        btnPlus.setOnClickListener {
            intentEdit(0, constant.TYPE_CREATE)
        }
    }


    fun setupRecyclerView() {
        adapterActivity =
            Adapter_Activity(arrayListOf(), object : Adapter_Activity.onAdapterListener {
                override fun onClick(tbBarang: tb_barang) {
                    intentEdit(tbBarang.id_brg, constant.TYPE_READ)
                }

                override fun onUpdate(tbBarang: tb_barang) {
                    intentEdit(tbBarang.id_brg, constant.TYPE_UPDATE)
                }

                override fun onDelete(tbBarang: tb_barang) {
                   deleteDialog(tbBarang)
                }

            })
        rvBarang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterActivity
        }
    }

    fun intentEdit(tbbarang: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, edit_Activity::class.java)
                .putExtra("intent_id", tbbarang)
                .putExtra("intent_Type", intentType)
        )
    }

    private fun deleteDialog(tbBarang: tb_barang) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("konfirmasi")
            setMessage("yakin hapus ${tbBarang.nama_brg}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBarangDao().deleteBarang(tbBarang)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}


