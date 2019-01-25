package ke.co.citesoft.finditkenya.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.citesoft.finditkenya.R
import ke.co.citesoft.finditkenya.data.model.Items
import ke.co.citesoft.finditkenya.ui.base.BaseActivity
import ke.co.citesoft.finditkenya.ui.inquiry.MemberInquiryActivity
import ke.co.citesoft.finditkenya.ui.register.MemberRegistrationActivity
import kotlinx.android.synthetic.main.main_activity.*
import java.util.ArrayList

class MainActivity : BaseActivity() {

    // Initializing an empty ArrayList to be filled with animals


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }*/


        //add menu list items
        var mItems = addMenuItem()

        // Creates a vertical Layout Manager
        rvMain.layoutManager = LinearLayoutManager(this)
        // Access the RecyclerView Adapter and load the data into it
        rvMain.adapter = MainActivityAdapter(mItems, {itemList: Items -> itemClicked(itemList)})


    }

    fun addMenuItem(): List<Items>{
        var itemList = ArrayList<Items>()
        itemList.add(Items("Member Inquiry"))
        itemList.add(Items("Member Registration"))

        return itemList
    }

    private fun itemClicked(itemList : Items) {
        // Launch second activity, pass part ID as string parameter
        if(itemList.Title.equals("Member Inquiry")){
            val intent = Intent(this, MemberInquiryActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, itemList.Title)
            startActivity(intent)
        } else{
            val intent = Intent(this, MemberRegistrationActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, itemList.Title)
            startActivity(intent)
        }

    }

}
