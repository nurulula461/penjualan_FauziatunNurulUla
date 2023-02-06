package com.example.penjualan_fauziatunnurulula

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_fauziatunnurulula.room.constant
import com.example.penjualan_fauziatunnurulula.room.db_penjualan
import com.example.penjualan_fauziatunnurulula.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class edit_Activity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    private var tbBarang: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        simpanBarang()
        setupView()
        tbBarang = intent.getIntExtra("intent_id", 0)
        Toast.makeText(this,tbBarang.toString(), Toast.LENGTH_SHORT).show()

    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_Type",0)
        when (intentType) {
            constant.TYPE_CREATE -> {
                btnUpdate.visibility = View.GONE

            }
            constant.TYPE_READ -> {
                btnSimpan.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                membaca()
            }
            constant.TYPE_UPDATE -> {
                btnSimpan.visibility = View.GONE
                membaca()
            }
        }
    }

    fun simpanBarang(){
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDao().addBarang(
                    tb_barang(idBarang.text.toString().toInt(),
                    namaBarang.text.toString(),
                    hargaBarang.text.toString().toInt(),
                    stokBarang.text.toString().toInt())
                )
                finish()
            }
        }
      btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDao().addBarang(
                    tb_barang(idBarang.text.toString().toInt(),
                        namaBarang.text.toString(),
                        hargaBarang.text.toString().toInt(),
                        stokBarang.text.toString().toInt())
                )
                finish()
            }
        }
    }


    fun membaca(){
        tbBarang = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val bayang = db.tbBarangDao().tampilBarang(tbBarang)[0]
            val dataid: String = bayang.id_brg.toString()
            val dataharga: String = bayang.harga_brg.toString()
            val dataStok: String = bayang.stok.toString()
            idBarang.setText(dataid)
            namaBarang.setText(bayang.nama_brg)
            hargaBarang.setText(dataharga)
            stokBarang.setText(dataStok)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}