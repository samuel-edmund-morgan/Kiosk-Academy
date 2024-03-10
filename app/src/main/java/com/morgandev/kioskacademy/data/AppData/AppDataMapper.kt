package com.morgandev.kioskacademy.data.AppData

import com.morgandev.kioskacademy.domain.entities.AppData

class AppDataMapper {
    fun mapEntityToDbModel(appData: AppData) = AppDataDbModel(
        id = appData.id,
        backgroundImage = appData.backgroundImage,
        emblemId = appData.emblemId,
        year = appData.year,
        galleryNameUA = appData.galleryNameUA,
    )

    fun mapDbModelToEntity(appDataDbModel: AppDataDbModel) = AppData(
        id = appDataDbModel.id,
        backgroundImage = appDataDbModel.backgroundImage,
        emblemId = appDataDbModel.emblemId,
        year = appDataDbModel.year,
        galleryNameUA = appDataDbModel.galleryNameUA
    )

    fun mapListDbModelToListEntity(list: List<AppDataDbModel>) = list.map {
        mapDbModelToEntity(it)
    }


}