package com.farhanhp.myanimedb.datas

data class Anime(
  var id: String,
  var description: String,
  var title: String,
  var imageUrl: String,
  var videoUrl: String?,
  var episodeCount: Int?,
  var score: Double,
  var userCount: Int,
  var isFavorite: Boolean,
  var scoreGiven: Int,
)
