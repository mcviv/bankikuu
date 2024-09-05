package com.example.e_bankingapplication.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.e_bankingapplication.models.Client
import com.example.e_bankingapplication.navigation.ROUTE_LOGIN
import com.example.e_bankingapplication.navigation.ROUTE_VIEW_CLIENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientViewModel(private val navController: NavController, private val context: Context) {

    private val authRepository: AuthViewModel = AuthViewModel(navController, context)

    init {
        if (!authRepository.isloggedin()) {
            navController.navigate(ROUTE_LOGIN)
        }
    }

    fun saveClient(
        firstname: String, lastname: String, gender: String, age: String,
        bio: String
    ) {
        if (firstname.isEmpty() || lastname.isEmpty() || bio.isEmpty() || gender.isEmpty()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val id = System.currentTimeMillis().toString()
        val clientData = saveClient(firstname, lastname, age, gender, bio)
        val clientRef = FirebaseDatabase.getInstance().getReference("Client/$id")

        clientRef.setValue(clientData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Client added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "ERROR: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun viewClients(
        client: SnapshotStateList<SnapshotStateList<Client>>,
        clients: SnapshotStateList<Client>
    ) {
        val ref = FirebaseDatabase.getInstance().getReference("Clients")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                clients.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Client::class.java)
                    value?.let {

                        clients.add(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateClient(
        firstname: String,
        lastname: String,
        gender: String,
        age: String,
        bio: String,
        id: String
    ) {
        val updateRef = FirebaseDatabase.getInstance().getReference("Client/$id")
        val updateData = updateClient(firstname, lastname, age, gender, bio, id)

        updateRef.setValue(updateData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_CLIENT)
            } else {
                Toast.makeText(context, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_CLIENT)
            }
        }
    }

    fun deleteClient(id: String) {
        // Initialize a ProgressDialog (if desired, or use another indicator)
        val progressDialog = ProgressDialog(context).apply {
            setMessage("Deleting client...")
            setCancelable(false)
            show()
        }

        val delRef = FirebaseDatabase.getInstance().getReference("Client/$id")

        delRef.removeValue().addOnCompleteListener { task ->
            progressDialog.dismiss()

            if (task.isSuccessful) {
                Toast.makeText(context, "Client deleted successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_CLIENT)
            } else {
                Toast.makeText(context, "Deletion failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
