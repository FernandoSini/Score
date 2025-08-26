package com.flemis.score.features.app.data.datasource.local

abstract class BaseDataSource<E, T>(private val entityMapper: (E) -> T, private val modelMapper: (T) -> E) {

    protected fun convertToModel(list: List<E>): List<T> {
        return list.map { entityMapper(it) }
    }

    protected fun convertToEntity(list: List<T>): List<E> {
        return list.map { modelMapper(it) }
    }
}