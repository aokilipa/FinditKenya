package ke.co.citesoft.finditkenya.ui.inquiry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import ke.co.citesoft.finditkenya.R
import ke.co.citesoft.finditkenya.data.model.Member
import ke.co.citesoft.finditkenya.ui.adapter.MemberAdapter
import ke.co.citesoft.finditkenya.ui.base.BaseActivity
import ke.co.citesoft.finditkenya.ui.register.MemberRegistrationActivity
import kotlinx.android.synthetic.main.activity_member_inquiry.*

class MemberInquiryActivity : BaseActivity() {

    private val TAG = "MemberInquiry"

    private var mAdapter: MemberAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_inquiry)
        firestoreDB = FirebaseFirestore.getInstance()

        loadMemberList()

        firestoreListener = firestoreDB!!.collection("Members")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }

                val memberList = mutableListOf<Member>()

                for (doc in documentSnapshots!!) {
                    val member = doc.toObject(Member::class.java)
                    member.id = doc.id
                    memberList.add(member)
                }

                mAdapter = MemberAdapter(memberList, applicationContext, firestoreDB!!)
                rvNoteList.adapter = mAdapter
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadMemberList() {
        firestoreDB!!.collection("Members")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val memberList = mutableListOf<Member>()

                    for (doc in task.result!!) {
                        val member = doc.toObject<Member>(Member::class.java)
                        member.id = doc.id
                        memberList.add(member)
                    }

                    mAdapter = MemberAdapter(memberList, applicationContext, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    rvNoteList.layoutManager = mLayoutManager
                    rvNoteList.itemAnimator = DefaultItemAnimator()
                    rvNoteList.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == R.id.addNote) {
                val intent = Intent(this, MemberRegistrationActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
