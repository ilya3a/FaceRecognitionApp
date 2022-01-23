package com.yoyo.facerecognition.ui.fragments.content.recycler

import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yoyo.facerecognition.database.models.PicInfoDataModel
import com.yoyo.facerecognition.databinding.ItemImageBinding

class PicInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mBinding = ItemImageBinding.bind(itemView)
    fun onBind(picInfoDataModel: PicInfoDataModel) {
        val bitmap = BitmapFactory.decodeFile(picInfoDataModel.path)
        val thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 480, 360)
        mBinding.pictureImage.load(thumbnail)
    }
}
