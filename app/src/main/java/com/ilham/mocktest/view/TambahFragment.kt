package com.ilham.mocktest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ilham.mocktest.R
import com.ilham.mocktest.databinding.FragmentTambahBinding
import com.ilham.mocktest.local.DatabaseToko
import com.ilham.mocktest.model.DataToko
import com.ilham.mocktest.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TambahFragment : Fragment() {
    lateinit var binding : FragmentTambahBinding
    lateinit var Vm : HomeViewModel
    var dbasetoko : DatabaseToko? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTambahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbasetoko = DatabaseToko.getInstance(requireContext())
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.btnInput.setOnClickListener {
            GlobalScope.async {
                var nama = binding.editNama.text.toString()
                var banyak_barang = binding.editBanyakBarang.text.toString()
                var pemasok = binding.editPemasok.text.toString()
                var tanggal = binding.editTanggal.text.toString()
                val dataInsert = DataToko(0,nama, banyak_barang,pemasok,tanggal)
                Vm.insert(dataInsert)

                activity?.runOnUiThread {
                    Toast.makeText(context,"Berhasil menambahkan data", Toast.LENGTH_LONG).show()
                }
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_tambahFragment_to_homeFragment)
        }
    }


}