package com.example.penjualan_fauziatunnurulula


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_fauziatunnurulula.room.tb_barang
import kotlinx.android.synthetic.main.activity_adapter.view.*
import kotlinx.android.synthetic.main.activity_edit.view.*

class Adapter_Activity (private val Barang: ArrayList<tb_barang>,private val listener: onAdapterListener):
    RecyclerView.Adapter<Adapter_Activity.BarangViewholder>() {

    class BarangViewholder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewholder {
        return BarangViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BarangViewholder, position: Int) {
       val TbBrg = Barang [position]
        holder.view.et_harga             .text = TbBrg.harga_brg.toString()
        holder.view.et_namabarang.text = TbBrg.nama_brg
        holder.view.cv_barang.setOnClickListener{
            listener.onClick(TbBrg)
        }
        holder.view.ic_edit.setOnClickListener{
            listener.onUpdate(TbBrg)
        }
        holder.view.ic_hapus.setOnClickListener{
          listener.onDelete(TbBrg)
        }
    }

    override fun getItemCount() = Barang.size

    fun setData(list: List<tb_barang>){
        Barang.clear()
        Barang.addAll(list)
        notifyDataSetChanged()
    }
interface onAdapterListener{
    fun onClick(tbBarang: tb_barang)
    fun onUpdate(tbBarang: tb_barang)
    fun onDelete(tbBarang: tb_barang)
  }

}

