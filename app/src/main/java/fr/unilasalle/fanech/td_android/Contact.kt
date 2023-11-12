package fr.unilasalle.fanech.td_android

class Contact {
    var name: String
    var firstname: String
    var phone: String
    var age: Int
    var gender: Int

    constructor(name: String, firstname: String, phone: String, age: Int , gender: Int) {
        this.name = name
        this.firstname = firstname
        this.phone = phone
        this.age = age
        this.gender = gender
    }
}