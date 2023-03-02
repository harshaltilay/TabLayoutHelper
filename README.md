# TabLayoutHelper.kt
A simple way to work with existing com.google.android.material.tabs.TabLayout

### How to use ?

##### Step 1 add TabLayout in Layout XML.

           <com.google.android.material.tabs.TabLayout
            android:id="@+id/period_tab_layout"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:orientation="horizontal"
            app:tabIndicatorHeight="0dp"
            app:tabMode="fixed"
            app:tabTextAppearance="@android:style/TextAppearance.Widget.TabWidget" />
            
            
##### Step 2 make sure you have <a href="custom_tab.xml">custom_tab.xml</a> included IF YOU WANT TO SHOW NOTIFICATION BADGES.


##### Step 3 Initialize TabLayoutHelper as follows

        _tabLayout = TabLayoutHelper(
        
           tabLayout= binding.categoryTabLayout,
           
           colorSelected = R.color.button_bg_blue,
           
            colorNormal = R.color.gray_1,
            
            font=resources.getFont(R.font.poppinsmedium),
            
            fontSize = 16f,
            
            hasBadge = true  
            
        )
        
##### Step 4 Create ids.xml inside valuse folder. Inside its "<"resources">"  tag add


    <item name="all" type="id" />
    <item name="essential" type="id" />
    <item name="casual" type="id" />
    <item name="anxiety" type="id" />

##### Step 5 Add Tabs and actions to our _tabLayout

_tabLayout.setupTabWithActions(

            listOf(
                Pair("All", R.id.all),
                
                Pair("Essential", R.id.essential),
                
                Pair("Casual", R.id.casual),
                
                Pair("Anxiety", R.id.anxiety)
            )
            
        ) { id: Int ->

           when (id) {
                R.id.essential -> // Todo write your action here
                R.id.casual -> // Todo write your action here
                R.id.anxiety -> // Todo write your action here
                else -> // Todo write your action here
            }

        }
        
##### Step 6 If you have included custom_tab.xml and want to show Notification badge then simply call 

setBadgeValue(tabPosition: Int, badgeValue: String?)
Simply put badgeValue = null if you want to hide the badge.
