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
import com.ilham.mocktest.databinding.FragmentEditBinding
import com.ilham.mocktest.local.DatabaseToko
import com.ilham.mocktest.model.DataToko
import com.ilham.mocktest.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class EditFragment : Fragment() {
    lateinit var binding : FragmentEditBinding
    lateinit var Vm: HomeViewModel
    var dbasetoko: DatabaseToko? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Update Note
        dbasetoko = DatabaseToko.getInstance(requireContext())
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        var gettoko = arguments?.getSerializable("edit") as DataToko
        binding.editNama.setText(gettoko.nama)
        binding.editBanyakBarang.setText(gettoko.banyakbarang)
        binding.editPemasok.setText(gettoko.pemasok)
        binding.editTanggal.setText(gettoko.tanggal)
        binding.idNote.setText(gettoko.id.toString())

        binding.btnUpdate.setOnClickListener {
            edittoko()
        }
    }

    private fun edittoko() {

        GlobalScope.async {
            var getNote = arguments?.getSerializable("edit") as DataToko
            var nama = binding.editNama.text.toString()
            var banyak_barang = binding.editBanyakBarang.text.toString()
            var pemasok = binding.editPemasok.text.toString()
            var tanggal = binding.editTanggal.text.toString()


            val dataInsert = DataToko(getNote.id, nama, banyak_barang, pemasok,tanggal)
            Vm.update(dataInsert)

            activity?.runOnUiThread {
                Toast.makeText(context, "Berhasil menambahkan note", Toast.LENGTH_LONG)
            }
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_editFragment_to_homeFragment)

    }


}