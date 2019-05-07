package com.andrey.owljena

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private val TAG = "MainAct"
    var controller: OntologyController? = null
    private val RECORD_REQUEST_CODE = 101
    private var isStorageRead = false
    var resultList = ArrayList<String>()

    lateinit var file:File

    override fun onClick(v: View?) {
        val item_id = v!!.id

            when (item_id) {
                R.id.btnGetClass -> {
                    controller!!.swrl(file)
//                    val str = controller!!.getClass(etRequest.text.toString()).nameSpace
//                    resultList.add(str)
//                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
//
//                    lvResult.adapter = adapter
                }
                R.id.btnGetSuperClass->{
                    controller!!.createIndividual(file, "Passenger", "LAMBORGINI")
                    //val str = controller!!.getSuperClass(etRequest.text.toString()).localName

                }
                R.id.btnGetDataProperty->{
                    controller!!.createDatatypeProperty("Passenger", "LAMBORGINI", "IDnumber", "666")

                }
                R.id.btnGetAllClasses->{
                    controller!!.query("PREFIX f: <http://www.owl-ontologies.com/family.owl#> SELECT ?x WHERE { ?x f:parent f:Tom. ?x f:parent f:Jane. }")
                }
//                R.id.btnGetAllClasses->{
//                    val str = controller!!.listClasses()
//                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, str)
//
//                    lvResult.adapter = adapter
//                }
//                R.id.btnCreateDataProperty->{
//                    controller!!.createDatatypeProperty(etRequest.text.toString())
//                    val str = controller!!.listDatatypeProperties()
//                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, str)
//
//                    lvResult.adapter = adapter
//                }
//                R.id.btnCreateIndividual->{
//                    controller!!.createIndividual(controller!!.getClass("Woman"),etRequest.text.toString())
//                    val str = controller!!.listIndividuals()
//                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, str)
//                    lvResult.adapter = adapter
//
//
//                }

            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupPermissions()
        btnGetClass.setOnClickListener(this)
        btnGetSuperClass.setOnClickListener(this)
        btnGetAllClasses.setOnClickListener(this)
        btnGetDataProperty.setOnClickListener(this)
        btnCreateDataProperty.setOnClickListener(this)
        btnCreateIndividual.setOnClickListener(this)
        controller = OntologyController()
        file = File(filesDir, "newOnt.owl")
        //val intent = Intent(this, CrossRoadActivity::class.java)
//        val intent = Intent(this, RoadBuilderActivity::class.java)
        //startActivity(intent)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        } else {
            isStorageRead = true
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    isStorageRead = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_load_ontology -> {
                loadFile()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun loadFile() {
        if (isStorageRead) {
            val filePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            filePickerIntent.type = "file/*"
            startActivityForResult(filePickerIntent, 1)
        } else {

            Toast.makeText(this, "Enable permission to read files", Toast.LENGTH_LONG)
                .show()
            setupPermissions()
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    //OntologyProvider.instance.loadOntology(data!!.data!!.path)
                }
            }
        }
    }

}


