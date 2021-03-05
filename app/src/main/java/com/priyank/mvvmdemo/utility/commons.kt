package com.priyank.mvvmdemo.utility

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.priyank.mvvmdemo.BuildConfig
import com.priyank.mvvmdemo.presentation.base.BaseActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

fun View.snackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * For show dialog
 *
 * @param title - title which shown in dialog (application name)
 * @param msg - message which shown in dialog
 * @param positiveText - positive button text
 * @param listener - positive button listener
 * @param negativeText - negative button text
 * @param negativeListener - negative button listener
 * @param icon - drawable icon which shown is dialog
 */
fun Context.showDialog(
    msg: String,
    positiveText: String? = "OK",
    listener: DialogInterface.OnClickListener? = null,
    negativeText: String? = "Cancel",
    negativeListener: DialogInterface.OnClickListener? = null,
    title: String? = "AppName",
    icon: Int? = null
) {
    if (BaseActivity.dialogShowing) {
        return
    }
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton(positiveText) { dialog, which ->
        BaseActivity.dialogShowing = false
        listener?.onClick(dialog, which)
    }
    if (negativeListener != null) {
        builder.setNegativeButton(negativeText) { dialog, which ->
            BaseActivity.dialogShowing = false
            negativeListener.onClick(dialog, which)
        }
    }
    if (icon != null) {
        builder.setIcon(icon)
    }
    builder.create().show()
    BaseActivity.dialogShowing = true
}

/**
 * For validate email-id
 *
 * @return email-id is valid or not
 */
fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * For validate phoneNumber
 *
 * @return phoneNumber is valid or not
 */
fun String.isValidMobile(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

/**
 * isNetworkAvailable - Check if there is a NetworkConnection
 * @return boolean
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        //for check internet over Bluetooth
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

/**
 * compare string
 * @return boolean
 */
infix fun String.shouldBeSame(other: String) = this == other

/**
 * For launch other activity
 */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

/**
 * load image
 */
fun Context.loadImage(imagePath: String, imageView: AppCompatImageView) {
    Glide.with(this)
        .load(imagePath)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

fun Context.loadCircleImage(imagePath: String, imageView: AppCompatImageView) {
    Glide.with(this)
        .load(imagePath)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

fun Context.loadRoundedCornerImage(imagePath: String, imageView: AppCompatImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    Glide.with(this).load(imagePath)
        .transform(CenterCrop(), RoundedCorners(10))
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

/**
 * path from resources
 */
fun pathFromResources(resourceId: Int): String {
    return "android.resource://${BuildConfig.APPLICATION_ID}/$resourceId"
}

/**
 * prevent double click
 */
fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

/**
 * for check string
 */
fun isEmptyString(text: String?): String {
    return if (text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }
            .isEmpty()) {
        "-"
    } else {
        text
    }
}

/**
 * value set in decimal format
 * like 1.1111 to 1.11
 */
fun setValueInDecimalFormat(value: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(value)
}

/**
 * convert object to string
 */
fun convertObjectToString(value: Any): String {
    return Gson().toJson(value)
}

/**
 * get object from string
 */
fun <T> convertStringToObject(value: String?, defValue: Class<T>): T {
    return Gson().fromJson(SharedPrefHelper[value!!, ""], defValue)
}

/**
 * convert to Requestbody
 */
fun String.convertToRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

/**
 * check guest user
 */
fun checkIsGuestUser(): Boolean {
    return SharedPrefHelper[SharedPrefHelper.USER_DATA, ""].isEmpty()
}


/**
 * getFull Address from latlng
 */
fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].getAddressLine(0)
}

/**
 * getFull Address from latlng
 */
fun getSubLocalityFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].subLocality
}

/**
 * fullScreen transparent statusbar
 */
fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

/**
 * convert dp to px
 */
fun dpToPx(dp: Int, context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}