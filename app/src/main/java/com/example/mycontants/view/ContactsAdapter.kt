package com.example.mycontants.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontants.databinding.ContactListItemBinding
import com.example.mycontants.model.ContactModel

class ContactsAdapter constructor(private val contactsList: List<ContactModel>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contactModel: ContactModel){
            binding.tvContactName.text = contactModel.contactName
            binding.tvContactPhoneNumber.text = contactModel.contactPhoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount() = contactsList.size
}
