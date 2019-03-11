package ke.co.citesoft.finditkenya.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Member(
    var full_name: String = "",
    val phone_number: Int = 0,
    val telephone: Int = 0,
    val occupation: String = "",
    val email: String = "",
    val postal_address: String = "",
    val postal_code: Int = 0,
    val location: String = "",
    var id: String = "",
    @ServerTimestamp val created_date: Timestamp? = null
){
    fun toMap(): Map<String, Any>{
        val member = HashMap<String, Any>()
        //member["id"]= id
        member["full_name"] = full_name
        member["phone_number"] = phone_number
        member["telephone"] = telephone
        member["occupation"] = occupation
        member["email"] = email
        member["postal_address"] = postal_address
        member["postal_code"] = postal_code
        member["location"] = location
        member["created_date"] = created_date?.toDate() ?: Timestamp.now().toDate()

        return member
    }
}
