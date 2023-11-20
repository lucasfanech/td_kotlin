package fr.unilasalle.fanech.td_android

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.unilasalle.fanech.td_android.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var userAdapter: UserAdapter
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser la base de données
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").build()

        // Initialiser l'adaptateur de la liste de contacts
        userAdapter = UserAdapter(ArrayList())

        // Initialiser l'adaptateur de la liste de contacts
        contactAdapter = ContactAdapter(ArrayList())

        // Initialiser le ViewModel
        userViewModel = UserViewModelFactory(db).create(UserViewModel::class.java)

        val button = binding.btnIncrement
        val btnSend = binding.btnSend
        var counter = 0

        val btnAddUser = binding.btnAddUser

        btnAddUser.setOnClickListener {
            showAddUserDialog()
        }


        button.setOnClickListener(){
            counter++
            binding.newText.text = counter.toString()
        }

        btnSend.setOnClickListener(){
            val textToShow = binding.textToShow.text.toString()
            if (textToShow != ""){
                val intent = Intent(applicationContext, Activity2::class.java)
                intent.putExtra("textToShow", textToShow)
                startActivity(intent)

            }else{
                // affiche toast message vide
                val toast = Toast.makeText(applicationContext, "Message vide", Toast.LENGTH_SHORT)
                toast.show()

            }
        }

        // liste d'entiers de 0 à 30 à afficher dans une ListView
        val listInt = ArrayList<Int>()
        for (i in 0..30){
            listInt.add(i)
        }

        val recyclerView = binding.listInt
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = NumbersAdapter(listInt)



        // génerer une liste de 3 utilisateurs en db
//        val dateformat = SimpleDateFormat("dd-MM-yyyy")
//        val strdate = "02-04-2001"
//        val newdate: Date = dateformat.parse(strdate)
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val user1 = User(0, "Dupont", "Jean", newdate, "0600000000", 0)
//            val user2 = User(0, "Dupont", "Jean", newdate, "0600000000", 0)
//            val user3 = User(0, "Dupont", "Jean", newdate, "0600000000", 0)
//            insertUser(user1)
//            insertUser(user2)
//            insertUser(user3)
//        }


        // Liste de contacts à afficher dans une RecyclerView
        val recyclerViewContact = binding.listContact
        recyclerViewContact.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewContact.adapter = contactAdapter

        // Observer pour mettre à jour la liste de contacts
        userViewModel.userList.observe(this, { userList ->
            // Mettre à jour la liste d'objets Contact
            val contactList = userList.map { user ->
                val newContact = Contact(user.name, user.firstname, user.phoneNumber, calculateAge(user.birthDate), user.gender)
                println (newContact.age)
                newContact
            }
            contactAdapter.updateList(contactList)
        })

        // Charger les utilisateurs depuis la base de données
        userViewModel.loadUsers()

    }

    // Ajouter cette fonction pour insérer un utilisateur
    private suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            db.userDao().insert(user)
        }
    }

    // Ajouter cette fonction pour récupérer tous les utilisateurs
    private suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            db.userDao().getAllUsers()
        }
    }

    private fun calculateAge(birthDate: Date): Int {
        val today = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.time = birthDate

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    // Ajouter cette fonction pour afficher la boîte de dialogue pour ajouter un utilisateur
    @SuppressLint("MissingInflatedId")
    private fun showAddUserDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_user, null)
        dialog.setView(dialogView)
        dialog.setTitle("Ajouter un nouvel utilisateur")

        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etFirstName = dialogView.findViewById<EditText>(R.id.etFirstName)
        val etBirthDate = dialogView.findViewById<EditText>(R.id.etBirthDate)
        val etPhoneNumber = dialogView.findViewById<EditText>(R.id.etPhoneNumber)
        val etGender = dialogView.findViewById<EditText>(R.id.etGender)

        dialog.setPositiveButton("Ajouter") { _, _ ->
            val name = etName.text.toString()
            val firstName = etFirstName.text.toString()
            val birthDate = etBirthDate.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val gender = etGender.text.toString().toIntOrNull()

            if (name.isNotEmpty() && firstName.isNotEmpty() && birthDate.isNotEmpty() && phoneNumber.isNotEmpty() && gender != null) {
                val newUser = User(
                    name = name,
                    firstname = firstName,
                    birthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(birthDate),
                    phoneNumber = phoneNumber,
                    gender = gender
                )

                userViewModel.insertUser(newUser)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setNegativeButton("Annuler", null)
        dialog.show()
    }

}