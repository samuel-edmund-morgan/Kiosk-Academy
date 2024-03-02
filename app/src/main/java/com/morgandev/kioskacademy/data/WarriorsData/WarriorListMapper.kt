package com.morgandev.kioskacademy.data.WarriorsData

import com.morgandev.kioskacademy.domain.entity.Warrior

class WarriorListMapper {

    fun mapEntityToDbModel(warrior: Warrior) = WarriorDbModel(
        id = warrior.id,
        profilePicture = warrior.profilePicture,
        rank = warrior.rank,
        nameUA = warrior.nameUA,
        nameENG = warrior.nameENG,
        fullNameUA = warrior.fullNameUA,
        fullNameENG = warrior.fullNameENG,
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
        nameENG = warriorDbModel.nameENG,
        fullNameUA = warriorDbModel.fullNameUA,
        fullNameENG = warriorDbModel.fullNameENG,
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