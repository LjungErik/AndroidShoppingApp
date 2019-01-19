package com.thetestcompany.presentation.shopping

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorInflater
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.*
import biz.laenger.android.vpbs.BottomSheetUtils
import biz.laenger.android.vpbs.ViewPagerBottomSheetBehavior
import com.google.android.gms.vision.barcode.Barcode
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.checkout.CheckoutActivity
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.common.utils.BarcodeDetectorProcessor
import com.thetestcompany.presentation.common.utils.PermissionsDelegate
import com.thetestcompany.presentation.entities.ShoppingCartItem
import com.thetestcompany.presentation.selectlistforshopping.SelectListForShoppingActivity
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.selector.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_shopping.*
import javax.inject.Inject

class ShoppingActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ShoppingVMFactory

    private lateinit var viewModel: ShoppingViewModel

    /* Permission */
    private var permissionsGranted: Boolean = false

    /* barcode capturing on status */
    private var barcodeCaptureOn = false

    /* Cart properties */
    private var cartTotalAmount: Double = 0.0

    private lateinit var listNames: Array<String>

    private lateinit var fotoapparat: Fotoapparat

    private lateinit var barcodeDetectorProcessor: BarcodeDetectorProcessor

    private lateinit var failedScanView: View

    private lateinit var itemInfoView: View
    private lateinit var itemInfoName: TextView
    private lateinit var itemInfoPrice: TextView

    private lateinit var bottomSheet: LinearLayout
    private lateinit var bottomControlsImg: ImageView
    private lateinit var bottomControlsText: TextView

    private lateinit var titleAmount: TextView

    private lateinit var removeCb: CheckBox

    private lateinit var checkoutButton: Button

    private lateinit var unitTranslator: UnitTranslator

    private lateinit var adapter: ShoppingFragmentPagerAdapter

    private val permissionDelegate = PermissionsDelegate(this)

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createShoppingComponent().inject(this)
        setContentView(R.layout.activity_shopping)

        setupViewModel()

        setupViewState()

        setupIntentData()

        setupPermissions()

        setupBarcodeScanner()

        setupCamera()

        setupButtons()

        setupInfoTexts()

        setupBottomSheet()

        unitTranslator = UnitTranslator(resources)

        requestCartTotal()
    }

    override fun onDestroy() {
        disposables.clear()
        (application as App).releaseShoppingComponent()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        if(permissionsGranted) {
            fotoapparat.start()
            showCamera()
        }
    }

    override fun onStop() {
        super.onStop()
        if(permissionsGranted) {
            fotoapparat.stop()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(permissionDelegate.isRequestGranted(
                        permissions,
                        grantResults,
                        Manifest.permission.CAMERA)) {
                    /* Show camera and set permissions as granted */
                    permissionsGranted = true
                    fotoapparat.start()
                    /* + hide black screen */
                    showCamera()
                }
                else {
                    /* Show denied access */
                    permissionsGranted = false
                    hideCamera()
                }
            }
        }
    }

    override fun onBackPressed() {
        val dialog = ShoppingAbortDialogFragment.newInstance()
        disposables.add(dialog.observeCreate().subscribe{ onAbortShopping()})
        dialog.show(fragmentManager, null)
    }

    private fun showCamera() {
        camera_layout.visibility = View.VISIBLE
        cameraView.visibility = View.VISIBLE
        shoppingView.visibility = View.VISIBLE
        no_permission.visibility = View.GONE

    }

    private fun hideCamera() {
        camera_layout.visibility = View.GONE
        cameraView.visibility = View.GONE
        shoppingView.visibility = View.GONE
        no_permission.visibility = View.VISIBLE
    }

    private fun onBottomSheetVisible() {
        bottomControlsImg.setImageResource(R.drawable.ic_list)
        bottomControlsText.text = "Shopping Lists"
        adapter.setVisible()
    }

    private fun onBottomSheetHidden() {
        bottomControlsImg.setImageResource(R.drawable.ic_qr_reader)
        bottomControlsText.text = "Scanner"
    }

    private fun onBarcodesFound(barcodes: SparseArray<Barcode>) {
        if(barcodeCaptureOn) {
            stopBarcodeCapture()

            viewModel.getProductFromBarcodes(toListOfStrings(barcodes))
        }
    }

    private fun onAbortShopping() {
        //Clear shopping inmemorystore
        viewModel.clearShoppingCart()
        finish()
    }

    private fun onCheckoutClicked() {
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }

    private fun handleViewState(viewState: ShoppingViewState) {
        cartTotalAmount = viewState.total
        titleAmount.text = "${cartTotalAmount} kr"


        if(viewState.newItem) {
            if(viewState.barcodeProduct != null) {
                showItem(viewState.barcodeProduct!!, removeCb.isChecked)
            }
            else {
                showItemNotFound()
            }
        }

        if(viewState.startBarcodeCapture) {
            startBarcodeCapture()
        }
    }

    private fun toListOfStrings(barcodes: SparseArray<Barcode>): List<String> {
        val list: MutableList<String> = mutableListOf()
        var i = 0
        while(i < barcodes.size()) {
            val key = barcodes.keyAt(i)
            val value = barcodes.get(key)
            list.add(value.rawValue)
            i++
        }
        return list
    }

    private fun showItem(item: ShoppingCartItem, remove: Boolean) {
        itemInfoName.text = "${item.name} ${item.quantity}${unitTranslator.toUnitsOfQuantity(item.unit.id)}"

        if(remove) {
            itemInfoPrice.text = "- ${item.unitPrice} kr"
            itemInfoPrice.setTextColor(ContextCompat.getColor(this, R.color.colorRemoveItem))
        }
        else {
            itemInfoPrice.text = "+ ${item.unitPrice} kr"
            itemInfoPrice.setTextColor(ContextCompat.getColor(this, R.color.colorAddItem))
        }

        itemInfoView.visibility = View.VISIBLE

        val fadeInFadeOut = AnimatorInflater.loadAnimator(this, R.animator.fade_in_fade_out)
        fadeInFadeOut.setTarget(itemInfoView)

        fadeInFadeOut.addListener(object: Animator.AnimatorListener {

            override fun onAnimationEnd(animation: Animator) {
                itemInfoView.visibility = View.GONE

                if(remove) {
                    viewModel.removeItemFromCart(item)
                }
                else {
                    viewModel.addItemToCart(item)
                }
            }

            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        fadeInFadeOut.start()
    }

    private fun showItemNotFound() {
        failedScanView.visibility = View.VISIBLE

        val fadeInFadeOut = AnimatorInflater.loadAnimator(this, R.animator.fade_in_fade_out)
        fadeInFadeOut.setTarget(failedScanView)

        fadeInFadeOut.addListener(object: Animator.AnimatorListener {

            override fun onAnimationEnd(animation: Animator) {
                itemInfoView.visibility = View.GONE

                startBarcodeCapture()
            }

            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        fadeInFadeOut.start()
    }

    private fun startBarcodeCapture() {
        barcodeCaptureOn = true
    }

    private fun stopBarcodeCapture() {
        barcodeCaptureOn = false
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ShoppingViewModel::class.java)
    }

    private fun setupViewState() {
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer {
            //handle ErrorState updates
            Log.e(TAG, "Unable to perform action", it)
        })
    }

    private fun setupIntentData() {
        listNames = intent.getStringArrayExtra(LISTS_ARG)
    }

    private fun setupPermissions() {
        permissionsGranted = permissionDelegate.hasPermission(Manifest.permission.CAMERA)

        if(!permissionsGranted) {
            permissionDelegate.requestPermission(CAMERA_REQUEST_CODE, Manifest.permission.CAMERA)
        }
    }

    private fun setupBarcodeScanner() {
        barcodeDetectorProcessor = BarcodeDetectorProcessor(this.applicationContext)

        disposables.add(barcodeDetectorProcessor.observBarcodes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onBarcodesFound(it)
                }))
    }

    private fun setupCamera() {
        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                logger = logcat(),
                lensPosition = back(),
                cameraConfiguration = CameraConfiguration(
                        previewResolution = firstAvailable(
                                wideRatio(highestResolution()),
                                standardRatio(highestResolution())
                        ),
                        previewFpsRange = highestFps(),
                        flashMode = off(),
                        focusMode = firstAvailable(
                                continuousFocusPicture(),
                                autoFocus(),
                                fixed()
                        ),
                        frameProcessor = barcodeDetectorProcessor
                ),
                cameraErrorCallback = {}
        )
    }

    private fun setupButtons() {
        checkoutButton = findViewById(R.id.bottom_controls_checkout_bt)

        checkoutButton.setOnClickListener {
            onCheckoutClicked()
        }
    }

    private fun setupInfoTexts() {
        titleAmount = findViewById(R.id.title_amount)

        failedScanView = findViewById(R.id.fail_scan_view)

        itemInfoView = findViewById(R.id.item_info_view)
        itemInfoName = findViewById(R.id.item_info_name)
        itemInfoPrice = findViewById(R.id.item_info_price)

        removeCb = findViewById(R.id.remove_cb)
    }

    private fun setupBottomSheet() {
        bottomSheet = findViewById(R.id.bottom_sheet_container)
        bottomControlsImg = findViewById(R.id.bottom_controls_info_img)
        bottomControlsText = findViewById(R.id.bottom_controls_info_text)
        titleAmount = findViewById(R.id.title_amount)

        val viewPager = findViewById<ViewPager>(R.id.bottom_sheet_view_pager)
        adapter = ShoppingFragmentPagerAdapter(this, supportFragmentManager, listNames)

        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.bottom_sheet_tab_layout)
        tabLayout.setupWithViewPager(viewPager)

        BottomSheetUtils.setupViewPager(viewPager)

        val bottomSheet = ViewPagerBottomSheetBehavior.from(bottomSheet)
        bottomSheet.setBottomSheetCallback(object : ViewPagerBottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(bottomSheet: View, state: Int) {
                when(state) {
                    ViewPagerBottomSheetBehavior.STATE_COLLAPSED ->
                        onBottomSheetHidden()
                    ViewPagerBottomSheetBehavior.STATE_EXPANDED ->
                        onBottomSheetVisible()

                }
            }
        })
    }

    private fun requestCartTotal() {
        viewModel.getCartTotal()
    }

    companion object {
        private val CAMERA_REQUEST_CODE = 10
        private val TAG = "Shopping"

        val LISTS_ARG: String = "lists"
    }
}
