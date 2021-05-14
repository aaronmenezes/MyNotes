package com.kyser.mynotes.model

class Note {

    private var id = 0
    private var key = 0
    private var priority = 0
    private var name: String? = null
    private var body: String? = null
    private var date: String? = null

    fun setId(id: Int) {
        this.id = id
    }

    fun setKey(key: Int) {
        this.key = key
    }

    fun setPriority(priority: Int) {
        this.priority = priority
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun setBody(body: String?) {
        this.body = body
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getId(): Int {
        return id
    }

    fun getKey(): Int {
        return key
    }

    fun getPriority(): Int {
        return priority
    }

    fun getName(): String? {
        return name
    }

    fun getBody(): String? {
        return body
    }

    fun getDate(): String? {
        return date
    }
}