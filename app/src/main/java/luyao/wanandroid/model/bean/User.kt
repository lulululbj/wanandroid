package luyao.wanandroid.model.bean


/**
 * Created by Lu
 * on 2018/4/5 08:02
 */

/*
 * {
    "data": {
        "admin": false,
        "chapterTops": [],
        "collectIds": [
            8259,
            8251,
            8072,
            8273,
            8160,
            8386,
            8695,
            9607,
            10022,
            9766,
            10825
        ],
        "email": "",
        "icon": "",
        "id": 22057,
        "nickname": "秉心说",
        "password": "",
        "publicName": "秉心说",
        "token": "",
        "type": 0,
        "username": "秉心说___"
    },
    "errorCode": 0,
    "errorMsg": ""
}
 */
data class User(val admin: Boolean,
                val chapterTops: List<String>,
                val collectIds: List<Int>,
                val email: String,
                val icon: String,
                val id: Int,
                val nickname: String,
                val password: String,
                val publicName: String,
                val token: String,
                val type: Int,
                val username: String){

    override fun equals(other: Any?): Boolean {
        return if (other is User){
            this.id == other.id
        }else false
    }
}