package com.ilham.mocktest.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.mocktest.R
import com.ilham.mocktest.databinding.FragmentHomeBinding
import com.ilham.mocktest.local.DatabaseToko
import com.ilham.mocktest.model.DataToko
import com.ilham.mocktest.view.adapter.HomeAdapter
import com.ilham.mocktest.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var Vm : HomeViewModel
    lateinit var pref : SharedPreferences
    lateinit var adapter: HomeAdapter
    lateinit var toko : ArrayList<DataToko>
    var datbasetoko :DatabaseToko? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        //ViewModel
        adapter = HomeAdapter(requireActivity(),ArrayList())

        binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = adapter

        //LiveData
        Vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        Vm.getNoteObservers().observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it as ArrayList<DataToko>)
        })
        //Room
        datbasetoko = DatabaseToko.getInstance(requireContext())
        //Add Note
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_tambahFragment)

        }



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ascending -> {
                getNoteAscend()
                return true
            }
            R.id.descending -> {
                getNoteDescend()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getNoteAscend() {
        GlobalScope.launch {
            var data = datbasetoko?.tokoDao()?.getTokoASC()
            activity?.runOnUiThread {
                adapter = HomeAdapter(requireActivity(),data!!)
                binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvMain.adapter = adapter
            }
        }
    }

    fun getNoteDescend() {
        GlobalScope.launch {
            var data = datbasetoko?.tokoDao()?.getTokoDESC()
            activity?.runOnUiThread {
                adapter = HomeAdapter(requireActivity(),data!!)
                binding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvMain.adapter = adapter
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = datbasetoko?.tokoDao()?.getTokoASC()
            activity?.runOnUiThread {
                adapter = HomeAdapter(requireActivity(), data!!)
                binding.rvMain.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvMain.adapter = adapter
            }
        }
    }




}