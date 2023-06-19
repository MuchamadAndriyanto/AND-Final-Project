package com.finalproject.tiketku.view.DetailPenerbangan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.tiketku.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPenerbanganFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_penerbangan, container, false)
    }


}