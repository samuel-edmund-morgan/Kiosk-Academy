package com.morgandev.kioskacademy.data.AppData

import com.morgandev.kioskacademy.domain.entity.AppData

class AppDataMapper {
    fun mapEntityToDbModel(appData: AppData) = AppDataDbModel(
        id = appData.id,
        backgroundImage = appData.backgroundImage,
        emblemId = appData.emblemId,
        year = appData.year,
        galleryNameUA = appData.galleryNameUA,
        galleryNameENG = appData.galleryNameENG
    )

    fun mapDbModelToEntity(appDataDbModel: AppDataDbModel) = AppData(
        id = appDataDbModel.id,
        backgroundImage = appDataDbModel.backgroundImage,
        emblemId = appDataDbModel.emblemId,
        year = appDataDbModel.year,
        galleryNameUA = appDataDbModel.galleryNameUA,
        galleryNameENG = appDataDbModel.galleryNameENG
    )

    fun mapListDbModelToListEntity(list: List<AppDataDbModel>) = list.map {
        mapDbModelToEntity(it)
    }


}