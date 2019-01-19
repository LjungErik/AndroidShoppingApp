package com.thetestcompany.presentation.common.utils

import android.content.Context
import android.graphics.*
import android.util.SparseArray
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import io.fotoapparat.preview.Frame
import io.fotoapparat.util.FrameProcessor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.ByteArrayOutputStream


class BarcodeDetectorProcessor : FrameProcessor {

    private val detector: BarcodeDetector

    private val barcodeSubject: PublishSubject<SparseArray<Barcode>> = PublishSubject.create()

    constructor(ctx: Context) {
        detector = BarcodeDetector.Builder(ctx)
                .setBarcodeFormats(Barcode.EAN_13 or Barcode.EAN_8 or Barcode.CODE_39 or Barcode.CODE_93)
                .build()
    }

    override fun invoke(frame: Frame) {
        val bitmap = getAsBitmap(frame)
        val barcodes = detectBarcodes(bitmap)

        /* Only signal when there are actually barcodes available */
        if (barcodes.size() > 0) {
            barcodeSubject.onNext(barcodes)
        }
    }

    fun observBarcodes(): Observable<SparseArray<Barcode>> {
        return barcodeSubject
    }

    private fun getAsBitmap(frame: Frame): Bitmap {
        val data = frame.image
        val height: Int = frame.size.height
        val width = frame.size.width

        val yuvImage = YuvImage(data, ImageFormat.NV21, width, height, null)
        val os = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, os)
        val jpegByteArray = os.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.size)
        os.close()
        return bitmap
    }

    private fun detectBarcodes(bitmap: Bitmap) : SparseArray<Barcode> {
        val barcodeFrame = com.google.android.gms.vision.Frame.Builder().setBitmap(bitmap).build()
        return detector.detect(barcodeFrame)
    }
}