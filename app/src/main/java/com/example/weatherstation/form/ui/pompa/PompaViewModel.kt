package com.example.weatherstation.form.ui.pompa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherstation.model.Pompa
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PompaViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val statusRelayRef = database.getReference("WATER_PUMP/status_RELAY")

    private val _statusRelay = MutableLiveData<String>()
    val statusRelay: LiveData<String> get() = _statusRelay

    init {
        fetchStatusRelay()
    }

    fun fetchStatusRelay() {
        statusRelayRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val statusRelay = snapshot.getValue(String::class.java)
                _statusRelay.value = statusRelay
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                Log.e("Firebase", "Error retrieving data", error.toException())
            }
        })
    }

//    fun sendStatusRelayData(statusRelay: Pompa) {
//        statusRelayRef.setValue(statusRelay).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d("Firebase", "Data successfully written.")
//            } else {
//                Log.e("Firebase", "Failed to write data.", task.exception)
//            }
//        }
//    }

    fun updateStatusRelay(newStatus: String) {
        statusRelayRef.setValue(newStatus).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "status_RELAY updated successfully")
            } else {
                Log.e("Firebase", "Failed to update status_RELAY", task.exception)
            }
        }
    }


//    fun onPump(pump: Pompa) {
//        val on = database.push().key ?: return
//        pump.STATUS_RELAY = on
//
//        database.child("pump_control").child(on).setValue(pump)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Firebase", "Pompa berhasil nyala")
//                } else {
//                    Log.e("Firebase", "Gagal di nyalakan", task.exception)
//                }
//            }
//    }

//    fun offPump(pump: Pompa) {
//        val off = database.push().key ?: return
//        pump.STATUS_RELAY = off
//
//        database.child("pump_control").child(off).setValue(pump)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Firebase", "Pompa berhasil mati")
//                } else {
//                    Log.e("Firebase", "Gagal dimatikan", task.exception)
//                }
//            }
//    }
}