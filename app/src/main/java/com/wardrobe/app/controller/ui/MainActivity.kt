package com.wardrobe.app.controller.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import com.wardrobe.app.R
import com.wardrobe.app.controller.adapter.ShirtDeckAdapter
import com.wardrobe.app.controller.adapter.TrouserDeckAdapter
import com.wardrobe.app.controller.interfaces.SimpleSwipeListener
import com.wardrobe.app.controller.utils.BitmapUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mShirtAdapter = ShirtDeckAdapter()
    private val mTrouserAdapter = TrouserDeckAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setElements()
        setListeners()
    }

    private fun setElements() {
        swipe_deck_shirt.adapter = mShirtAdapter
        swipe_deck_trouser.adapter = mTrouserAdapter
    }

    fun randomizeClothes(v: View) {
        val random = Random()

        mShirtAdapter.clearData()
        mShirtAdapter.addClothAndGetData((BitmapUtils(assets).getBitmapFromAssets( "s" + random.nextInt(5) + ".webp")))
        ivShirt.visibility = View . INVISIBLE

        mTrouserAdapter.clearData()
        mTrouserAdapter . addClothAndGetData (BitmapUtils(assets).getBitmapFromAssets("t" + random.nextInt(5) + ".webp"))
        ivTrouser . visibility = View . INVISIBLE
        resetStacks()
    }

    fun addTrouser(v: View) {
        if (permissionGranted()) {
            showImagePicker(ADD_TROUSER)
        } else {
            requestPermission()
        }
    }

    fun addShirt(v: View) {
        if (permissionGranted()) {
            showImagePicker(ADD_SHIRT)
        } else {
            requestPermission()
        }
    }

    private fun resetStacks() {
        swipe_deck_shirt.resetStack()
        swipe_deck_trouser.resetStack()
    }

    private fun permissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
                &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
    }

    private val PERMISSION_REQUEST = 100

    private fun requestPermission() {
        // Permission is not granted
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST)

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onPermissionGranted()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    Manifest.permission.CAMERA)
                            &&
                            ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // case 4 User has denied permission but not permanently
                        onPermissionDenied()
                    } else {
                        // case 5. Permission denied permanently.
                        onPermissionPermanentlyDenied()
                    }
                }
                return
            }
        }
    }

    private fun onPermissionDenied() {
        Snackbar.make(clRoot, "Permission denied.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun onPermissionPermanentlyDenied() {
        Snackbar.make(clRoot, "Please go to settings to grant permissions.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun onPermissionGranted() {
        Snackbar.make(clRoot, "Permission granted. Please click \"+\" again to capture or select image.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun showImagePicker(toAdd: Int) {
        val builderSingle = AlertDialog.Builder(this)
        builderSingle.setTitle("Capture or choose image..")

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        arrayAdapter.add("Open Camera")
        arrayAdapter.add("Open Gallery")

        builderSingle.setAdapter(arrayAdapter) { _, p1 ->
            if (p1 == 0) {
                openCamera(toAdd)
            } else if (p1 == 1) {
                pickFromGallery(toAdd)
            }
        }

        builderSingle.show()
    }

    private val REQUEST_CAMERA_ADD_SHIRT = 101
    private val REQUEST_GALLERY_ADD_SHIRT = 102

    private val REQUEST_CAMERA_ADD_TROUSER = 103
    private val REQUEST_GALLERY_ADD_TROUSER = 104

    private val ADD_SHIRT = 105
    private val ADD_TROUSER = 106

    private fun openCamera(toAdd: Int) {
        if (toAdd == ADD_SHIRT) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, REQUEST_CAMERA_ADD_SHIRT)
        } else if (toAdd == ADD_TROUSER) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, REQUEST_CAMERA_ADD_TROUSER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode in REQUEST_CAMERA_ADD_SHIRT..REQUEST_GALLERY_ADD_TROUSER) {
            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    REQUEST_CAMERA_ADD_SHIRT -> {
                        val dataLocal = mShirtAdapter.addClothAndGetData(data!!.extras.get("data") as Bitmap)
                        swipe_deck_shirt.resetStack()
                        mShirtAdapter.data = dataLocal
                        mShirtAdapter.notifyDataSetChanged()
                    }
                    REQUEST_GALLERY_ADD_SHIRT -> {
                        val dataLocal = mShirtAdapter.addClothAndGetData(MediaStore.Images.Media.getBitmap(this.contentResolver, data!!.data))
                        swipe_deck_shirt.resetStack()
                        mShirtAdapter.data = dataLocal
                        mShirtAdapter.notifyDataSetChanged()
                    }
                    REQUEST_CAMERA_ADD_TROUSER -> {
                        val dataLocal = mTrouserAdapter.addClothAndGetData(data!!.extras.get("data") as Bitmap)
                        swipe_deck_trouser.resetStack()
                        mTrouserAdapter.data = dataLocal
                        mTrouserAdapter.notifyDataSetChanged()
                    }
                    REQUEST_GALLERY_ADD_TROUSER -> {
                        val dataLocal = mTrouserAdapter.addClothAndGetData(MediaStore.Images.Media.getBitmap(this.contentResolver, data!!.data))
                        swipe_deck_shirt.resetStack()
                        mTrouserAdapter.data = dataLocal
                        mTrouserAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun pickFromGallery(toAdd: Int) {
        if (toAdd == ADD_SHIRT) {
            val pickPhoto = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, REQUEST_GALLERY_ADD_SHIRT)
        } else if (toAdd == ADD_TROUSER) {
            val pickPhoto = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, REQUEST_GALLERY_ADD_TROUSER)
        }
    }

/*
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val builder = ToolTip.Builder(this, tvHint, clRoot, "Welcome!" +
                "\nTap on shirt or jeans to add your own. " +
                "\nSwipe them to remove.\n" +
                "\nClick \uD83D\uDD04 for surprise!", ToolTip.POSITION_ABOVE)
        ToolTipsManager().show(builder.build())
    }*/

    private fun setListeners() {
        swipe_deck_shirt.setListener(object : SimpleSwipeListener() {
            /**
             * Called when the last view has been dismissed.
             */
            override fun onStackEmpty() {
                mShirtAdapter.clearData()
                ivShirt.visibility = View.VISIBLE
            }
        })

        swipe_deck_trouser.setListener(object : SimpleSwipeListener() {
            /**
             * Called when the last view has been dismissed.
             */
            override fun onStackEmpty() {
                mTrouserAdapter.clearData()
                ivTrouser.visibility = View.VISIBLE
            }
        })
    }
}