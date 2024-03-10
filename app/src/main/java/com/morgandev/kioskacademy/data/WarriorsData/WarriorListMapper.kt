package com.morgandev.kioskacademy.data.WarriorsData

import com.morgandev.kioskacademy.domain.entities.Warrior

class WarriorListMapper {

    fun mapEntityToDbModel(warrior: Warrior) = WarriorDbModel(
        id = warrior.id,
        profilePicture = warrior.profilePicture,
        rank = warrior.rank,
        nameUA = warrior.nameUA,
        fullNameUA = warrior.fullNameUA,
        departmentEmblem = warrior.departmentEmblem,
        dateBirth = warrior.dateBirth,
        dateDied = warrior.dateDied,
        description = warrior.description,
        photos = warrior.photos,
        videos = warrior.videos
    )

    fun mapDbModelToEntity(warriorDbModel: WarriorDbModel) = Warrior(
        id = warriorDbModel.id,
        profilePicture = warriorDbModel.profilePicture,
        rank = warriorDbModel.rank,
        nameUA = warriorDbModel.nameUA,
        fullNameUA = warriorDbModel.fullNameUA,
        departmentEmblem = warriorDbModel.departmentEmblem,
        dateBirth = warriorDbModel.dateBirth,
        dateDied = warriorDbModel.dateDied,
        description = warriorDbModel.description,
        photos = warriorDbModel.photos,
        videos = warriorDbModel.videos
    )

    fun mapListDbModelToListEntity(list: List<WarriorDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}