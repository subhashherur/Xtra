package com.github.andreyasadchy.xtra.model.misc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class STVChannelResponse(
    @SerialName("emote_set_id")
    val emoteSetId: String? = null,
    @SerialName("emote_set")
    val emoteSet: STVEmoteSetResponse? = null,
)

@Serializable
class STVGqlDataResponse(
    val data: STVGqlData? = null,
)

@Serializable
class STVGqlData(
    val userByConnection: STVGqlUser? = null,
    val emoteSetsByID: List<STVEmoteSetResponse>? = null,
)

@Serializable
class STVGqlUser(
    val id: String? = null,
    val connections: List<STVGqlConnection>? = null,
)

@Serializable
class STVGqlConnection(
    val platform: String? = null,
    @SerialName("emote_set_id")
    val emoteSetId: String? = null,
)