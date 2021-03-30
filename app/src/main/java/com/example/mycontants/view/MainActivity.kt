package com.example.mycontants.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import com.example.mycontants.R.*
import com.example.mycontants.model.ContactModel


@ExperimentalFoundationApi
class MainActivity : DaggerAppCompatActivity() {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        lifecycle.addObserver(mainViewModel)
        loadContacts()
        setContent {
            ContactsScreen()
        }

//        startService(Intent(baseContext, ContactWatchService::class.java))

    }

    @Composable
    fun ContactsScreen() {
        val contacts: Map<Char, List<ContactModel>> by mainViewModel.contactsList.observeAsState(
            mapOf()
        )
        ContactList(contacts)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    fun ContactList(contactsList: Map<Char, List<ContactModel>>) {
        LazyColumn(Modifier.fillMaxHeight()) {
            contactsList.forEach { (initial, contacts) ->
                stickyHeader {
                    CharacterHeader(text = initial)
                }
                items(contacts) { contact: ContactModel ->
                    ContactListItem(contactModel = contact)
                }
            }
        }
    }

    @Composable
    private fun CharacterHeader(text: Char) {
        Card(backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                Text(text = text.toString(), fontSize = 16.sp, color = Color.White)
            }
        }
    }

    @Composable
    fun ConstraintContactListItem(contactModel: ContactModel) {
        Card {
            ConstraintLayout {

                // Create references for the composables to constrain
                val (button, text) = createRefs()

                Button(
                    onClick = { /* Do something */ },
                    // Assign reference "button" to the Button composable
                    // and constrain it to the top of the ConstraintLayout
                    modifier = Modifier.constrainAs(button) {
                        top.linkTo(parent.top, margin = 16.dp)
                    }
                ) {
                    Text("Button")
                }

                // Assign reference "text" to the Text composable
                // and constrain it to the bottom of the Button composable
                Text("Text", Modifier.constrainAs(text) {
                    top.linkTo(button.bottom, margin = 16.dp)
                })
            }
        }
    }

    @Composable
    private fun ContactListItem(contactModel: ContactModel) {

        Card(backgroundColor = Color.Black) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(
                    Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape,
                        elevation = 2.dp,
                        backgroundColor = Color.LightGray
                    ) {
                        Image(
                            painterResource(id = drawable.ic_user),
                            contentDescription = "",
                            contentScale = ContentScale.None,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Column(modifier = Modifier.apply {
                        align(Alignment.CenterVertically)
                    }) {
                        Text(
                            text = contactModel.contactName,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(
                            text = contactModel.contactPhoneNumber,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    elevation = 2.dp,
                    backgroundColor = Color.LightGray,
                ) {
                    Image(
                        painterResource(id = drawable.ic_call),
                        contentDescription = "",
                        contentScale = ContentScale.None,
                        modifier = Modifier.size(16.dp)
                    )

                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewContactListItem() {
        ContactListItem(
            contactModel = ContactModel(
                contactId = "a",
                contactName = "ahmed abdelhay",
                contactPhoneNumber = "01118150870"
            )
        )
    }

    private fun loadContacts() {

        requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSIONS_REQUEST_READ_CONTACTS
        )
        //callback onRequestPermissionsResult
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainViewModel.getContacts()

            } else {
                Toast.makeText(
                    this,
                    "Permission must be granted in order to display contacts information",
                    Toast.LENGTH_SHORT
                ).show()
                loadContacts()
            }
        }
    }
}