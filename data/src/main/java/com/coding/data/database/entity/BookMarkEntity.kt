package com.coding.data.database.entity


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class BookMarkEntity




    : RealmObject {
    @PrimaryKey
    var id: ObjectId= ObjectId.invoke()
    var author: String?=""
    var content: String?=""
    var description: String?=""
    var publishedAt: String=""
    var title: String=""
    var category: String?=""
    var priority: String?=""
    var url: String=""
    var urlToImage: String?=""

}