package ke.co.citesoft.finditkenya.ui.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import ke.co.citesoft.finditkenya.R
import ke.co.citesoft.finditkenya.data.model.Member
import ke.co.citesoft.finditkenya.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_member_registration.*


class MemberRegistrationActivity : BaseActivity() {

    val TAG = "Member"

    var db: FirebaseFirestore?=null
    internal var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_registration)

        //Access Cloud Firestore instance
        db = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("docId","")

            et_fullName.setText(bundle.getString("fullName"))
            et_phoneNumber.setText(bundle.getInt("phoneNumber").toString())
            et_telNumber.setText(bundle.getInt("telNumber").toString())
            et_occupation.setText(bundle.getString("occupation"))
            et_email.setText(bundle.getString("email"))
            et_postalAddress.setText(bundle.getString("postalAddress"))
            et_postalCode.setText(bundle.getInt("postalCode").toString())
            et_location.setText(bundle.getString("location"))

        }

        registerButton.setOnClickListener {
            val fullName = et_fullName.text.toString()
            val phoneNumber =  et_phoneNumber.text.toString().toInt()
            val telNumber = et_telNumber.text.toString().toInt()
            val occupation= et_occupation.text.toString()
            val email = et_email.text.toString()
            val postalAddress= et_postalAddress.text.toString()
            val postalCode = et_postalCode.text.toString().toInt()
            val location = et_location.text.toString()

            if (fullName.isNotEmpty()) {
                if (id.isNotEmpty()) {
                    updateMember(fullName,phoneNumber,telNumber,occupation,email,postalAddress,postalCode,location,id)
                } else {
                    addMember(fullName,phoneNumber,telNumber,occupation,email,postalAddress,postalCode,location)
                }
            }

            finish()

        }



    }

    fun addMember(_fullName: String, _phoneNumber: Int, _telephone: Int, _occupation: String,_email: String, _postal_address: String,
    postal_code: Int,_location: String /*_created_date: Timestamp*/
    ){
        showProgressDialog("registering...")
        val member = Member(_fullName,_phoneNumber,_telephone,_occupation,_email,_postal_address,postal_code,_location).toMap()

        db!!.collection("Members")
            .add(member)
            .addOnSuccessListener { documentReference ->
                hideProgressDialog()
                Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(applicationContext, "Member Registered!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                hideProgressDialog()
                Log.e(TAG, "Error adding Note document", e)
                Toast.makeText(applicationContext, "Failed to register new member!", Toast.LENGTH_SHORT).show()
            }

    }

    private fun updateMember(_fullName: String, _phoneNumber: Int, _telephone: Int, _occupation: String,_email: String, _postal_address: String,
                             postal_code: Int,_location: String ,_id: String) {
        val member = Member(_fullName,_phoneNumber,_telephone,_occupation,_email,_postal_address,postal_code,_location,_id).toMap()

        db!!.collection("Members")
            .document(_id)
            .set(member)
            .addOnSuccessListener {
                Log.e(TAG, "Record update successful!")
                Toast.makeText(applicationContext, "Member details has been updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Note document", e)
                Toast.makeText(applicationContext, "Record could not be updated!", Toast.LENGTH_SHORT).show()
            }
    }
}
