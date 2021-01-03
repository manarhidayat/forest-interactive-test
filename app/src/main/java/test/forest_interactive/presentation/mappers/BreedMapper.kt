package test.forest_interactive.presentation.mappers

import test.forest_interactive.data.entities.BreedEntity
import test.forest_interactive.data.model.breed.BreedsItem
import test.forest_interactive.data.model.breed.Image

fun BreedEntity.toData() = BreedsItem(
    this.description,
    this.id,
    Image(
        320,
        this.images!!,
        this.images,
        420
    ),
    this.name,
    this.origin
)

fun List<BreedEntity>.toDataList() = this.map { it.toData() }

fun BreedsItem.toDataEntity() =
    this.image?.url?.let {
        BreedEntity(
        id = this.id,
        images = it,
        name = this.name,
        origin = this.origin,
        description = this.description
    )
    }

fun List<BreedsItem>.toDataEntityList() = this.map {
    var imageUrl = "-"
    if(it.image != null && it.image!!.url != null){
        imageUrl = it.image!!.url
    }
    BreedEntity(
        id = it.id,
        images = imageUrl,
        name = it.name,
        origin = it.origin,
        description = it.description
    )

//    it.toDataEntity()
}