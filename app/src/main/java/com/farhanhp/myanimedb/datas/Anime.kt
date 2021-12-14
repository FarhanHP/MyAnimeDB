package com.farhanhp.myanimedb.datas

data class Anime(
  val id: String,
  val description: String,
  val title: String,
  val imageUrl: String,
  val videoUrl: String?,
  val episodeCount: Int?,
  val score: Double,
  val userCount: Int,
  val isFavorite: Boolean,
  val scoreGiven: Int,
)
