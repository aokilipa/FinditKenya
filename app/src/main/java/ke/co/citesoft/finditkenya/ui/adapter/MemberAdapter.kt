package ke.co.citesoft.finditkenya.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import ke.co.citesoft.finditkenya.R
import ke.co.citesoft.finditkenya.data.model.Member
import ke.co.citesoft.finditkenya.ui.inquiry.MemberInquiryActivity
import ke.co.citesoft.finditkenya.ui.register.MemberRegistrationActivity

class MemberAdapter (
    private val memberList: MutableList<Member>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore):
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_member, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val member = memberList[position]

            holder.title.text = member.full_name
            holder.content.text = member.occupation

            holder.edit.setOnClickListener { updateMember(member) }
            holder.delete.setOnClickListener {
                // Initialize a new instance of
                val builder = AlertDialog.Builder(holder.mContext)

                // Set the alert dialog title
                builder.setTitle("Delete")
                builder.setMessage("Are you sure you want to delete this record?")

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("YES"){dialog, which ->
                    // Do something when user press the positive button
                    Toast.makeText(context.applicationContext,"Record Deleted",Toast.LENGTH_SHORT).show()
                    deleteMember(member.id!!, position)
                }


                // Display a negative button on alert dialog
                builder.setNegativeButton("No"){ _, which ->
                    Toast.makeText(context,"Canceled",Toast.LENGTH_SHORT).show()
                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        override fun getItemCount(): Int {
            return memberList.size
        }

        inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
            internal var title: TextView
            internal var content: TextView
            internal var edit: ImageView
            internal var delete: ImageView

            val mContext: Context

            init {
                title = view.findViewById(R.id.tvTitle)
                content = view.findViewById(R.id.tvContent)

                edit = view.findViewById(R.id.ivEdit)
                delete = view.findViewById(R.id.ivDelete)
                mContext = view.context

            }
        }


        private fun updateMember(member: Member) {
            val intent = Intent(context, MemberRegistrationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("docID", member.id!!)
            intent.putExtra("fullName", member.full_name)
            intent.putExtra("phoneNumber", member.phone_number)
            intent.putExtra("telNumber", member.telephone)
            intent.putExtra("occupation", member.occupation)
            intent.putExtra("email", member.email)
            intent.putExtra("postalAddress", member.postal_address)
            intent.putExtra("postalCode", member.postal_code)
            intent.putExtra("location", member.location)

            context.startActivity(intent)
        }

        private fun deleteMember(id: String, position: Int) {
            firestoreDB.collection("Members")
                .document(id)
                .delete()
                .addOnCompleteListener {
                    memberList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, memberList.size)
                    Toast.makeText(context, "Member has been deleted!", Toast.LENGTH_SHORT).show()
                }
        }

}