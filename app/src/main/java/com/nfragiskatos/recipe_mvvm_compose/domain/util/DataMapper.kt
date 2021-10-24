package com.nfragiskatos.recipe_mvvm_compose.domain.util

interface DataMapper<Data, Domain> {

    fun mapFromData(data: Data): Domain

    fun mapToData(domain: Domain): Data
}