package com.example.mymovielibrary.data.remote.lists.adapter

import com.example.mymovielibrary.data.remote.lists.model.media.MediaItemResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class MediaItemAdapter

//Adapter for MediaItem to separate Movie & TvShow
class ResultsAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val delegateAnnotations = Types.nextAnnotations(annotations, MediaItemAdapter::class.java) ?: return null
        val delegateType = Types.newParameterizedType(List::class.java, MediaItemResponse::class.java)
        val delegateAdapter: JsonAdapter<List<MediaItemResponse>> = moshi.adapter(delegateType, delegateAnnotations)

        val mediaItemAdapter: JsonAdapter<MediaItemResponse> = moshi.adapter(MediaItemResponse::class.java)
        return ResultsJsonAdapter(mediaItemAdapter)
    }
}

class ResultsJsonAdapter(private val adapter: JsonAdapter<MediaItemResponse>) : JsonAdapter<List<MediaItemResponse>>() {
    @FromJson
    @MediaItemAdapter
    override fun fromJson(reader: JsonReader): List<MediaItemResponse> {
        val result = mutableListOf<MediaItemResponse>()
        reader.beginArray()
        while (reader.hasNext()) {
            result.add(adapter.fromJson(reader)!!)
        }
        reader.endArray()
        return result
    }

    @ToJson
    @MediaItemAdapter
    override fun toJson(p0: JsonWriter, p1: List<MediaItemResponse>?) {}
}